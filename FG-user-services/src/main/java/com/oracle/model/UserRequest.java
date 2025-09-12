package com.oracle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_requests")
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // match your User table style
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "user_id", nullable = false)  // foreign key reference to User.userId
    private Long userId;

    @Column(name = "requested_role", nullable = false)
    private String requestedRole;

    @Column(name = "status", nullable = false)
    private String status = "PENDING";  // default value
}
