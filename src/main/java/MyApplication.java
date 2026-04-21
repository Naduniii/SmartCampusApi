
import com.mycompany.smartcampus.exception.GlobalExceptionMapper;
import com.mycompany.smartcampus.exception.JsonParseExceptionMapper;
import com.mycompany.smartcampus.exception.LinkedResourceNotFoundExceptionMapper;
import com.mycompany.smartcampus.exception.NotFoundExceptionMapper;
import com.mycompany.smartcampus.exception.ResourceNotFoundExceptionMapper;
import com.mycompany.smartcampus.exception.RoomNotEmptyExceptionMapper;
import com.mycompany.smartcampus.exception.SensorUnavailableExceptionMapper;
import com.mycompany.smartcampus.filter.LoggingFilter;
import com.mycompany.smartcampusapi.resources.DiscoveryResource;
import com.mycompany.smartcampusapi.resources.SensorResource;
import com.mycompany.smartcampusapi.resources.SensorRoomResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author naduniameesha
 */

@ApplicationPath("/api/v1")

public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> registeredClasses = new HashSet<>();
        registeredClasses.add(DiscoveryResource.class);
        registeredClasses.add(SensorRoomResource.class);
        registeredClasses.add(SensorResource.class);
        registeredClasses.add(RoomNotEmptyExceptionMapper.class);
        registeredClasses.add(LinkedResourceNotFoundExceptionMapper.class);
        registeredClasses.add(SensorUnavailableExceptionMapper.class);
        registeredClasses.add(GlobalExceptionMapper.class);
        registeredClasses.add(LoggingFilter.class);
        registeredClasses.add(JsonParseExceptionMapper.class);
        registeredClasses.add(ResourceNotFoundExceptionMapper.class);
        registeredClasses.add(NotFoundExceptionMapper.class);
        
        return registeredClasses;
    }
}

