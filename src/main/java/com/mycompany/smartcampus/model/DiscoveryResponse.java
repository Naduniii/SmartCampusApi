/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.model;

import java.util.Map;

/**
 *
 * @author naduniameesha
 */

public class DiscoveryResponse {

    private String apiVersion;
    private String supportContact;
    private Map<String, String> resourceLinks;

    public DiscoveryResponse() {}

    public DiscoveryResponse(String apiVersion, String supportContact, Map<String, String> resourceLinks) {
        this.apiVersion = apiVersion;
        this.supportContact = supportContact;
        this.resourceLinks = resourceLinks;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getSupportContact() {
        return supportContact;
    }

    public void setSupportContact(String supportContact) {
        this.supportContact = supportContact;
    }

    public Map<String, String> getResourceLinks() {
        return resourceLinks;
    }

    public void setResourceLinks(Map<String, String> resourceLinks) {
        this.resourceLinks = resourceLinks;
    }
}

    
