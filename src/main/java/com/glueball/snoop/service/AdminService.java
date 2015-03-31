package com.glueball.snoop.service;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Service get/set the properties of the server.
 *
 * @author karesz
 */
@Path("/admin")
public class AdminService {

    /**
     * Hello (echo) service.
     *
     * @param name
     *            name
     * @return hello name
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hello/{name}")
    public Response hello(@PathParam("name") final String name) {

        return Response.ok("Hello " + name, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Ping service.
     *
     * @return pong
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ping")
    public Response ping() {

        return Response.ok("pong", MediaType.APPLICATION_JSON).build();
    }
}
