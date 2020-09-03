package com.smida.registry.repository;

import com.smida.registry.domain.Registry;
import com.smida.registry.dto.RegistryFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class RegistryRepositoryCustomImpl implements RegistryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Registry> findByFilter(RegistryFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("select r from Registry r where 1=1");

        if (filter.getStatuses() != null) {
            sb.append(" and r.status in (:statuses)");
        }

        if (filter.getUsreou() != null
                && !filter.getUsreou().trim().isEmpty()) {
            sb.append(" and r.usreou = :usreou");
        }

        TypedQuery<Registry> query = entityManager
                .createQuery(sb.toString(), Registry.class);

        if (filter.getStatuses() != null) {
            query.setParameter("statuses", filter.getStatuses());
        }

        if (filter.getUsreou() != null
                && !filter.getUsreou().trim().isEmpty()) {
            query.setParameter("usreou", filter.getUsreou());
        }

        return query.getResultList();
    }
}
