package com.myandroidremote.shared;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.myandroidremote.server.AndroidCommand", locator = "com.myandroidremote.server.AndroidCommandLocator")
public interface AndroidCommandProxy extends ValueProxy {

	public void setId(Long id);

	public Long getId();
	
	String getUserId();

	void setUserId(String userId);

	Date getRequestTime();

	void setRequestTime(Date requestTime);

	int getCommand();

	void setCommand(int command);

	Date getResponseTime();

	void setResponseTime(Date responseTime);

	int getLatitude();

	void setLatitude(int latitude);

	int getLongitude();

	void setLongitude(int longitude);
	
	Date getTime();
	
	void setTime(Date time);
	
	String getUri();
	
	void setUri(String uri);
}
