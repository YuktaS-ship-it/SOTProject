package com.railway.TicketManagement.repository;

import com.railway.TicketManagement.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

// TicketDAO.java
public interface TicketDAO extends JpaRepository<Ticket, Long> {
    List<Ticket> findByBooking_BookingId(Long bookingId);
}