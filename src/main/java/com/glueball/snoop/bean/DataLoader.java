package com.glueball.snoop.bean;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.glueball.snoop.entity.DocumentPath;
import com.glueball.snoop.entity.NetworkShare;
import com.glueball.snoop.entity.NetworkShares;
import com.glueball.snoop.parser.MimeFileextMap;
import com.glueball.snoop.parser.ParserMap;
import com.glueball.snoop.visitor.DbLoaderVisitor;

/**
 * Periodically checks the directories on the disk or network file shares and
 * updates the document index statuses in the ralational databse. This code
 * checks if the snoop has a parser for the mime-type of the file and skip if
 * has not.
 *
 * @author karesz
 */
public final class DataLoader {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager.getLogger(DataLoader.class);

    /**
     * Document paths database service.
     */
    @Autowired
    private DocumentPathBean docPathBean;

    /**
     * Setter methond of the docPathBean field.
     *
     * @param pDdocPathBean
     *            the DocumentPathBean instance.
     */
    public void setDocPathBean(final DocumentPathBean pDdocPathBean) {

        this.docPathBean = pDdocPathBean;
    }

    /**
     * This map contains all of the file parser objects as value. Key is the
     * mime type.
     */
    @Autowired
    private ParserMap parserMap;

    /**
     * Setter methods of the ParserMap field.
     *
     * @param pParserMap
     *            the ParserMap object.
     */
    public void setParserMap(final ParserMap pParserMap) {

        this.parserMap = pParserMap;
    }

    /**
     * This map contains the related file extensions for the specific
     * mime-types.
     */
    @Autowired
    private MimeFileextMap mimeFileextMap;

    /**
     * Setter method of the mimeFileextMap field.
     *
     * @param pMimeFileextMap
     *            MimeFileextMap instance.
     */
    public void setMimeFileextMap(final MimeFileextMap pMimeFileextMap) {

        this.mimeFileextMap = pMimeFileextMap;
    }

    /**
     * Path of the xml file containing the set of network shares to scan and
     * index its' content's.
     */
    private String sharesXml;

    /**
     * Setter method of the sharesXml field.
     *
     * @param source
     *            path of the shares.xml file.
     */
    public void setSharesXml(final String source) {

        this.sharesXml = source;
    }

    /**
     * Constructor.
     *
     * @param pSharesXml
     *            path of the shares.xml file.
     */
    public DataLoader(final String pSharesXml) {

        this.sharesXml = pSharesXml;
    }

    /**
     * Scheduled task method. Goes through the shares and tree walks on the sub
     * directories. Checks all the files if it is parsable by the snoop and
     * updates the statuses of the documents in the relational database.
     */
    @Scheduled(
            fixedDelay = 300000)
    public void load() {

        for (final NetworkShare share : getShares()) {

            LOG.info("Loading netwok share: " + share.getName());
            LOG.info("Remote path: " + share.getRemotePath());
            LOG.info("Local  path: " + share.getLocalPath());

            final List<DocumentPath> docs = new ArrayList<DocumentPath>();
            final FileVisitor<Path> visitor = new DbLoaderVisitor(docs,
                    parserMap, mimeFileextMap, share);

            try {

                final String path = !StringUtils.isEmpty(
                        share.getLocalPath()) ? share.getLocalPath() : share
                        .getRemotePath();

                Files.walkFileTree(Paths.get(path), visitor);
                this.docPathBean.updateDocuments(share.getName(), docs);
            } catch (final IOException e) {

                LOG.error("IO ERROR when discovering files");
                LOG.debug(e.getMessage());
            }
        }
    }

    /**
     * Unmarshall the network share instances from shares.xml file.
     *
     * @return list of NetworkShare instances.
     */
    private List<NetworkShare> getShares() {

        final List<NetworkShare> shares = new ArrayList<NetworkShare>();
        try {

            final JAXBContext jaxbContext = JAXBContext
                    .newInstance(NetworkShares.class);
            final Unmarshaller jaxbUnmarshaller = jaxbContext
                    .createUnmarshaller();
            final NetworkShares netShares = (NetworkShares) jaxbUnmarshaller
                    .unmarshal(new File(sharesXml));
            shares.addAll(netShares.getShares());
        } catch (JAXBException e) {

            LOG.error("IO ERROR when unmarshalling shares.xml");
            LOG.debug(e.getMessage());
        }

        return shares;
    }
}
