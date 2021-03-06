package com.glueball.snoop.module.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.JsonEncoderDecoder.DecodingException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.glueball.snoop.module.main.model.ServerMessage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

/**
 * Helper class to communicate with the server over json.
 * 
 * @author karesz
 * @param <T>
 *            the of the entity to send/receive.
 */
public final class SnoopRequest<T> {

    /**
     * ServerMessage codec interface declaration.
     * 
     * @author karesz
     */
    interface MessageCodec extends JsonEncoderDecoder<ServerMessage> {
    }

    /**
     * ServerMessage codec instance.
     */
    private final MessageCodec messageCodec = GWT.create(MessageCodec.class);

    /**
     * Generic codec.
     */
    private final JsonEncoderDecoder<T> codec;

    /**
     * Constructor.
     *
     * @param codec
     *            the codec of the entity type to send/receive.
     */
    public SnoopRequest(final JsonEncoderDecoder<T> codec) {

        this.codec = codec;
    }

    /**
     * Sends a restful json request to server and handles the response.
     *
     * @param url
     *            the url of the restful request.
     * @param callback
     *            the response handler callback.
     */
    public void request(final String url, final SnoopRequestCallback<T> callback) {

        final Resource resource = new Resource(url);
        resource.get().send(new JsonCallback() {

            @Override
            public void onSuccess(final Method method, final JSONValue response) {

                try {

                    if (response.isObject().containsKey("serverMessage")) {

                        callback.onMessage(messageCodec.decode(response));
                    } else {

                        callback.onSuccess(codec.decode(response));
                    }
                } catch (DecodingException e) {

                    Window.alert(this.getClass().getName() + " : "
                            + "DecodingError: " + e);
                } catch (Exception e) {

                    Window.alert(this.getClass().getName() + " : "
                            + "GeneralError: " + e);
                }
            }

            @Override
            public void onFailure(Method method, Throwable exception) {

                Window.alert(this.getClass().getName() + " : "
                        + "CommunicationFailure: " + exception);
            }
        });

    }
}
