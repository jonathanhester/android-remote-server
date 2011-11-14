/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.myandroidremote.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.myandroidremote.client.MyRequestFactory.HelloWorldRequest;
import com.myandroidremote.shared.AndroidCommandProxy;
import com.myandroidremote.shared.AndroidCommandRequest;
import com.myandroidremote.shared.MyAndroidRemoteRequestFactory;
import com.myandroidremote.shared.RemoteCommands;

public class MyAndroidRemoteWidget extends Composite {
	private static final int STATUS_DELAY = 4000;
	private static final String STATUS_ERROR = "status error";
	private static final String STATUS_NONE = "status none";
	private static final String STATUS_SUCCESS = "status success";

	interface MyAndroidRemoteUiBinder extends
			UiBinder<Widget, MyAndroidRemoteWidget> {
	}

	private static MyAndroidRemoteUiBinder uiBinder = GWT
			.create(MyAndroidRemoteUiBinder.class);

	MyAndroidRemoteRequestFactory remoteRequestFactory;

	@UiField
	DivElement status;

	@UiField
	Button sayHelloButton;

	@UiField
	Button getLocationButton;

	@UiField
	Button stopLocationButton;

	@UiField
	Button pingButton;

	@UiField
	Button openUriButton;

	@UiField
	TextBox uriString;

	@UiField
	Button playAudioButton;

	@UiField
	Button stopAudioButton;

	private void setStatus(String message, boolean error) {
		status.setInnerText(message);
		if (error) {
			status.setClassName(STATUS_ERROR);
		} else {
			if (message.length() == 0) {
				status.setClassName(STATUS_NONE);
			} else {
				status.setClassName(STATUS_SUCCESS);
			}
		}

	}

	public MyAndroidRemoteWidget() {

		initWidget(uiBinder.createAndBindUi(this));
		sayHelloButton.getElement().setClassName("send centerbtn");
		getLocationButton.getElement().setClassName("send centerbtn");
		stopLocationButton.getElement().setClassName("send centerbtn");

		pingButton.getElement().setClassName("send centerbtn");
		openUriButton.getElement().setClassName("send centerbtn");
		playAudioButton.getElement().setClassName("send centerbtn");
		stopAudioButton.getElement().setClassName("send centerbtn");

		final EventBus eventBus = new SimpleEventBus();
		final MyRequestFactory requestFactory = GWT
				.create(MyRequestFactory.class);
		remoteRequestFactory = GWT.create(MyAndroidRemoteRequestFactory.class);
		requestFactory.initialize(eventBus);
		remoteRequestFactory.initialize(eventBus);

		sayHelloButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sayHelloButton.setEnabled(false);
				HelloWorldRequest helloWorldRequest = requestFactory
						.helloWorldRequest();
				helloWorldRequest.getMessage().fire(new Receiver<String>() {
					@Override
					public void onFailure(ServerFailure error) {
						sayHelloButton.setEnabled(true);
						setStatus(error.getMessage(), true);
					}

					@Override
					public void onSuccess(String response) {
						sayHelloButton.setEnabled(true);
						setStatus(response, response.startsWith("Failure:"));
					}
				});
			}
		});

		getLocationButton.addClickHandler(new CommandClickHandler(
				getLocationButton, remoteRequestFactory,
				RemoteCommands.COMMAND_GET_LOCATION));
		stopLocationButton.addClickHandler(new CommandClickHandler(
				stopLocationButton, remoteRequestFactory,
				RemoteCommands.COMMAND_STOP_LOCATION));
		pingButton.addClickHandler(new CommandClickHandler(pingButton,
				remoteRequestFactory, RemoteCommands.COMMAND_PING));
		openUriButton.addClickHandler(new CommandClickHandler(openUriButton,
				remoteRequestFactory, RemoteCommands.COMMAND_OPEN_URI));
		playAudioButton.addClickHandler(new CommandClickHandler(playAudioButton,
				remoteRequestFactory, RemoteCommands.COMMAND_PLAY_AUDIO));
		stopAudioButton.addClickHandler(new CommandClickHandler(stopAudioButton,
				remoteRequestFactory, RemoteCommands.COMMAND_STOP_AUDIO));
		
	}

	class CommandClickHandler implements ClickHandler {
		private MyAndroidRemoteRequestFactory requestFactory;
		private int command;
		private Button button;

		public CommandClickHandler(Button button,
				MyAndroidRemoteRequestFactory requestFactory, int command) {
			this.requestFactory = requestFactory;
			this.command = command;
			this.button = button;
		}

		@Override
		public void onClick(ClickEvent event) {
			setStatus(RemoteCommands.getString(command), false);
			button.setEnabled(false);

			// Send a message using RequestFactory
			final AndroidCommandRequest request = requestFactory
					.androidCommandRequest();
			final AndroidCommandProxy androidCommand = request
					.create(AndroidCommandProxy.class);
			androidCommand.setCommand(command);
			String uri = uriString.getText();
			androidCommand.setUri(uri);
			request.updateAndroidCommand(androidCommand).fire(
					new Receiver<AndroidCommandProxy>() {
						@Override
						public void onFailure(ServerFailure error) {
							button.setEnabled(true);
							setStatus(error.getMessage(), true);
						}

						@Override
						public void onSuccess(AndroidCommandProxy response) {
							button.setEnabled(true);
							Poller poller = new Poller(request
									.readAndroidCommand(response.getId()),
									response.getId());
							Scheduler.get().scheduleFixedDelay(poller,
									STATUS_DELAY);
						}
					});
		}
	}

	class Poller implements RepeatingCommand {
		private Long id;
		private boolean finished = false;

		public Poller(Request<AndroidCommandProxy> request, Long id) {
			this.id = id;
		}

		@Override
		public boolean execute() {

			// Send a message using RequestFactory
			AndroidCommandRequest request = remoteRequestFactory
					.androidCommandRequest();
			request.readAndroidCommand(id).fire(
					new Receiver<AndroidCommandProxy>() {
						@Override
						public void onFailure(ServerFailure error) {
							setStatus(error.getMessage(), true);
						}

						@Override
						public void onSuccess(AndroidCommandProxy response) {
							if (response != null
									&& response.getResponseTime() != null) {
								setStatus("Ping took " + getPingTime(response)
										+ "ms, " + response.getLatitude()
										+ ", " + response.getLongitude(), false);
								finished = true;
							}
						}
					});
			return finished;
		}
	}

	private String getPingTime(AndroidCommandProxy response) {
		long elapsed = response.getResponseTime().getTime()
				- response.getRequestTime().getTime();
		return Long.toString(elapsed);

	}
}
