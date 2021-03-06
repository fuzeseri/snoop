package com.glueball.snoop.service;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Service to provide file content over http.
 *
 * @author karesz
 */
@Path("/page")
public class FileService {

    /**
     * Provides a htmt with the request for the javasript content of the related
     * ui module.
     *
     * @param module
     *            the name of the ui module.
     * @return html with the modules javasript content.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{module}")
    public final Response getFile(@PathParam(
            value = "module") final String module) {

        final String retVal = "<!doctype html>\n"
                + "<html>\n"
                + "  <head>\n"
                + "    <meta http-equiv=\"content-type\" content=\"text/html; "
                + " charset=UTF-8\">\n"
                + "    <title>module</title>\n"
                + "  </head>\n"
                + "  <body>\n"
                + "    <iframe src=\"javascript:''\" "
                + " id=\"__gwt_historyFrame\" tabIndex='-1' style=\"position:"
                + "absolute;width:0;height:0;border:0\"></iframe>\n"
                + "	<script languge=\"javascript\" "
                + " src=\"com.glueball.snoop.module."
                + module + "Module.nocache.js\"></script>\n" + "  </body>\n"
                + "</html>\n";

        return Response.ok(retVal, MediaType.TEXT_HTML).build();
    }

    /**
     * Provides the text content of the requested file on the server.
     *
     * @param module
     *            the name of the ui module.
     * @param filename
     *            the name of the file requested.
     * @return the content of the file requested.
     * @throws IOException
     *             on unsuccessful file read.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{module}/{filename}")
    public final Response getFile(@PathParam(
            value = "module") final String module,
            @PathParam(
                    value = "filename") final String filename)
            throws IOException {

        final String filePath = "com.glueball.snoop.module." + module
                + "Module/" + filename;
        final String retVal = FileServerUtil.getContent(filePath);

        return Response.ok(retVal, MediaType.TEXT_HTML).build();
    }

    /**
     * Provides the content of the requested image.
     *
     * @param filename
     *            the name of the image file.
     * @return the content of the image as image/png mime-type.
     * @throws IOException
     *             on unsuccessful file read.
     */
    @GET
    @Produces("image/png")
    @Path("/image/{filename}")
    public final Response getImage(
            @PathParam(value = "filename") final String filename)
            throws IOException {

        final Resource res = new ClassPathResource("images/" + filename);

        return Response.ok(res.getInputStream(), "image/png").build();
    }

}
