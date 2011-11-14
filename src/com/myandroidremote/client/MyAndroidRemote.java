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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyAndroidRemote implements EntryPoint {
	//private MapWidget mapWidget;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		MyAndroidRemoteWidget widget = new MyAndroidRemoteWidget();
		RootPanel.get().add(widget);
		//addMap();

	}

//	private void addMap() {
//		final MapOptions options = new MapOptions();
//		// Zoom level. Required
//		options.setZoom(8);
//		// Open a map centered on Cawker City, KS USA. Required
//		options.setCenter(new LatLng(39.509, -98.434));
//		// Map type. Required.
//		options.setMapTypeId(new MapTypeId().getRoadmap());
//
//		// Enable maps drag feature. Disabled by default.
//		options.setDraggable(true);
//		// Enable and add default navigation control. Disabled by default.
//		options.setNavigationControl(true);
//		// Enable and add map type control. Disabled by default.
//		options.setMapTypeControl(true);
//		mapWidget = new MapWidget(options);
//		mapWidget.setSize("800px", "600px");
//
//		// Add the map to the HTML host page
//		RootPanel.get("mapsTutorial").add(mapWidget);
//
//	}
}
