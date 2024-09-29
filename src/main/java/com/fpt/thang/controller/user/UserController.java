package com.fpt.thang.controller.user;

import com.fpt.thang.model.dto.ApiResponse;
import com.fpt.thang.model.dto.LoginRequest;
import com.fpt.thang.model.entity.user.User;
import com.fpt.thang.service.user.AuthService;
import com.fpt.thang.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<User>>> getUser() {
        Iterable<User> users = userService.findAll();
        ApiResponse<Iterable<User>> response = new ApiResponse<>("success", users, null);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            ApiResponse<User> response = new ApiResponse<>("error", null, "Username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409 Conflict
        }
        User savedUser = userService.save(user);
        ApiResponse<User> response = new ApiResponse<>("success", savedUser, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (token != null) {
            ApiResponse<String> response = new ApiResponse<>("success", token, null);
            return ResponseEntity.ok(response); // Return wrapped JWT token
        } else {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", null, "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); // Return error response
        }
    }

}

