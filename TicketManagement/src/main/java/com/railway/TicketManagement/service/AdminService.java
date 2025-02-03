package com.railway.TicketManagement.service;

import com.railway.TicketManagement.dto.BookingResponseDTO;
import com.railway.TicketManagement.dto.StationSummaryDTO;
import com.railway.TicketManagement.dto.TrainSummaryDTO;

import java.util.List;

public interface AdminService {
    List<StationSummaryDTO> getStationSummary();
    List<TrainSummaryDTO> getTrainSummary();
    List<BookingResponseDTO> getAllUsersWithBookings();  // NEW METHOD
}
