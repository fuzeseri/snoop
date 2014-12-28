package com.glueball.snoop.module.util;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.JsonEncoderDecoder.DecodingException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class SnoopRequest<T> {
		
	private final JsonEncoderDecoder<T> codec;

	public SnoopRequest(final JsonEncoderDecoder<T> codec) {
		this.codec = codec;
	}

	public void getEntity(final String url, final String queryString, final SnoopRequestCallback<T> callback) {
		
		final Resource resource = new Resource(url + "/" + queryString);
		resource.get().send(new JsonCallback() {
			public void onSuccess(final Method method, final JSONValue response) {
				try {
					callback.onSuccess((T)codec.decode(response));
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
}
