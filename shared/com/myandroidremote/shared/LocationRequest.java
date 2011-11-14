package com.myandroidremote.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;

@ServiceName("com.myandroidremote.server.MyAndroidRemoteService")
public interface LocationRequest extends RequestContext {

	Request<LocationProxy> createLocation();

	Request<LocationProxy> readLocation(Long id);

	Request<LocationProxy> updateLocation(LocationProxy location);

	Request<Void> deleteLocation(LocationProxy location);

	Request<List<LocationProxy>> queryLocations();

}
