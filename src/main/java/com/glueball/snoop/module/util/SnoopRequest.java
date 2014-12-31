package com.glueball.snoop.module.util;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.JsonEncoderDecoder.DecodingException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.glueball.snoop.module.main.model.ServerMessage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class SnoopRequest<T> {

	interface MessageCodec extends JsonEncoderDecoder<ServerMessage> {
	}

	private final MessageCodec messageCodec = GWT.create(MessageCodec.class);

	private final JsonEncoderDecoder<T> codec;

	public SnoopRequest(final JsonEncoderDecoder<T> codec) {
		this.codec = codec;
	}

	public void request(final String url, final SnoopRequestCallback<T> callback) {
		
		final Resource resource = new Resource(url);
		resource.get().send(new JsonCallback() {

			public void onSuccess(final Method method, final JSONValue response) {

				try {

					if (response.isObject().containsKey("serverMessage")) {
						
						callback.onMessage((ServerMessage)messageCodec.decode(response));
					} else {

						callback.onSuccess((T)codec.decode(response));
					}
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
