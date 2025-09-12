package com.oracle.controller;

import com.oracle.model.UserRequest;
import com.oracle.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class UserRequestController {

    @Autowired
    private UserRequestService requestService;

    // Create a new role change request
    @PostMapping
    public ResponseEntity<UserRequest> createRequest(@RequestBody UserRequest request) {
        UserRequest savedRequest = requestService.createRequest(request);
        return ResponseEntity.ok(savedRequest);
    }

    // Get all requests
    @GetMapping
    public ResponseEntity<List<UserRequest>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    // Get all requests by status (PENDING / APPROVED / REJECTED)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserRequest>> getRequestsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(requestService.getRequestsByStatus(status));
    }

    // Approve request
    @PutMapping("/{id}/approve")
    public ResponseEntity<UserRequest> approveRequest(@PathVariable Long id) {
        UserRequest updatedRequest = requestService.approveRequest(id);
        if (updatedRequest != null) {
            return ResponseEntity.ok(updatedRequest);
        }
        return ResponseEntity.notFound().build();
    }

    // Reject request
    @PutMapping("/{id}/reject")
    public ResponseEntity<UserRequest> rejectRequest(@PathVariable Long id) {
        UserRequest updatedRequest = requestService.rejectRequest(id);
        if (updatedRequest != null) {
            return ResponseEntity.ok(updatedRequest);
        }
        return ResponseEntity.notFound().build();
    }
}
