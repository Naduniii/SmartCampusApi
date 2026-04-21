/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.repo;

import com.mycompany.smartcampus.model.Room;
import com.mycompany.smartcampus.model.Sensor;
import com.mycompany.smartcampus.model.SensorReading;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author naduniameesha
 */

public class MockDatabase {

    public static final Map<String, Room> ROOM_STORE = new HashMap<>();

    public static final Map<String, Sensor> SENSOR_STORE = new HashMap<>();

    public static final Map<String, List<SensorReading>> SENSOR_READING_STORE = new HashMap<>();

    static {

        // Rooms
        Room labRoom = new Room(UUID.randomUUID().toString(), "Hall 1", 10);
        Room lectureRoom = new Room(UUID.randomUUID().toString(), "Main Hall", 100);

        ROOM_STORE.put(labRoom.getRoomId(), labRoom);
        ROOM_STORE.put(lectureRoom.getRoomId(), lectureRoom);

        // Sensors
        Sensor co2Sensor = new Sensor(UUID.randomUUID().toString(), "CO2", "WORKING", 400.0, labRoom.getRoomId());
        Sensor tempSensor = new Sensor(UUID.randomUUID().toString(), "TEMP", "MAINTANANCE", 25.0, lectureRoom.getRoomId());

        SENSOR_STORE.put(co2Sensor.getSensorId(), co2Sensor);
        SENSOR_STORE.put(tempSensor.getSensorId(), tempSensor);

        // Link sensors to rooms
        labRoom.getSensorIds().add(co2Sensor.getSensorId());
        lectureRoom.getSensorIds().add(tempSensor.getSensorId());

        // Readings
        List<SensorReading> co2Readings = new ArrayList<>();
        co2Readings.add(new SensorReading(
                UUID.randomUUID().toString(),
                System.currentTimeMillis(),
                400.0
        ));

        SENSOR_READING_STORE.put(co2Sensor.getSensorId(), co2Readings);

        List<SensorReading> tempReadings = new ArrayList<>();
        tempReadings.add(new SensorReading(
                UUID.randomUUID().toString(),
                System.currentTimeMillis(),
                25.0
        ));

        SENSOR_READING_STORE.put(tempSensor.getSensorId(), tempReadings);
    }
}
