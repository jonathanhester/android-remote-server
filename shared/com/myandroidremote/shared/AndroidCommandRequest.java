package com.myandroidremote.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;

@ServiceName("com.myandroidremote.server.MyAndroidRemoteService")
public interface AndroidCommandRequest extends RequestContext {

	Request<AndroidCommandProxy> createAndroidCommand();
	
	Request<AndroidCommandProxy> respondAndroidCommand(Long id);
	
	Request<AndroidCommandProxy> respondLocationCommand(Long id, double latitude, double longitude, long time);

	Request<AndroidCommandProxy> readAndroidCommand(Long id);
	
	Request<AndroidCommandProxy> readOpenUriCommand(Long id);

	Request<AndroidCommandProxy> updateAndroidCommand(
			AndroidCommandProxy androidcommand);

	Request<Void> deleteAndroidCommand(AndroidCommandProxy androidcommand);

	Request<List<AndroidCommandProxy>> queryAndroidCommands();

}
