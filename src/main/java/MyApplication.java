
import com.mycompany.smartcampusapi.resources.DiscoveryResource;
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
        
    
        return registeredClasses;
        
    
    }
}
