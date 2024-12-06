package com.example.zokalocabackend.features.visits.presentation;

import com.example.zokalocabackend.features.campsites.domain.Campsite;
import com.example.zokalocabackend.features.campsites.presentation.mappers.CampsiteMapper;
import com.example.zokalocabackend.features.campsites.services.CampsiteService;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.mappers.BranchMapper;
import com.example.zokalocabackend.features.usermanagement.services.BranchService;
import com.example.zokalocabackend.features.usermanagement.services.UserBranchService;
import com.example.zokalocabackend.features.visits.Visit;
import com.example.zokalocabackend.features.visits.VisitService;
import com.example.zokalocabackend.features.visits.presentation.requests.CreateVisitRequest;
import com.example.zokalocabackend.features.visits.presentation.requests.UpdateVisitRequest;
import com.example.zokalocabackend.features.visits.presentation.responses.GetVisitWithBranchAndCampsiteResponse;
import com.example.zokalocabackend.features.visits.presentation.responses.GetVisitWithBranchResponse;
import com.example.zokalocabackend.features.visits.presentation.responses.GetVisitWithCampsiteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitController {
    private final CampsiteService campsiteService;
    private final VisitService visitService;
    private final BranchService branchService;
    private final UserBranchService userBranchService;

    @GetMapping("/campsite/{campsiteId}")
    public ResponseEntity<List<GetVisitWithBranchResponse>> getAllVisitsByCampsiteId(@PathVariable String campsiteId) {
        if (!campsiteService.existsCampsiteById(campsiteId)) {
            throw new NoSuchElementException("Campsite not found");
        }

        List<Visit> visits = visitService.getAllVisitsByCampsiteId(campsiteId);
        List<GetVisitWithBranchResponse> response = visits.stream()
                .map(visit -> {
                    Branch branch = branchService.getBranchById(visit.getBranchId());
                    return VisitMapper.toGetVisitWithBranchResponse(visit, BranchMapper.toBranchCollectionItemDTO(branch));
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<GetVisitWithCampsiteResponse>> getAllVisitsByBranchId(@PathVariable String branchId) {
        if (!branchService.existsBranchById(branchId)) {
            throw new NoSuchElementException("Branch not found");
        }

        List<Visit> visits = visitService.getAllVisitsByBranchId(branchId);
        List<GetVisitWithCampsiteResponse> response = visits.stream()
                .map(visit -> {
                    Campsite campsite = campsiteService.getCampsiteById(visit.getCampsiteId());
                    double rating = visitService.getAverageRatingByCampsiteId(visit.getCampsiteId());
                    return VisitMapper.toGetVisitWithCampsiteResponse(visit, CampsiteMapper.toGetCampsiteResponse(campsite, rating));
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<GetVisitWithBranchAndCampsiteResponse> getVisitById(@PathVariable String visitId) {
        Visit visit = visitService.getVisitById(visitId);
        Branch branch = branchService.getBranchById(visit.getBranchId());
        Campsite campsite = campsiteService.getCampsiteById(visit.getCampsiteId());
        double rating = visitService.getAverageRatingByCampsiteId(visit.getCampsiteId());
        GetVisitWithBranchAndCampsiteResponse response = VisitMapper.toGetVisitWithBranchAndCampsiteResponse(visit, BranchMapper.toBranchCollectionItemDTO(branch), CampsiteMapper.toGetCampsiteResponse(campsite, rating));
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<?> createVisit(@Valid @RequestBody CreateVisitRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        User loggedInUser = (User) userDetails;

        if (!campsiteService.existsCampsiteById(request.campsiteId())) {
            throw new NoSuchElementException("Campsite not found");
        } else if (!branchService.existsBranchById(request.branchId())) {
            throw new NoSuchElementException("Branch not found");
        } else if (!userBranchService.existsUserBranchByUserIdAndBranchId(loggedInUser.getId(), request.branchId())) {
            throw new IllegalArgumentException("User is not associated with the branch");
        }

        Visit visit = VisitMapper.toVisit(null, request);
        visitService.createVisit(visit);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{visitId}")
    public ResponseEntity<?> updateVisit(@PathVariable String visitId, @Valid @RequestBody UpdateVisitRequest request) {
        Visit existingVisit = visitService.getVisitById(visitId);
        Visit newVisit = VisitMapper.toVisit(existingVisit.getId(), request, existingVisit.getBranchId(), existingVisit.getCampsiteId());
        visitService.updateVisit(visitId, newVisit);
        return ResponseEntity.ok().build();
    }
}
