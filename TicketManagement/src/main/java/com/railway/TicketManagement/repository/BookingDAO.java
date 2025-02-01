package com.railway.TicketManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.railway.TicketManagement.entities.Booking;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingDAO extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b JOIN FETCH b.tickets WHERE b.user.userId = :userId")
    List<Booking> findByUser_UserId(Long userId);
}
