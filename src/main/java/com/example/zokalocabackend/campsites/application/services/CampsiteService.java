package com.example.zokalocabackend.campsites.application.services;

import com.example.zokalocabackend.campsites.domain.Building;
import com.example.zokalocabackend.campsites.domain.Campsite;
import com.example.zokalocabackend.campsites.domain.CampsiteFilter;
import com.example.zokalocabackend.campsites.domain.Field;
import com.example.zokalocabackend.campsites.persistence.CampsiteRepository;
import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampsiteService {
    private final CampsiteRepository campsiteRepository;

    @Autowired
    public CampsiteService(CampsiteRepository campsiteRepository) {
        this.campsiteRepository = campsiteRepository;
    }

    public Page<Campsite> getAllCampsites(Pageable pageable, CampsiteFilter filter) {
        return campsiteRepository.findAllWithFilters(pageable, filter);
    }

    public Campsite getCampsiteById(String id) {
        return campsiteRepository.findById(id).orElseThrow();
    }

    public void createCampsite(Campsite campsite) {
        if (campsiteRepository.existsByNameIgnoreCase(campsite.getName())) {
            throw new DuplicateResourceException("Campsite already exists");
        }

        if (campsite instanceof Field field) {
            field.setSizeSquareMeters(field.getSizeSquareMeters());
        } else if (campsite instanceof Building building) {
            building.setNumOfRooms(building.getNumOfRooms());
            building.setNumOfCommonAreas(building.getNumOfCommonAreas());
        }

        campsiteRepository.save(campsite);
    }

    public void updateCampsite(String id, Campsite campsite) {
        Campsite campsiteToUpdate = campsiteRepository.findById(id).orElseThrow();
        campsiteToUpdate.setName(campsite.getName());
        campsiteToUpdate.setDescription(campsite.getDescription());
        campsiteToUpdate.setAddress(campsite.getAddress());
        campsiteToUpdate.setPersonLimit(campsite.getPersonLimit());
        campsiteToUpdate.setPrice(campsite.getPrice());
        campsiteToUpdate.setArrivalTime(campsite.getArrivalTime());
        campsiteToUpdate.setDepartureTime(campsite.getDepartureTime());
        campsiteToUpdate.setNumOfToilets(campsite.getNumOfToilets());
        campsiteToUpdate.setNumOfShowers(campsite.getNumOfShowers());
        campsiteToUpdate.setNumOfWaterSources(campsite.getNumOfWaterSources());
        campsiteToUpdate.setSurroundings(campsite.getSurroundings());
        campsiteToUpdate.setExternalSources(campsite.getExternalSources());
        campsiteToUpdate.setFacilities(campsite.getFacilities());
        campsiteToUpdate.setCampGroundId(campsite.getCampGroundId());

        if (campsite instanceof Field) {
            ((Field) campsiteToUpdate).setSizeSquareMeters(((Field) campsite).getSizeSquareMeters());
        } else if (campsiteToUpdate instanceof Building) {
            ((Building) campsiteToUpdate).setNumOfRooms(((Building) campsite).getNumOfRooms());
            ((Building) campsiteToUpdate).setNumOfCommonAreas(((Building) campsite).getNumOfCommonAreas());
        }

        Campsite existingCampsite = campsiteRepository.findByNameIgnoreCase(campsite.getName());

        if (existingCampsite != null && !existingCampsite.getId().equals(id)) {
            throw new DuplicateResourceException("Another campsite already has the same name");
        }

        campsiteRepository.save(campsiteToUpdate);
    }
}
