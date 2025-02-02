package com.railway.TicketManagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingRequestDTO {
    private Long userId;
    private String trainId;
    private String dateOfJourney;
    private Long sourceStationId;
    private Long destinationStationId;
    private Integer numOfTicketsBooked;
    // private Double totalAmount;
    private String paymentMethod;
    private String paymentStatus;
}
