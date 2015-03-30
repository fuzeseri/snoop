package com.glueball.snoop.module.util;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.module.main.model.ServerMessage;

public interface SnoopRequestCallback<T> {

    public void onSuccess(T entity);

    public void onMessage(ServerMessage message);
}
