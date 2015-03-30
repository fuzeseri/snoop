package com.glueball.snoop.bean;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
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

public class DataLoader {

    private static final Logger LOG = LogManager.getLogger(DataLoader.class);

    @Autowired
    private DocumentPathBean docPathBean;

    public void setDocPathBean(final DocumentPathBean docPathBean) {

        this.docPathBean = docPathBean;
    }

    @Autowired
    private ParserMap parserMap;

    public void setParserMap(final ParserMap parserMap) {

        this.parserMap = parserMap;
    }

    @Autowired
    private MimeFileextMap mimeFileextMap;

    public void setPMimeFileextMap(final MimeFileextMap _mimeFileextMap) {

        this.mimeFileextMap = _mimeFileextMap;
    }

    private String sharesXml;

    public void setSharesXml(final String source) {

        this.sharesXml = source;
    }

    public DataLoader() {

    }

    public DataLoader(final String _sharesXml) {

        this.sharesXml = _sharesXml;
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void load() {

        for (final NetworkShare share : getShares()) {

            LOG.info("Loading netwok share: " + share.getName());
            LOG.info("Remote path: " + share.getRemotePath());
            LOG.info("Local  path: " + share.getLocalPath());

            final List<DocumentPath> docs = new ArrayList<DocumentPath>();
            final FileVisitor<Path> visitor = new DbLoaderVisitor(docs, parserMap, mimeFileextMap, share);

            try {

                final String path = !StringUtils.isEmpty(share.getLocalPath()) ? share.getLocalPath() : share.getRemotePath();

                Files.walkFileTree(Paths.get(path), visitor);
                this.docPathBean.updateDocuments(share.getName(), docs);
            } catch (final IOException e) {

                LOG.error("IO ERROR when discovering files");
                LOG.debug(e.getMessage());
            }
        }
    }

    private List<NetworkShare> getShares() {

        final List<NetworkShare> shares = new ArrayList<NetworkShare>();
        try {

            final JAXBContext jaxbContext = JAXBContext.newInstance(NetworkShares.class);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            final NetworkShares netShares = (NetworkShares) jaxbUnmarshaller.unmarshal(new File(sharesXml));
            shares.addAll(netShares.getShares());
        } catch (JAXBException e) {

            LOG.error("IO ERROR when unmarshalling shares.xml");
            LOG.debug(e.getMessage());
        }

        return shares;
    }
}
