package com.railway.TicketManagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingResponseDTO {
    private Integer userId;
    private String trainNumber;
    private String trainName;
    private String dateOfJourney;
    private String sourceStation;
    private String departureTimeFromStart;
    private String destinationStation;
    private String arrivalTimeAtDestination;
    private Integer numOfTicketsBooked;
    private Double totalAmount;
    private String paymentMethod;
    private String paymentStatus;
}
