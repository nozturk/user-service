package dev.nozturk.userservice.audit;

import dev.nozturk.userservice.auth.CustomUserDetails;
import dev.nozturk.userservice.repository.RequestLogRepository;
import dev.nozturk.userservice.repository.entity.RequestLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class AuditManager {
    private final RequestLogRepository repository;
    private final HttpServletRequest request;

    @Pointcut(value = "execution(* dev.nozturk.userservice.controller.*.*(..) )")
    public void AuditPointCut() {}

    @Around("AuditPointCut()")
    public Object AuditLogger(ProceedingJoinPoint p) throws Throwable {
        Object returnObject = p.proceed();
        try {
            String uriPath = request.getRequestURI().trim();
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            RequestLog log = new RequestLog();
            log.setResource(uriPath);
            log.setUsername(userDetails.getUsername());
            log.setTimestamp(new Date());
            log.setQueryParams(request.getQueryString());

            repository.save(log);

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
     return returnObject;
    }

    private String getRequestBody() {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            log.error("Unable to read request body");
            return "Unable to read request body";
        }
    }

}
