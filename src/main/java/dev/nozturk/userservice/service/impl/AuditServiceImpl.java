package dev.nozturk.userservice.service.impl;

import dev.nozturk.userservice.repository.entity.RequestLog;
import dev.nozturk.userservice.service.AuditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<RequestLog> retrieve(String userName, LocalDate startDate, LocalDate endDate, int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RequestLog> query = criteriaBuilder.createQuery(RequestLog.class);
        Root<RequestLog> root = query.from(RequestLog.class);

        List<Predicate> predicates = new ArrayList<>();
        if (startDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), startDate.atStartOfDay()));
        }
        if (endDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), endDate.plusDays(1).atStartOfDay()));
        }
        if (userName != null) {
            predicates.add(criteriaBuilder.equal(root.get("username"), userName));
        }

        query.where(predicates.toArray(new Predicate[0]));

//        query.orderBy(criteriaBuilder.desc(root.get("timestamp")));

        TypedQuery<RequestLog> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();

    }
}
