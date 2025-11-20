package com.freshandfix.service;

import com.freshandfix.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();

    public User register(User user) {
        if (users.containsKey(user.getEmail())) {
            throw new RuntimeException("User already exists with this email");
        }
        users.put(user.getEmail(), user);
        return user;
    }

    public User login(String email, String password) {
        User user = users.get(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }
        
        String sessionToken = generateSessionToken();
        userSessions.put(sessionToken, email);
        return user;
    }

    public void logout(String token) {
        userSessions.remove(token);
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    private String generateSessionToken() {
        return "session_" + System.currentTimeMillis() + "_" + Math.random();
    }

    public boolean validateSession(String token) {
        return userSessions.containsKey(token);
    }

    public Optional<User> getUserBySession(String token) {
        String email = userSessions.get(token);
        return email != null ? Optional.ofNullable(users.get(email)) : Optional.empty();
    }
}