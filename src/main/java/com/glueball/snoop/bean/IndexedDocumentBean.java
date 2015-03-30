package com.glueball.snoop.bean;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

import com.glueball.snoop.entity.IndexedDocument;

public interface IndexedDocumentBean {

    void unLockUpdateState(final List<IndexedDocument> haveToIndexList);

    List<IndexedDocument> haveToIndex(final int docNum);
}
