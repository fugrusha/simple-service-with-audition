package com.smida.registry.repository;

import com.smida.registry.domain.Registry;
import com.smida.registry.dto.RegistryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class RegistryRepositoryCustomImpl implements RegistryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Registry> findByFilter(RegistryFilter filter, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select r from Registry r where 1=1");

        Query query = createQueryApplyingFilter(filter, pageable.getSort(), sb);

        applyingPageable(query, pageable);

        List<Registry> data = query.getResultList();

        long count = getCountOfRegistries(filter);
        return new PageImpl<>(data, pageable, count);
    }

    private Query createQueryApplyingFilter(RegistryFilter filter, Sort sort,
            StringBuilder sb) {

        if (filter.getStatuses() != null) {
            sb.append(" and r.status in (:statuses)");
        }

        if (filter.getUsreou() != null
                && !filter.getUsreou().trim().isEmpty()) {
            sb.append(" and r.usreou = :usreou");
        }

        if (sort != null && sort.isSorted()) {
            sb.append(" order by ");
            for (Sort.Order order : sort.toList()) {
                sb.append("r.")
                        .append(order.getProperty())
                        .append(" ")
                        .append(order.getDirection());
            }
        }

        Query query = entityManager.createQuery(sb.toString());

        if (filter.getStatuses() != null) {
            query.setParameter("statuses", filter.getStatuses());
        }

        if (filter.getUsreou() != null
                && !filter.getUsreou().trim().isEmpty()) {
            query.setParameter("usreou", filter.getUsreou());
        }

        return query;
    }

    private long getCountOfRegistries(RegistryFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(r) from Registry r where 1=1");
        Query query = createQueryApplyingFilter(filter, null, sb);
        Object o = query.getResultList().get(0);
        return ((Number) o).longValue();
    }

    private void applyingPageable(Query query, Pageable pageable) {
        if (pageable.isPaged()) {
            query.setMaxResults(pageable.getPageSize());
            query.setFirstResult((int) pageable.getOffset());
        }
    }
}
