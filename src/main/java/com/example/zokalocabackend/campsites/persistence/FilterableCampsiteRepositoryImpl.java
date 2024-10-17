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

        addNameCriteria(query, filter);
        addCampsiteTypeCriteria(query, filter);
        addDistanceCriteria(query, filter);
        addNumOfPeopleCriteria(query, filter);
        addFacilityIdsCriteria(query, filter);
        addNumOfToiletsCriteria(query, filter);
        addNumOfShowersCriteria(query, filter);
        addNumOfWaterSourcesCriteria(query, filter);
        addNumOfRoomsCriteria(query, filter);
        addNumOfCommonAreasCriteria(query, filter);
        addSizeSquareMetersCriteria(query, filter);

        List<Campsite> campsites = mongoTemplate.find(query, Campsite.class);
        long count = mongoTemplate.count(query.skip(0).limit(0), Campsite.class);

        return new PageImpl<>(campsites, pageable, count);
    }

    private void addNameCriteria(Query query, CampsiteFilter filter) {
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(filter.getName(), "i"));
        }
    }

    private void addCampsiteTypeCriteria(Query query, CampsiteFilter filter) {
        if (filter.getCampsiteType() != null && !filter.getCampsiteType().isEmpty()) {
            query.addCriteria(Criteria.where("_class").regex(filter.getCampsiteType(), "i"));
        }
    }

    private void addDistanceCriteria(Query query, CampsiteFilter filter) {
        if (filter.getMinDistanceInKm() != null && filter.getMaxDistanceInKm() != null) {
            query.addCriteria(new Criteria().andOperator(
                    Criteria.where("address.distanceInKm").gte(filter.getMinDistanceInKm()),
                    Criteria.where("address.distanceInKm").lte(filter.getMaxDistanceInKm())
            ));
        } else {
            if (filter.getMinDistanceInKm() != null) {
                query.addCriteria(Criteria.where("address.distanceInKm").gte(filter.getMinDistanceInKm()));
            }
            if (filter.getMaxDistanceInKm() != null) {
                query.addCriteria(Criteria.where("address.distanceInKm").lte(filter.getMaxDistanceInKm()));
            }
        }
    }

    private void addNumOfPeopleCriteria(Query query, CampsiteFilter filter) {
        if (filter.getNumOfPeople() != null) {
            query.addCriteria(Criteria.where("personLimit.minimum").lte(filter.getNumOfPeople()));
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("personLimit.maximum").gte(filter.getNumOfPeople()),
                    Criteria.where("personLimit.maximum").is(0)
            ));
        }
    }

    private void addFacilityIdsCriteria(Query query, CampsiteFilter filter) {
        if (filter.getFacilityIds() != null && !filter.getFacilityIds().isEmpty()) {
            query.addCriteria(Criteria.where("facilities").all(filter.getFacilityIds()));
        }
    }

    private void addNumOfToiletsCriteria(Query query, CampsiteFilter filter) {
        if (filter.getMinNumOfToilets() != null) {
            query.addCriteria(Criteria.where("numOfToilets").gte(filter.getMinNumOfToilets()));
        }
    }

    private void addNumOfShowersCriteria(Query query, CampsiteFilter filter) {
        if (filter.getMinNumOfShowers() != null) {
            query.addCriteria(Criteria.where("numOfShowers").gte(filter.getMinNumOfShowers()));
        }
    }

    private void addNumOfWaterSourcesCriteria(Query query, CampsiteFilter filter) {
        if (filter.getMinNumOfWaterSources() != null) {
            query.addCriteria(Criteria.where("numOfWaterSources").gte(filter.getMinNumOfWaterSources()));
        }
    }

    private void addNumOfRoomsCriteria(Query query, CampsiteFilter filter) {
        if (filter.getMinNumOfRooms() != null) {
            query.addCriteria(Criteria.where("numOfRooms").gte(filter.getMinNumOfRooms()));
        }
    }

    private void addNumOfCommonAreasCriteria(Query query, CampsiteFilter filter) {
        if (filter.getMinNumOfCommonAreas() != null) {
            query.addCriteria(Criteria.where("numOfCommonAreas").gte(filter.getMinNumOfCommonAreas()));
        }
    }

    private void addSizeSquareMetersCriteria(Query query, CampsiteFilter filter) {
        if (filter.getMinSizeSquareMeters() != null) {
            query.addCriteria(Criteria.where("sizeSquareMeters").gte(filter.getMinSizeSquareMeters()));
        }
    }
}