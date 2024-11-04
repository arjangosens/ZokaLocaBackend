package com.example.zokalocabackend.features.usermanagement.persistence;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.BranchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class FilterableBranchRepositoryImpl implements FilterableBranchRepository {
    private final MongoTemplate mongoTemplate;

    public FilterableBranchRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Branch> findAllWithFilters(Pageable pageable, BranchFilter filter) {
        Query query = new Query().with(pageable);

        addNameCriteria(query, filter);

        List<Branch> branches = mongoTemplate.find(query, Branch.class);
        long count = mongoTemplate.count(query.skip(0).limit(0), Branch.class);
        return new PageImpl<>(branches, pageable, count);
    }

    private void addNameCriteria(Query query, BranchFilter filter) {
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(filter.getName(), "i"));
        }
    }
}
