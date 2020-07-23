package rain.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestExceptionController {

    @GetMapping("/test-resource-exception")
    public void testResourceException() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", "2");
        throw new ResourceNotFoundException(map);
    }

    @GetMapping("/test-native-exception")
    public void testNativeException() {
        String a = null;
        System.out.println(a.length());
    }
}
