package com.railway.TicketManagement.service;

import com.railway.TicketManagement.dto.UserDTO;
import com.railway.TicketManagement.entities.Booking;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO);

    UserDTO login(String email, String password);

    UserDTO getUserById(Long userId);

    List<Booking> getBookingsByUserId(Integer userId);
}

