package dev.nozturk.userservice.service.impl;

import dev.nozturk.userservice.repository.RequestLogRepository;
import dev.nozturk.userservice.repository.entity.RequestLog;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestLoggerService {

  @Autowired private RequestLogRepository requestLogRepository;

  public void logRequest(String url, String requestBody) {
    RequestLog requestLog = new RequestLog();
    requestLog.setResource(url);
    requestLog.setRequestBody(requestBody);
    requestLog.setTimestamp(new Date());
    requestLogRepository.save(requestLog);
  }
}
