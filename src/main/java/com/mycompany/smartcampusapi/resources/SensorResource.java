/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.resources;

import com.mycompany.smartcampus.model.Room;
import com.mycompany.smartcampus.model.Sensor;
import com.mycompany.smartcampus.repo.MockDatabase;
import static com.mycompany.smartcampus.repo.MockDatabase.ROOM_STORE;
import static com.mycompany.smartcampus.repo.MockDatabase.SENSOR_STORE;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author naduniameesha
 */

@Path("/sensors")
public class SensorResource {

    private static Map<String, Sensor> sensorStore = MockDatabase.SENSOR_STORE;
    private static Map<String, Room> roomStore = MockDatabase.ROOM_STORE;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensorPayload) {

        // CHECK: ensure assigned room exists
        if (sensorPayload.getRoomId() == null || !roomStore.containsKey(sensorPayload.getRoomId())) {
            throw new LinkedResourceNotFoundException("Invalid room reference: room not found");
        }

        String sensorId = UUID.randomUUID().toString();
        sensorPayload.setSensorId(sensorId);

        sensorStore.put(sensorId, sensorPayload);

        // Link sensor to its room
        Room parentRoom = roomStore.get(sensorPayload.getRoomId());
        parentRoom.getSensorIds().add(sensorId);

        return Response.status(Response.Status.CREATED)
                .entity(sensorPayload)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
    // GET /sensors (optional type filtering)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchSensors(@QueryParam("type") String sensorType) {

        List<Sensor> filteredSensors = new ArrayList<>();

        for (Sensor sensor : sensorStore.values()) {
            if (sensorType == null || sensor.getSensorType().equalsIgnoreCase(sensorType)) {
                filteredSensors.add(sensor);
            }
        }

        return Response.ok(filteredSensors).build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}

