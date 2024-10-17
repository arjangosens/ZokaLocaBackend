package com.example.zokalocabackend.campsites.persistence;

import com.example.zokalocabackend.campsites.domain.Campsite;
import com.example.zokalocabackend.campsites.domain.CampsiteFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterableCampsiteRepository {
    Page<Campsite> findAllWithFilters(Pageable pageable, CampsiteFilter filter);
}
