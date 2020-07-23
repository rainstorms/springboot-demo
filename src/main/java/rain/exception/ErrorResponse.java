package rain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @AllArgsConstructor @NoArgsConstructor
public class ErrorResponse {
    private String status;
    private String message;
    private String path;
    private Map<String, Object> data;

    public ErrorResponse(BaseException ex, String path) {
        this(ex.getError().getStatus(), ex.getError().getMessage(), path, ex.getData());
    }

    public ErrorResponse(String status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
