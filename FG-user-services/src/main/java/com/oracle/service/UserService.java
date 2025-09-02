package com.oracle.service;

import com.oracle.model.User;
import com.oracle.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component 
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get user by ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public float getBalanceById(Long userid) {
    	User user = userRepository.findByUserId(userid).get(); 
    	return user.getCurrentBalance();
    }

    // Get user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Get users by role
    public List<User> getUsersByRole(String roleId) {
        return userRepository.findAllByRoleId(roleId);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update existing user
    public User updateUser(Long userId, User user) {
        return userRepository.findByUserId(userId).map(existing -> {
            existing.setUsername(user.getUsername());
            existing.setEmail(user.getEmail());
            existing.setPassword(user.getPassword());
            existing.setRoleId(user.getRoleId());
            return userRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    // Delete user
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

	public void updateBalance(Long id, float newBalance) {
    	User user = userRepository.findByUserId(id).get(); 	
    	user.setCurrentBalance(newBalance);
	}
}