package com.glueball.snoop.module.util;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.JsonEncoderDecoder.DecodingException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class CrmRequest<T> {
		
	private final JsonEncoderDecoder<T> codec;

	public CrmRequest(final JsonEncoderDecoder<T> codec) {
		this.codec = codec;
	}

	public void getEntity(final String url, final CrmRequestCallback<T> crmCallback) {
		
		final Resource resource = new Resource(url);
		resource.get().send(new JsonCallback() {
			public void onSuccess(final Method method, final JSONValue response) {
				try {
					crmCallback.onSuccess((T)codec.decode(response));
				} catch (DecodingException e) {
					Window.alert(this.getClass().getName()+" : "+"DecodingError: "+e);
				} catch (Exception e) {
					Window.alert(this.getClass().getName()+" : "+"GeneralError: "+e);
				}
			}
			public void onFailure(Method method, Throwable exception) {
				Window.alert(this.getClass().getName()+" : "+"CommunicationFailure: "+exception);
			}
		});

	}

	public List<T> getEntities(final String url) {
		
		final List<T> retVal = new ArrayList<T>(1);
				
		final Resource resource = new Resource(url);
		resource.get().send(new JsonCallback() {
			public void onSuccess(final Method method, final JSONValue response) {
				try {
					retVal.add((T)codec.decode(response));
				} catch (DecodingException e) {
					Window.alert(this.getClass().getName()+" : "+"Error: "+e);
				} catch (Exception e) {
					Window.alert(this.getClass().getName()+" : "+"Error: "+e);
				}
			}
			public void onFailure(Method method, Throwable exception) {
				Window.alert(this.getClass().getName()+" : "+"Error: "+exception);
			}
		});
		return retVal;
	}

	public void save(final String url, final T entity, final CrmRequestCallback<T> crmCallback) {
		final Resource resource = new Resource(url);
		final JSONValue request = codec.encode(entity);
		resource.post().json(request).send(new JsonCallback() {
			public void onFailure(final Method method, final Throwable exception) {
				Window.alert(this.getClass().getName()+" : "+"CommunicationFailure: "+exception);
			}
			public void onSuccess(final Method method, final JSONValue response) {
				try {
					crmCallback.onSuccess((T)codec.decode(response));
				} catch (final DecodingException e) {
					Window.alert(this.getClass().getName()+" : "+"DecodingError: "+e);
				} catch (final Exception e) {
					Window.alert(this.getClass().getName()+" : "+"GeneralError: "+e);
				}
			}
		});
	}
}
