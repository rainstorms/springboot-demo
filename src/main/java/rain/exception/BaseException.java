package rain.exception;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class BaseException extends RuntimeException {
    private final ExceptionErrorStatus error;
    private final HashMap<String, Object> data = new HashMap<>();

    public BaseException(ExceptionErrorStatus error, Map<String, Object> data) {
        super(error.getMessage());
        this.error = error;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    protected BaseException(ExceptionErrorStatus error, Map<String, Object> data, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public ExceptionErrorStatus getError() {
        return error;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
