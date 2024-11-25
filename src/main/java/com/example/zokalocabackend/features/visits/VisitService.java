package com.example.zokalocabackend.features.visits;

import com.example.zokalocabackend.utilities.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing visits.
 */
@RequiredArgsConstructor
@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final Validator validator;

    /**
     * Retrieves all visits by campsite ID.
     *
     * @param campsiteId the ID of the campsite
     * @return a list of visits for the specified campsite
     */
    public List<Visit> getAllVisitsByCampsiteId(String campsiteId) {
        return visitRepository.findAllByCampsiteId(campsiteId);
    }

    public double getAverageRatingByCampsiteId(String campsiteId) {
        List<Visit> visits = visitRepository.findAllByCampsiteId(campsiteId);
        return visits.stream().mapToInt(Visit::getRating).average().orElse(0);
    }

    /**
     * Retrieves all visits by branch ID.
     *
     * @param branchId the ID of the branch
     * @return a list of visits for the specified branch
     */
    public List<Visit> getAllVisitsByBranchId(String branchId) {
        return visitRepository.findAllByBranchId(branchId);
    }

    /**
     * Retrieves a visit by its ID.
     *
     * @param visitId the ID of the visit
     * @return the visit with the specified ID
     * @throws NoSuchElementException if no visit with the specified ID is found
     */
    public Visit getVisitById(String visitId) {
        return visitRepository.findById(visitId).orElseThrow();
    }

    /**
     * Creates a new visit.
     *
     * @param visit the visit to create
     * @throws ConstraintViolationException if the visit entity is invalid
     */
    public void createVisit(Visit visit) {
        ValidationUtils.validateEntity(visit, validator);
        visitRepository.save(visit);
    }

    /**
     * Updates an existing visit.
     *
     * @param visitId the ID of the visit to update
     * @param visit the visit with updated information
     * @throws NoSuchElementException if no visit with the specified ID is found
     * @throws ConstraintViolationException if the visit entity is invalid
     */
    public void updateVisit(String visitId, Visit visit) {
        Visit existingVisit = visitRepository.findById(visitId).orElseThrow();
        existingVisit.setArrivalDate(visit.getArrivalDate());
        existingVisit.setDepartureDate(visit.getDepartureDate());
        existingVisit.setRating(visit.getRating());
        existingVisit.setBranchId(visit.getBranchId());
        existingVisit.setCampsiteId(visit.getCampsiteId());
        existingVisit.setNumOfPeople(visit.getNumOfPeople());
        existingVisit.setPrice(visit.getPrice());
        existingVisit.setDescription(visit.getDescription());
        existingVisit.setPros(visit.getPros());
        existingVisit.setCons(visit.getCons());

        ValidationUtils.validateEntity(existingVisit, validator);
        visitRepository.save(existingVisit);
    }
}