/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.model;

/**
 *
 * @author naduniameesha
 */

public class Sensor {
    private String sensorId;
    private String sensorType;
    private String sensorStatus;
    private double sensorValue;
    private String roomId;

    public Sensor() {}

    public Sensor(String sensorId, String sensorType, String sensorStatus, double sensorValue, String roomId) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.sensorStatus = sensorStatus;
        this.sensorValue = sensorValue;
        this.roomId = roomId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorStatus() {
        return sensorStatus;
    }

    public void setSensorStatus(String sensorStatus) {
        this.sensorStatus = sensorStatus;
    }

    public double getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(double sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
