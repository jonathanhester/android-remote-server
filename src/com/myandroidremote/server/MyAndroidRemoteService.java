package com.myandroidremote.server;

import java.util.Date;
import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.myandroidremote.shared.AndroidCommandProxy;


public class MyAndroidRemoteService {

	static DataStore db = new DataStore();
	
	public static AndroidCommand createAndroidCommand() {
		return db.update(new AndroidCommand());
	}

	public static AndroidCommand readAndroidCommand(Long id) {
		return db.find(id);
	}

	public static AndroidCommand updateAndroidCommand(
			AndroidCommand androidCommand) {
		androidCommand.setEmailAddress(DataStore.getUserEmail());
		androidCommand.setRequestTime(new Date());
		
		androidCommand = db.update(androidCommand);
		DataStore.sendAndroidCommandC2DMUpdate(androidCommand);
		
		return androidCommand;
	}
	
	public static AndroidCommand respondAndroidCommand(Long id) {
		AndroidCommand androidCommand = readAndroidCommand(id);
		androidCommand.setResponseTime(new Date());
		androidCommand = db.update(androidCommand);
		return androidCommand;
	}
	
	public static AndroidCommand readOpenUriCommand(Long id) {
		AndroidCommand androidCommand = readAndroidCommand(id);
		androidCommand.setResponseTime(new Date());
		androidCommand = db.update(androidCommand);
		return androidCommand;
	}
	
	
	public static AndroidCommand respondLocationCommand(Long id, int latitude, int longitude, long timestamp) {
		AndroidCommand androidCommand = readAndroidCommand(id);
		androidCommand.setResponseTime(new Date());
		androidCommand.setLatitude(latitude);
		androidCommand.setLongitude(longitude);
		androidCommand.setTime(new Date(timestamp));
		androidCommand = db.update(androidCommand);
		return androidCommand;
	}

	public static void deleteAndroidCommand(AndroidCommand androidcommand) {
		db.delete(androidcommand.getId());
	}

	public static List<AndroidCommand> queryAndroidCommands() {
		return db.findAll();
	}



}
