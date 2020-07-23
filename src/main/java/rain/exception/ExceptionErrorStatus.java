package rain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
public enum ExceptionErrorStatus {
    NOT_LOGIN("419", "未登录"),
    NOT_CUSTOM_EXCEPTION("500", "不是系统自定义异常"),
    RESOURCE_NOT_FOUND("409", "找不到该资源");

    @Getter
    private String status;
    @Getter
    private String message;

}
