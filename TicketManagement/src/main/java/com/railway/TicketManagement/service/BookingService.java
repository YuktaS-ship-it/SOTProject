package com.railway.TicketManagement.service;

import com.railway.TicketManagement.dto.BookingDTO;
import com.railway.TicketManagement.dto.BookingRequestDTO;
import com.railway.TicketManagement.dto.BookingResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    List<BookingResponseDTO> showAllBookings();
    ResponseEntity<Void> addNewBooking(BookingRequestDTO bookingRequestDTO);

}
