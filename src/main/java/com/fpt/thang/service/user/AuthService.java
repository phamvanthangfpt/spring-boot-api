package com.fpt.thang.service.user;

import com.fpt.thang.model.entity.user.User;
import com.fpt.thang.model.repository.user.IUserRepository;
import com.fpt.thang.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null; // User not found
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(username); // Generate JWT token
        }
        return null; // Invalid credentials
    }
}
