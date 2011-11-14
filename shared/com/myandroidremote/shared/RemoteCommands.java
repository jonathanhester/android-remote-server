package com.myandroidremote.shared;

public class RemoteCommands {
	
	public static final String PARAM_COMMAND = "command";
	public static final String PARAM_COMMAND_ID = "commandId";
	public static final String PARAM_URI = "uri";

	public static final int COMMAND_PING 			= 1;
	public static final int COMMAND_GET_LOCATION 	= 2;
	public static final int COMMAND_STOP_LOCATION 	= 4;
	public static final int COMMAND_OPEN_URI		= 3;
	public static final int COMMAND_PLAY_AUDIO		= 5;
	public static final int COMMAND_STOP_AUDIO		= 6;
	
	public static String getString(int command) {
		switch(command) {
		case RemoteCommands.COMMAND_GET_LOCATION:
			return "Get Location";
		case RemoteCommands.COMMAND_PING:
			return "Ping";
		case RemoteCommands.COMMAND_OPEN_URI:
			return "Open uri";
		case RemoteCommands.COMMAND_STOP_LOCATION:
			return "Stop location";
		}
		return "not found";
	}
}
