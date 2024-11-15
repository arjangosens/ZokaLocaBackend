package com.example.zokalocabackend.features.usermanagement.persistence;

import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserFilter;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

public class FilterableUserRepositoryImpl implements FilterableUserRepository {
    private final MongoTemplate mongoTemplate;

    public FilterableUserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<User> findAllWithFilters(Pageable pageable, UserFilter filter) {
        Query query = new Query().with(pageable);
        addFirstNameCriteria(query, filter);
        addLastNameCriteria(query, filter);
        addFullNameCriteria(query, filter);
        addEmailCriteria(query, filter);
        addRoleCriteria(query, filter);
        addBranchesCriteria(query, filter);

        List<User> users = mongoTemplate.find(query, User.class);
        long count = mongoTemplate.count(query.skip(0).limit(0), User.class);

        return new PageImpl<>(users, pageable, count);
    }

    private void addFirstNameCriteria(Query query, UserFilter filter) {
        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            query.addCriteria(Criteria.where("firstName").regex(filter.getFirstName(), "i"));
        }
    }

    private void addLastNameCriteria(Query query, UserFilter filter) {
        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            query.addCriteria(Criteria.where("lastName").regex(filter.getLastName(), "i"));
        }
    }

    private void addEmailCriteria(Query query, UserFilter filter) {
        if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
            query.addCriteria(Criteria.where("email").regex(filter.getEmail(), "i"));
        }
    }

    private void addRoleCriteria(Query query, UserFilter filter) {
        if (filter.getRole() != null) {
            query.addCriteria(Criteria.where("role").is(filter.getRole()));
        }
    }

    private void addBranchesCriteria(Query query, UserFilter filter) {
        if (filter.getBranchId() != null && !filter.getBranchId().isEmpty()) {
            query.addCriteria(Criteria.where("branches.$id").is(new ObjectId(filter.getBranchId())));
        }
    }

    private void addFullNameCriteria(Query query, UserFilter filter) {
        if (filter.getFullName() != null && !filter.getFullName().isEmpty()) {
            String[] fullNameParts = filter.getFullName().split(" ");

            if (fullNameParts.length <= 1) {
                query.addCriteria(new Criteria().orOperator(
                        Criteria.where("firstName").regex(filter.getFullName(), "i"),
                        Criteria.where("lastName").regex(filter.getFullName(), "i")
                ));
            } else {
                String lastNamePart = String.join(" ", Arrays.copyOfRange(fullNameParts, 1, fullNameParts.length));
                query.addCriteria(new Criteria().andOperator(
                        Criteria.where("firstName").regex(fullNameParts[0], "i"),
                        Criteria.where("lastName").regex(lastNamePart, "i")
                ));
            }

        }
    }
}
