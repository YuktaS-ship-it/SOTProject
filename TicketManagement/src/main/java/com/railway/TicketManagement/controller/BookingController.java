package com.railway.TicketManagement.controller;

import com.railway.TicketManagement.dto.BookingRequestDTO;
import com.railway.TicketManagement.dto.BookingResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.railway.TicketManagement.dto.BookingDTO;
import com.railway.TicketManagement.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin
@Validated
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/allbookings")
    public ResponseEntity<List<BookingResponseDTO>> showAllBookings() {
        return ResponseEntity.ok(bookingService.showAllBookings());
    }

    @PostMapping("/addnewbooking")
    public ResponseEntity<BookingResponseDTO> addNewBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        System.out.println(bookingRequestDTO.toString());
        bookingService.addNewBooking(bookingRequestDTO);
        return ResponseEntity.ok().build();
    }
}
