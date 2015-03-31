package com.glueball.snoop.module.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.module.main.model.ServerMessage;

/**
 * Callback interface to handle the server reponse.
 *
 * @author karesz
 * @param <T>
 *            the type of the received object.
 */
public interface SnoopRequestCallback<T> {

    /**
     * Callback method to call on successful request/response.
     *
     * @param entity
     *            the entity received.
     */
    void onSuccess(final T entity);

    /**
     * Callback method to call on unsuccessful request/response.
     *
     * @param message
     *            the ServeMessage object received.
     */
    void onMessage(final ServerMessage message);
}
