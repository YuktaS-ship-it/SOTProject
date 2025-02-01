package com.railway.TicketManagement.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingDTO {
    private Long bookingID;
    private Long userId;
    private Long trainId;
    private Long scheduleId;
    private LocalDate dateOfJourney;
    private Double totalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private Integer numOfTicketsBooked;
    private Set<TicketDTO> Tickets;
}
