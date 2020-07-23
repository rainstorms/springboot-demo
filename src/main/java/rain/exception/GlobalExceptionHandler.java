package rain.exception;

import com.github.bingoohuang.utils.servlet.RequestDumper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 理论上 本系统的中的 所有异常都是 BaseException 的子类
     */
    @ExceptionHandler(BaseException.class)
    public ErrorResponse handleAppException(BaseException e, HttpServletRequest request) {
        log.error("error:" + e.getMessage(), e);
        return new ErrorResponse(e, request.getRequestURI());
    }

    /**
     * 为了防止 有新人不知道用 系统异常   还是加上 原生异常
     *
     * @param e
     */
    @ExceptionHandler({Throwable.class})
    public ErrorResponse handleThrowable(Throwable e, HttpServletRequest request, HttpServletResponse res) {
        e.printStackTrace();
        String message = StringUtils.isEmpty(e.getMessage()) ? e.getClass().getName() : e.getMessage();
        log.error("error:" + message);
        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ErrorResponse("500", message, request.getRequestURI());
    }
}
