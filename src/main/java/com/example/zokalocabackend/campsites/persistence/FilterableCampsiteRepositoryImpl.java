package com.example.zokalocabackend.campsites.persistence;

import com.example.zokalocabackend.campsites.domain.Campsite;
import com.example.zokalocabackend.campsites.domain.CampsiteFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class FilterableCampsiteRepositoryImpl implements FilterableCampsiteRepository {
    private final MongoTemplate mongoTemplate;

    public FilterableCampsiteRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Campsite> findAllWithFilters(Pageable pageable, CampsiteFilter filter) {
        Query query = new Query().with(pageable);

        if (filter.getName() != null && !filter.getName().isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(filter.getName(), "i"));
        }
        if (filter.getCampsiteType() != null && !filter.getCampsiteType().isEmpty()) {
            query.addCriteria(Criteria.where("_class").regex(filter.getCampsiteType(), "i"));
        }
        if (filter.getMinDistanceInKm() != null) {
            query.addCriteria(Criteria.where("address.distanceInKm").gte(filter.getMinDistanceInKm()));
        }
        if (filter.getMaxDistanceInKm() != null) {
            query.addCriteria(Criteria.where("address.distanceInKm").lte(filter.getMaxDistanceInKm()));
        }
        if (filter.getNumOfPeople() != null) {
            query.addCriteria(Criteria.where("personLimit.minimum").lte(filter.getNumOfPeople()));
            query.addCriteria(Criteria.where("personLimit.maximum").gte(filter.getNumOfPeople()));
        }
        if (filter.getFacilityIds() != null && !filter.getFacilityIds().isEmpty()) {
            query.addCriteria(Criteria.where("facilities").all(filter.getFacilityIds()));
        }
        if (filter.getMinNumOfToilets() != null) {
            query.addCriteria(Criteria.where("numOfToilets").gte(filter.getMinNumOfToilets()));
        }
        if (filter.getMinNumOfShowers() != null) {
            query.addCriteria(Criteria.where("numOfShowers").gte(filter.getMinNumOfShowers()));
        }
        if (filter.getMinNumOfWaterSources() != null) {
            query.addCriteria(Criteria.where("numOfWaterSources").gte(filter.getMinNumOfWaterSources()));
        }
        if (filter.getMinNumOfRooms() != null) {
            query.addCriteria(Criteria.where("numOfRooms").gte(filter.getMinNumOfRooms()));
        }
        if (filter.getMinNumOfCommonAreas() != null) {
            query.addCriteria(Criteria.where("numOfCommonAreas").gte(filter.getMinNumOfCommonAreas()));
        }
        if (filter.getMinSizeSquareMeters() != null) {
            query.addCriteria(Criteria.where("sizeSquareMeters").gte(filter.getMinSizeSquareMeters()));
        }

        List<Campsite> campsites = mongoTemplate.find(query, Campsite.class);
        long count = mongoTemplate.count(query.skip(0).limit(0), Campsite.class);

        return new PageImpl<>(campsites, pageable, count);
    }
}