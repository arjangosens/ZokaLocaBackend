package com.example.zokalocabackend.features.campsites.persistence;

import com.example.zokalocabackend.features.campsites.domain.Campsite;
import com.example.zokalocabackend.features.campsites.domain.CampsiteFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterableCampsiteRepository {
    Page<Campsite> findAllWithFilters(Pageable pageable, CampsiteFilter filter);
}
