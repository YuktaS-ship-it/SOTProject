package com.railway.TicketManagement.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketDTO {
    private Long ticketID;
    private Long bookingID;
    private Integer seatNumber;
    private Long startStationId;
    private Long endStationId;
}
