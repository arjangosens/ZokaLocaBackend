package com.example.zokalocabackend.features.visits.presentation;

import com.example.zokalocabackend.features.campsites.domain.Campsite;
import com.example.zokalocabackend.features.campsites.services.CampsiteService;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.services.BranchService;
import com.example.zokalocabackend.features.usermanagement.services.UserBranchService;
import com.example.zokalocabackend.features.visits.Visit;
import com.example.zokalocabackend.features.visits.VisitService;
import com.example.zokalocabackend.features.visits.presentation.requests.ModifyVisitRequest;
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
    public ResponseEntity<?> getAllVisitsByCampsiteId(@PathVariable String campsiteId) {
        if (!campsiteService.existsCampsiteById(campsiteId)) {
            throw new NoSuchElementException("Campsite not found");
        }

        List<Visit> visits = visitService.getAllVisitsByCampsiteId(campsiteId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<?> getAllVisitsByBranchId(@PathVariable String branchId) {
        if (!branchService.existsBranchById(branchId)) {
            throw new NoSuchElementException("Branch not found");
        }
        List<Visit> visits = visitService.getAllVisitsByBranchId(branchId);

        return ResponseEntity.ok(visits);
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<?> getVisitById(@PathVariable String visitId) {
        Visit visit = visitService.getVisitById(visitId);
        return ResponseEntity.ok(visit);
    }

    @PostMapping()
    public ResponseEntity<?> createVisit(@Valid @RequestBody ModifyVisitRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        User loggedInUser = (User) userDetails;
        Campsite campsite = campsiteService.getCampsiteById(request.campsiteId());
        Branch branch = branchService.getBranchById(request.branchId());

        if (!userBranchService.existsUserBranchByUserIdAndBranchId(loggedInUser.getId(), branch.getId())) {
            throw new IllegalArgumentException("User is not associated with the branch");
        }

        Visit visit = VisitMapper.toVisit(null, request, branch, campsite);
        visitService.createVisit(visit);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{visitId}")
    public ResponseEntity<?> updateVisit(@PathVariable String visitId) {
        return ResponseEntity.ok().build();
    }
}
