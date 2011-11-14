package com.myandroidremote.server;

import com.google.web.bindery.requestfactory.shared.Locator;


public class AndroidCommandLocator extends Locator<AndroidCommand, Void> {

	@Override
	public AndroidCommand create(Class<? extends AndroidCommand> clazz) {
		return new AndroidCommand();
	}

	@Override
	public AndroidCommand find(Class<? extends AndroidCommand> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Class<AndroidCommand> getDomainType() {
		return AndroidCommand.class;
	}

	@Override
	public Void getId(AndroidCommand domainObject) {
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		return Void.class;
	}

	@Override
	public Object getVersion(AndroidCommand domainObject) {
		return null;
	}

}
