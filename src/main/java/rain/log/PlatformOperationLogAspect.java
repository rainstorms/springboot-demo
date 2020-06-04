package rain.log;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
public class PlatformOperationLogAspect {
    // 注入插入数据库的Service
//    @Autowired
//    private PlatformOperationLogService service;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(rain.log.IPlatformOperationLog)")
    public void operationLog() {
    }

    /**
     * 处理完请求，返回内容
     *
     * @param
     */
    @AfterReturning(value = "operationLog()", returning = "returnData")
    public void afterReturning(JoinPoint joinPoint, Object returnData) {
        //保存日志
        val builder = PlatformOperationLog.builder();
//        builder.id(Id.next());

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取操作
        IPlatformOperationLog iLog = signature.getMethod().getAnnotation(IPlatformOperationLog.class);
        if (iLog != null) {
            String value = iLog.value();
            builder.operation(value);

            if (StringUtils.equalsAny(value, "")) {
//                RestResp restResp = (RestResp) returnData;
//                String remark = (String) restResp.getData();
//                builder.remark(remark);
            }
        }

//        if (null == AclContext.getUser()) return;
//
//        //获取用户
//        builder.userId(AclContext.getUserId());
//        builder.unitId(AclContext.getUnitId());

        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        builder.ip(IPUtils.getIpAddress(request));

        //调用service保存 log实体类到数据库
//        service.addPlatformOperationLog(builder.build());
    }

}
