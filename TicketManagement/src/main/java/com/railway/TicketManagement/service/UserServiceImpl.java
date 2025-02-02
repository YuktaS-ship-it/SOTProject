package com.railway.TicketManagement.service;
import com.railway.TicketManagement.dto.UserDTO;
import com.railway.TicketManagement.entities.Booking;
import com.railway.TicketManagement.entities.User;
import com.railway.TicketManagement.repository.BookingDAO;
import com.railway.TicketManagement.repository.UserRepository;
import com.railway.TicketManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingDAO bookingRepository;
    /**
     * Registers a new user if the email is not already taken.
     */
    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        // Check if user with given email already exists
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already registered with this email: " + userDTO.getEmail());
        }

        // Create and save new User
        User newUser = mapDtoToEntity(userDTO);
        User savedUser = userRepository.save(newUser);

        // Return DTO
        return mapEntityToDto(savedUser);
    }

    /**
     * Logs in a user by validating their email and password.
     */
    @Override
    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or user does not exist"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // Return DTO upon successful login
        return mapEntityToDto(user);
    }

    /**
     * Retrieves user info by userId
     */
    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return mapEntityToDto(user);
    }

    @Override
    public List<Booking> getBookingsByUserId(Integer userId) {
        return  bookingRepository.findByUser_UserId(userId);
    }

    // Utility method to map DTO to Entity
    private User mapDtoToEntity(UserDTO userDTO) {
        return User.builder()
                .userId(Math.toIntExact(userDTO.getUserId()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .contactNumber(userDTO.getContactNumber())
                .password(userDTO.getPassword())
                .build();
    }

    // Utility method to map Entity to DTO
    private UserDTO mapEntityToDto(User user) {
        return UserDTO.builder()
                .userId(Long.valueOf(user.getUserId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .password(user.getPassword())
                .build();
    }
}
