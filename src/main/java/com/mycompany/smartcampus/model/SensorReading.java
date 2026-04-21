/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.model;

/**
 *
 * @author naduniameesha
 */

public class SensorReading {
    
    private String readingId;
    private long timeStamp;
    private double readingValue;

    public SensorReading() {}
    
    public SensorReading(String readingId, long timeStamp, double readingValue) {
        this.readingId = readingId;
        this.timeStamp = timeStamp;
        this.readingValue = readingValue;
    }

    public String getReadingId() {
        return readingId;
    }

    public void setReadingId(String readingId) {
        this.readingId = readingId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getReadingValue() {
        return readingValue;
    }

    public void setReadingValue(double readingValue) {
        this.readingValue = readingValue;
    }
}
