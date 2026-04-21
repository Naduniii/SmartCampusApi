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
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}

