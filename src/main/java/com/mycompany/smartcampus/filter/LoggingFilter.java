/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.filter;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author naduniameesha
 */

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    
    private static final Logger LOG = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException {

        LOG.info("Incoming Request: "
                + requestCtx.getMethod() + " "
                + requestCtx.getUriInfo().getRequestUri());
    }

    @Override
    public void filter(ContainerRequestContext requestCtx,
                       ContainerResponseContext responseCtx) throws IOException {

        LOG.info("Outgoing Response: Status = " + responseCtx.getStatus());
    }
}
