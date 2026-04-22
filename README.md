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

In code for JAX-RS resource classes such as DiscoveryResource and SensorResource, the default lifecycle for the resource class is a request-scoped lifecycle. That means when a new HTTP request comes in, the resource class gets instantied (a new instance of the resource qualification) and thereafter once the response is sent, the resource qualification is destroyed (removed from memory).

Since resource qualifications are not singletons, if store data in the instance variables, that data will be lost after the point at which the instance variables were last used (because the next request will have a new instance). To circumvent this,  utilize static in-memory data storage (for example: private static Map roomData) in  code.While this allows for data persistence across all requests, in a typical multi-threaded environment, static in-memory data structures would require synchronisation (for example: use of a ConcurrentHashMap) to avoid race conditions occurring from concurrent reads/writes.

### Q: Benefits of Hypermedia (HATEOAS)

HATEOAS complies with the architecture of REST services and offers HTTP clients the ability to dynamically discover how to navigate through all actions available via an API, because each HTTP response from a server contains hypermedia links .In my implementation of HATEOAS, the API includes an endpoint to return a map of primary resource URLs for a client to use for navigation.For instance, the GET/api/v1/discover endpoint returns a map of primary resources such as room or sensor URLs.A client who has no previous knowledge of the API’s URL REST structure can use these links to explore the API.

There are three major benefits of using hypermedia links to discover available HTTP actions versus providing only static documentation of the actions.First, if the server changes a resource URL from /api/v1/rooms to /api/v2/rooms, clients that follow the links in the responses will not have to update their applications and thus, they will remain functional;however, clients that have hardcoded the resource URL will break. Second, using hypermedia links between clients and servers will reduce the coupling between clients and servers. Third, the API will be self-describing because each response will include the hypermedia links needed for clients to navigate throughout an entire API.Finally, the system can evolve independently from clients updates by using hypermedia links as a method for clients to navigate their way through the API.


## PART 2: Room Management

### Q: Implications of Returning IDs vs. Full objects

The roomData object returned to the API has a room object returned for every room in the fetch AllRooms method.

Returning full objects has the benefit of reducing the amount of subsequent “follow-up” requests that would otherwise need to be made by the client for room detail requests which converges network bandwidth and reduces overall processing time.

Returning only IDs does save on bandwidth consumption and is faster to initially load, but requires the client to make multiple additional requests to retrieve the individual details for each room which can cause the API to become chattier.

### Q: Idempotency of the DELETE operation

The idempotency of the removeRoom operation through the SensorRoomResource is evident when a user deletes an already existing room. After a successful delete, the system returns a success message. When the user attempts to perform an identical delete request again, the existingRoom in roomData has previously been deleted and the delete attempt will produce a ResourceNotFoundException and the status code will be changed from 200 to 404.

Regardless of the status code change occurring between 200 and 404, the actual state of the server has not changed - the room will still be deleted - therefore, the definition of idempotence has been satisfied.


## PART 3: Sensor Operations & Linking

### Q: Consequences of Mismatched Content Types

The addSensor function explicitly annotates the method using @Consumes(Meadia type.APPLICATION_JSON).When a client tries to send in a data format other than that specified by the addSensor method, such as text/plain or application/xml, JAX_RS will automatically intercept their request. The request will be rejected with an HTTP 415 status code indicating that the MediaType being transmitted is not one which this resource can process.

### Q: Query Parameters vs. URL Paths for Filtering

Have the ability to filter by using the @QueryParam(“type”)  annotation on  sensors and this method provides a better filtering methods for the following reasons:

URL Paths are meant to identify a unique resource (e.g. /sensors{id})

Query Parameters modify how a collection of resources are presented or subset (e.g. type=CO2&status=ACTIVE) which allows for multiple filtering options without having to use a hard and confusing hierarchy for URLs.


## PART 4: Deep Nesting with Sub-Resources

### Q: Benefit of the Sub-Resource Locator Pattern

A sub-resource locator is an annotated method that uses the @Path annotation, but has no HTTP method that uses the @Path annotation, but has no HTTP method annotation (no @GET, @POST, etc.).It responds to a request with an instance of a different resource class, rather than directly meeting the needs of the request. JAX-RS then uses the actual HTTP methods from the returned resource to process the request and delegates the responsibility of processing that request to a separate class.In this implementation, I have a sub-resource locator at the path {sensorId}/readings that will return an instance of SensorReadingResource.All the endpoints that are related to readings (GET/ and POST) will be fully handled in that specific class.

The overall architecture of this is modular.Rather than having all the different nested Paths (like /sensors/{id}/readings/{rid}) inside one big controller class, the logic is separated into smaller classes that each have a well-defined responsibility. As the API grows, the resource classes will remain small, making it easier for you to test them independently and maintain them without impacting other, unrelated endpoints.



## PART 5: Advanced Error Handling & Logging

### Q: Semantics of HTTP 422 vs. 404

When a sensor is supplied with a roomId that does not exist, LinkedResourceNotFoundExceptionMapper maps this to an HTTP 422 Unprocessable Entity because the resource is not present in the database. This is an accurate representation of what happened because there is no problem with the request URL and the JSON payload is well-formed (syntactically valid); however the roomID referenced does not exist in the database thus making it semantically invalid. A 404 response would imply that the URL was invalid whereas the 422 response identifies the logical error of there being an incorrect reference to data.

### Q: Risks of Exposing Stack Trances

Exposed raw Java stack traces on API response presents a major security flaw since they provide sensitive information such as the class names, package structure,library version, method name, line numbers, and server file path on the system to a potential adversary.

A disclosure of framework version numbers, such as the version of jersey (e.g 2.32), could be correlated against publicly available data of common vulnerabilities and exposures (CVE) to assist an adversary in identifying software attack vulnerabilities that may be exploited. Class names and package structure (e.g.com.mycompany.smartcampus), which are exposed by the raw stack traces, reveal the internal structure of the implementation and allow an adversary to construct a more precise attack against the system. The server file path on the system that is also displayed in the raw stack trace allows an adversary to construct a more detailed map of the deployment environment for the purpose of further exploiting the software stack.

In this situation, the GlobalExceptionMapper will intercept all unhandled Throwable instances and return a generic JSON error response with a 500 status code (the HTTP status code equivalent of an internal server error).No internal information about the implementation will ever be exposed to a consumer of the API. All details regarding the error will be captured by calling exception.printStackTrace() providing detailed information about the error to the development team for troubleshooting purposes only.


### Q: Advantages of JAX_RS Filters

The use of ContainerRequestFilter and ContainerResponseFilter in conjunction with LoggingFilter allows  logging across crosscutting areas. When logging the request and response at a central point, and can log both with ease since  will not have to individually insert the repetitive Logger.info() calls into each resource method. This results in cleaner code that is less likely to contain omissions from manual insertions instead of using a single set of filters for your logging efforts.




