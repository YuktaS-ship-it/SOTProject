package com.railway.TicketManagement.controller;
import com.railway.TicketManagement.dto.LoginDTO;
import com.railway.TicketManagement.dto.UserDTO;
import com.railway.TicketManagement.entities.Booking;
import com.railway.TicketManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint for user registration (Sign Up).
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        // Register new user
        UserDTO createdUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Endpoint for user login.
     */
//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> login(@RequestParam String email,
//                                         @RequestParam String password) {
//        UserDTO loggedInUser = userService.login(email, password);
//        return ResponseEntity.ok(loggedInUser);
//    }
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO) {
        UserDTO loggedInUser = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(loggedInUser);
    }

    /**
     * Endpoint for retrieving user info by user ID.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{id}/myBookings")
    public List<Booking> getUserBookings(@PathVariable Integer id) {
        return userService.getBookingsByUserId(id);
    }
}

