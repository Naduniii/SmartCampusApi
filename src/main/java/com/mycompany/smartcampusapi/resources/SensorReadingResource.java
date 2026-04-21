/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.resources;

import com.mycompany.smartcampus.exception.ResourceNotFoundException;
import com.mycompany.smartcampus.exception.SensorUnavailableException;
import com.mycompany.smartcampus.model.Sensor;
import com.mycompany.smartcampus.model.SensorReading;
import com.mycompany.smartcampus.repo.MockDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author naduniameesha
 */

public class SensorReadingResource {

    private String currentSensorId;

    // Shared data storage
    private static Map<String, List<SensorReading>> readingStore = MockDatabase.SENSOR_READING_STORE;
    private static Map<String, Sensor> sensorStore = MockDatabase.SENSOR_STORE;

    public SensorReadingResource(String currentSensorId) {
        this.currentSensorId = currentSensorId;
    }

    // GET readings for a specific sensor
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchReadings() {

        if (!sensorStore.containsKey(currentSensorId)) {
            throw new ResourceNotFoundException("Requested sensor does not exist");
        }

        List<SensorReading> resultList
                = SensorReadingResource.readingStore.getOrDefault(currentSensorId, new ArrayList<>());

        return Response.ok(resultList).build();
    }

    // POST new reading for a specific sensor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReading(SensorReading readingPayload) {

        Sensor targetSensor = sensorStore.get(currentSensorId);

        if (targetSensor == null) {
            throw new ResourceNotFoundException("Requested sensor does not exist");
        }

        if ("MAINTENANCE".equals(targetSensor.getSensorStatus())) {
            throw new SensorUnavailableException("Sensor is currently unavailable due to maintenance");
        }

        readingPayload.setReadingId(UUID.randomUUID().toString());
        readingPayload.setTimeStamp(System.currentTimeMillis());

        // Initialize list if not present
        List<SensorReading> sensorReadingList = readingStore.get(currentSensorId);

        if (sensorReadingList == null) {
            sensorReadingList = new ArrayList<>();
            readingStore.put(currentSensorId, sensorReadingList);
        }

        sensorReadingList.add(readingPayload);

        // Update latest sensor value
        targetSensor.setSensorValue(readingPayload.getReadingValue());

        return Response.status(Response.Status.CREATED)
                .entity(readingPayload)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
