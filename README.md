#  Smart Campus Sensor & Room Management API

##  Overview
This project is a**RESTful API** built using **JAX-RS (Jersey)** to simulate a Smart Campus system.  
It provides endpoints to manage:

-  Rooms  
-  Sensors  
-  Sensor Readings  

The application uses **in-memory storage (HashMap / ArrayList)** and follows core REST principles such as stateless communication, proper HTTP methods, and structured JSON responses.

---

##  Technology Stack

- Java  
- JAX-RS (Jersey)  
- Maven  
- Apache Tomcat  
- Jackson (JSON processing)  
- In-memory data structures  

---

##  Base URL
```
http://localhost:8080/api/v1
```


---

##  API Endpoints

###  API Discovery
```
GET /api/v1
```

### Rooms
```
GET /api/v1/rooms
POST /api/v1/rooms
GET /api/v1/rooms/{roomId}
DELETE /api/v1/rooms/{roomId}
```

###  Sensors
```
GET /api/v1/sensors
POST /api/v1/sensors
GET /api/v1/sensors?type=CO2
```

###  Sensor Readings
```
GET /api/v1/sensors/{sensorId}/readings
POST /api/v1/sensors/{sensorId}/readings
```

---

##  Relationships

- A Room can contain multiple Sensors  
- A Sensor belongs to one Room  
- A Sensor can store multiple Readings  
---

##  Setup & Run

### Prerequisites
- Java JDK 11+
- Maven
- Apache Tomcat
- IDE (NetBeans recommended)

---

###  Clone Repository
```bash
git clone https://github.com/binuriii/smartcampus.git
cd smartcampus 
```
### Build Project
```
mvn clean install
```
### Deploy
Run using IDE (NetBeans → Run Project)
OR
Deploy .war file into Tomcat webapps/
### Run Server
Start Tomcat and open:
```
http://localhost:8080/api/v1
```
## Sample cURL Requests
 ### Get API Info
```
curl http://localhost:8080/api/v1
```
 ### Create Room
```
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"roomName":"Lab A","maxCapacity":30}'
```
### Get All Rooms
```
curl http://localhost:8080/api/v1/rooms
```
### Create Sensor
```
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"sensorType":"CO2","sensorStatus":"WORKING","sensorValue":400,"roomId":"ROOM_ID"}'
```
### Filter Sensors
```
curl "http://localhost:8080/api/v1/sensors?type=CO2"
```
### Add Sensor Reading
```
curl -X POST http://localhost:8080/api/v1/sensors/SENSOR_ID/readings \
-H "Content-Type: application/json" \
-d '{"readingValue":420}'
```

## Business Rules

- A room cannot be deleted if it has sensors
  
- A sensor must reference a valid room
  
- Sensors in MAINTENANCE cannot accept readings
  
- Adding a reading updates the sensor’s current value

## Error Handling

The API uses custom exception mappers to return structured JSON errors.

```
Code	  Description

400   	Bad Request (invalid JSON)
403	    Sensor unavailable
404	    Resource not found
409	    Conflict (room not empty)
422    	Invalid reference
500    	Internal server error
```

## Logging

All requests and responses are logged using:

- ContainerRequestFilter
- ContainerResponseFilter

Logs include:

- HTTP Method + URL
- Response status code
  
 Design Highlights
 
- Stateless REST architecture
  
- Sub-resource locator for nested routes
  
- Query parameter filtering
  
- Centralized exception handling
  
- In-memory data simulation
  
## Project Structure
```
src/main/java/
 ├── resources/
 ├── model/
 ├── repo/
 ├── exception/
 ├── filter/
 └── dto/
```




