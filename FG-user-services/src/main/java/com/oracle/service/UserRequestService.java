package com.oracle.service;

import com.oracle.model.UserRequest;
import com.oracle.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRequestService {

    @Autowired
    private UserRequestRepository requestRepo;

    // Create new request
    public UserRequest createRequest(UserRequest request) {
        request.setStatus("PENDING");
        return requestRepo.save(request);
    }

    // Get all requests
    public List<UserRequest> getAllRequests() {
        return requestRepo.findAll();
    }

    // Get requests by status
    public List<UserRequest> getRequestsByStatus(String status) {
        return requestRepo.findByStatus(status);
    }

    // Approve request (update status + optionally update user role)
    public UserRequest approveRequest(Long requestId) {
        Optional<UserRequest> reqOpt = requestRepo.findById(requestId);
        if (reqOpt.isPresent()) {
            UserRequest request = reqOpt.get();
            request.setStatus("APPROVED");
            return requestRepo.save(request);
        }
        return null;
    }

    // Reject request
    public UserRequest rejectRequest(Long requestId) {
        Optional<UserRequest> reqOpt = requestRepo.findById(requestId);
        if (reqOpt.isPresent()) {
            UserRequest request = reqOpt.get();
            request.setStatus("REJECTED");
            return requestRepo.save(request);
        }
        return null;
    }
}
