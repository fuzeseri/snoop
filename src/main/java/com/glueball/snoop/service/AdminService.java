package com.glueball.snoop.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin")
public class AdminService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hello/{name}")
    public Response hello(@PathParam("name") String name) {

        return Response.ok("Hello " + name, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ping")
    public Response ping() {

        return Response.ok("pong", MediaType.APPLICATION_JSON).build();
    }
}
