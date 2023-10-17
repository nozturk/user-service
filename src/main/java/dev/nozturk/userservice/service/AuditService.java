package dev.nozturk.userservice.service;

import dev.nozturk.userservice.repository.entity.RequestLog;

import java.time.LocalDate;
import java.util.List;

public interface AuditService {
    List<RequestLog> retrieve(String userName, LocalDate startDate, LocalDate endDate, int page, int size);
}
