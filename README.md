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

# Report (Answers)

## PART 1 : Service Architecture & Setup

### Q: Lifecycle of a JAX-RS Resource class

In your code for JAX-RS resource classes such as DiscoveryResource and SensorResource, the default lifecycle for the resource class is a request-scoped lifecycle. That means when a new HTTP request comes in, the resource class gets instantied (a new instance of the resource qualification) and thereafter once the response is sent, the resource qualification is destroyed (removed from memory).

Since resource qualifications are not singletons, if you store data in the instance variables, that data will be lost after the point at which the instance variables were last used (because the next request will have a new instance). To circumvent this, you utilize static in-memory data storage (for example: private static Map roomData) in your code.While this allows for data persistence across all requests, in a typical multi-threaded environment, static in-memory data structures would require synchronisation (for example: use of a ConcurrentHashMap) to avoid race conditions occurring from concurrent reads/writes.

### Q: Benefits of Hypermedia (HATEOAS)

DiscoveryResource uses Hypermedia as a way to provide you with a mapping of your AP’s primary resources.In this case, you get to see what resources are available through hyperlinks such as “rooms” -> ”/api/v1/rooms.” This method has the added benefit of being self-discoverable for client developers.Client developers have the ability to navigate through DAOs via dynamical link, rather than relying on static documents and or external documents that may no longer be accurate.

## PART 2: Room Management

### Q: Implications of Returning IDs vs. Full objects

The roomData object returned to you by the API has a room object returned for every room in your fetchAllRooms method.

Returning full objects has the benefit of reducing the amount of subsequent “follow-up” requests that would otherwise need to be made by the client for room detail requests which converges network bandwidth and reduces overall processing time.

Returning only IDs does save on bandwidth consumption and is faster to initially load, but requires the client to make multiple additional requests to retrieve the individual details for each room which can cause the API to become chattier.

### Q: Idempotency of the DELETE operation

The idempotency of the removeRoom operation through the SensorRoomResource is evident when a user deletes an already existing room. After a successful delete, the system returns a success message. When the user attempts to perform an identical delete request again, the existingRoom in roomData has previously been deleted and the delete attempt will produce a ResourceNotFoundException and the status code will be changed from 200 to 404.

Regardless of the status code change occurring between 200 ans 404, the actual state of the server has not changed - the room will still be deleted - therefore, the definition of idempotence has been satisfied.


## PART 3: Sensor Operations & Linking

### Q: Consequences of Mismatched Content Types

The addSensor function explicitly annotates the method using @Consumes(Meadia type.APPLICATION_JSON).When a client tries to send in a data format other than that specified by the addSensor method, such as text/plain or application/xml, JAX_RS will automatically intercept their request. The request will be rejected with an HTTP 415 status code indicating that the media type being transmitted is not one which this resource can process.

### Q: Query Parameters vs. URL Paths for Filtering

You have the ability to filter by using the @QueryParam(“type”)  annotation on your sensors and this method provides a better filtering methods fro the following reasons:

URL Paths are meant to identify a unique resource (e.g. /sensors{id})

Query Parameters modify how a collection of resources are presented or subset (e.g. type=CO2&status=ACTIVE) which allows for multiple filtering options without having to use a hard and confusing hierarchy for URLs.

## PART 4: Deep Nesting with Sub-Resources

### Q: Benefit of the Sub-Resource Locator Pattern

The path for your SensorResource includes a sub-resource locator with the function {sensorId}/ readings that will produce an instance of SensorReadingResource. This is an example of modularization.Rather than using a single “fat” controller class to handle all potential subdivisions, you can delegate to a separate class that handles all things related to reading. As the complexity of your API expands, it also becomes easier to maintain, test, and grow your codebase.

## PART 5: Advanced Error Handling & Logging

### Q: Semantics of HTTP 422 vs. 404

When a sensor is supplied with a roomId that does not exist, your LinkedResourceNotFoundExceptionMapper maps this to an HTTP 422 Unprocessable Entity because the resource is not present in the database. This is an accurate representation of what happened because there is no problem with the request URL and the JSON payload is well-formed (syntactically valid); however the roomID referenced does not exist in the database thus making it semantically invalid. A 404 response would imply that the URL was invalid whereas the 422 response identifies the logical error of there being an incorrect reference to data.

### Q: Risks of Exposing Stack Trances

Your GlobalExceptionMapper does not allow raw Java stack traces to be displayed; instead, it returns a generic JSON ErrorResponse with an HTTP status of 500.It is highly insecure to expose stack traces because they reveal detailed information about the internal workings of an application, including class names, library versions, and database schemas. If an attacker receives this information, they can use it to identify vulnerabilities in your applications software stack or the configuration of your server and that  knowledge to launch a more focused attack against you.

### Q: Advantages of JAX_RS Filters

The use of ContainerRequestFilter and ContainerResponseFilter in conjunction with LogginFilter allows you to log across crosscutting areas. When logging the request and response at a central point, you can log both with ease since you will not have to individually insert the repetitive Logger.info() calls into each resource method. This results in cleaner code that is less likely to contain omissions from manual insertions instead of using a single set of filters for your logging efforts.





