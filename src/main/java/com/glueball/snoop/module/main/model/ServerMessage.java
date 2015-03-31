package com.glueball.snoop.module.main.model;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Entity class representing a message what the server can send to the client
 * application.
 * 
 * @author karesz
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "server_message")
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ServerMessage implements Serializable {

    /**
     * Message type ok.
     */
    public static final int MESSAGE_TYPE_OK = 0;

    /**
     * Message type error.
     */
    public static final int MESSAGE_TYPE_ERROR = 1;

    /**
     * Server message: the result set is empty.
     */
    public static final ServerMessage MESSAGE_NO_HITS =
            new ServerMessage(
                    MESSAGE_TYPE_ERROR,
                    "No documents found int the search term.");

    /**
     * Server message: the index is not ready yet.
     */
    public static final ServerMessage MESSAGE_INDEX_NOT_READY =
            new ServerMessage(
                    MESSAGE_TYPE_ERROR,
                    "The index is not ready yet. Please come back later.");

    /**
     * Server message: the server can not open the index.
     */
    public static final ServerMessage MESSAGE_CANT_OPEN_INDEX =
            new ServerMessage(
                    MESSAGE_TYPE_ERROR,
                    "Error opening index.");

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Message type.
     */
    private int messageType;

    /**
     * Message text.
     */
    private String message = "";

    /**
     * Constructor.
     */
    public ServerMessage() {

    }

    /**
     * Constructor.
     *
     * @param pMessageType
     *            Message type of the message.
     * @param pMessage
     *            Text of the message.
     */
    public ServerMessage(final int pMessageType, final String pMessage) {

        this.messageType = pMessageType;
        this.message = pMessage;
    }

    /**
     * @return the messageType
     */
    public int getMessageType() {

        return messageType;
    }

    /**
     * @param pMessageType
     *            the messageType to set
     */
    public void setMessageType(final int pMessageType) {

        this.messageType = pMessageType;
    }

    /**
     * @return the message
     */
    public String getMessage() {

        return message;
    }

    /**
     * @param pMessage
     *            the message to set
     */
    public void setMessage(final String pMessage) {

        this.message = pMessage;
    }

    /**
     * Method to convert numeric message type to text.
     *
     * @param pMessageType
     *            the numeric message type.
     * @return text name of the message type.
     */
    public static String getTypeText(final int pMessageType) {

        if (pMessageType == MESSAGE_TYPE_OK) {

            return "OK";
        }
        if (pMessageType == MESSAGE_TYPE_ERROR) {

            return "ERROR";
        }

        return "";
    }

    /**
     * Get the name of the icon file of a message type.
     *
     * @param messageType
     *            the numeric message type.
     * @return icon file name.
     */
    public static String getIcon(final int messageType) {

        if (messageType == MESSAGE_TYPE_OK) {

            return "ok.png";
        }

        if (messageType == MESSAGE_TYPE_ERROR) {

            return "error.png";
        }

        return "";
    }

    /**
     * @return true
     */
    public boolean isServerMessage() {

        return true;
    }
}
