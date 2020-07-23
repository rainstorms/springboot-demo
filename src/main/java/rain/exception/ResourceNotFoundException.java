package rain.exception;

import java.util.Map;

public class ResourceNotFoundException extends BaseException {
    
    public ResourceNotFoundException(Map<String, Object> data) {
        super(ExceptionErrorStatus.RESOURCE_NOT_FOUND, data);
    }
}
