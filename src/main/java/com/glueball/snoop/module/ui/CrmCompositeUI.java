package com.glueball.snoop.module.ui;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.google.gwt.user.client.ui.Composite;

public abstract class CrmCompositeUI extends Composite {

    protected final Composite crmApp;

    // protected Widget widget;

    protected CrmCompositeUI(final Composite app) {

        this.crmApp = app;
    }

    protected Composite getCrmApp() {

        return crmApp;
    }

}
