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
    private Integer bookingID;
    private Integer userId;
    private Long trainId;
    private Long scheduleID;
    private LocalDate dateOfJourney;
    private Double totalAmount;
    private Set<TicketDTO> Tickets;
}
