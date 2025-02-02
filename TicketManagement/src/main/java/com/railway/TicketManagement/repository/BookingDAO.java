package com.railway.TicketManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.railway.TicketManagement.entities.Booking;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface BookingDAO extends JpaRepository<Booking, Long> {

    @Query(value = """
            SELECT 
                b.user_id AS userId,
                t.train_number AS trainNumber,
                t.train_name AS trainName,
                b.date AS dateOfJourney,
                s1.station_name AS sourceStation,
                sch1.departure_time AS departureTimeFromStart,
                s2.station_name AS destinationStation,
                sch2.arrival_time AS arrivalTimeAtDestination,
                COUNT(tkt.ticket_id) AS numOfTicketsBooked,
                p.amount AS totalAmount,
                p.payment_method AS paymentMethod,
                p.payment_status AS paymentStatus
            FROM booking b
            JOIN trains t ON b.train_number = t.train_number
            JOIN schedule sch1 ON sch1.schedule_id = b.schedule_id
            JOIN station s1 ON s1.station_id = sch1.station_id
            JOIN schedule sch2 ON sch2.schedule_id = b.schedule_id
            JOIN station s2 ON s2.station_id = sch2.station_id
            LEFT JOIN ticket tkt ON tkt.booking_id = b.booking_id
            LEFT JOIN payment p ON p.booking_id = b.booking_id
            GROUP BY b.user_id, t.train_number, t.train_name, b.date, 
                     s1.station_name, sch1.departure_time, 
                     s2.station_name, sch2.arrival_time, p.amount, 
                     p.payment_method, p.payment_status
            """, nativeQuery = true)
    List<Object[]> findAllBookingsRaw();

}

