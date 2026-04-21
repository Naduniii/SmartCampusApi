/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.resources;

import com.mycompany.smartcampus.model.DiscoveryResponse;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author naduniameesha
 */

@Path("/")

public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApiInfo() {

        Map<String, String> apiEndpoints = new HashMap<>();
        apiEndpoints.put("rooms", "/api/v1/rooms");
        apiEndpoints.put("sensors", "/api/v1/sensors");

        DiscoveryResponse apiResponse = new DiscoveryResponse(
                "v1",
                "admin@smartcampus.com",
                apiEndpoints
        );

        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
