package com.glueball.snoop.bean;

import java.util.List;

import com.glueball.snoop.entity.IndexedDocument;

public interface IndexedDocumentBean {

    void unLockUpdateState(final List<IndexedDocument> haveToIndexList);

    List<IndexedDocument> haveToIndex(final int docNum);
}
