package com.glueball.snoop.module.util;

import com.glueball.snoop.module.main.model.ServerMessage;

public interface SnoopRequestCallback<T> {

    public void onSuccess(T entity);

    public void onMessage(ServerMessage message);
}
