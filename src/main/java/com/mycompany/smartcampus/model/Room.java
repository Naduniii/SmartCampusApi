/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author naduniameesha
 */

public class Room {
    
    private String roomId;
    private String roomName;
    private int maxCapacity;
    private List<String> sensorIds = new ArrayList<>();

    public Room() {}

    public Room(String roomId, String roomName, int maxCapacity) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxCapacity = maxCapacity;
    }
    
    public Room(String roomId, String roomName, int maxCapacity, List<String> sensorIds) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxCapacity = maxCapacity;
        this.sensorIds = sensorIds;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }
}
