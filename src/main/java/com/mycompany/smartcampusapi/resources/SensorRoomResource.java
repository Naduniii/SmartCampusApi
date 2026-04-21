/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.resources;

import com.mycompany.smartcampus.model.Room;
import com.mycompany.smartcampus.repo.MockDatabase;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author naduniameesha
 */

@Path("/rooms")
public class SensorRoomResource {
    
    private static Map<String, Room> roomData = MockDatabase.ROOM_STORE;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAllRooms() {
        return Response.ok(roomData)
            .type(MediaType.APPLICATION_JSON)
            .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewRoom(Room roomPayload) {

        String roomIdentifier = UUID.randomUUID().toString();
        roomPayload.setRoomId(roomIdentifier);

        // Ensure sensor list is initialized
        if (roomPayload.getSensorIds() == null) {
            roomPayload.setSensorIds(new ArrayList<>());
        }

        roomData.put(roomIdentifier, roomPayload);

        return Response.status(Response.Status.CREATED)
                .entity(roomPayload)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchRoom(@PathParam("roomId") String roomId) {

        Room existingRoom = roomData.get(roomId);

        if (existingRoom == null) {
            throw new ResourceNotFoundException("Requested room does not exist");
        }

        return Response.ok(existingRoom).build();
    }
    
    
    @DELETE
    @Path("/{roomId}")
    public Response removeRoom(@PathParam("roomId") String roomId) {

        Room existingRoom = roomData.get(roomId);

        if (existingRoom == null) {
            throw new ResourceNotFoundException("Requested room does not exist");
        }

        // Prevent deletion if sensors are still linked
        if (existingRoom.getSensorIds() != null && !existingRoom.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Cannot delete room because sensors are still assigned");
        }

        roomData.remove(roomId);

        return Response.ok("Room successfully removed").build();
    }

    
}
