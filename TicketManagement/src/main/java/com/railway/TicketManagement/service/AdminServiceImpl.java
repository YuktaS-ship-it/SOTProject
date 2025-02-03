package com.railway.TicketManagement.service;

import com.railway.TicketManagement.dto.BookingResponseDTO;
import com.railway.TicketManagement.dto.StationSummaryDTO;
import com.railway.TicketManagement.dto.TrainSummaryDTO;
import com.railway.TicketManagement.entities.Booking;
import com.railway.TicketManagement.entities.Station;
import com.railway.TicketManagement.entities.Ticket;
import com.railway.TicketManagement.entities.Trains;
import com.railway.TicketManagement.repository.AdminRepository;
import com.railway.TicketManagement.repository.BookingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BookingDAO bookingDAO;  // Inject BookingDAO

    @Override
    public List<StationSummaryDTO> getStationSummary() {
        List<Station> stations = adminRepository.findAllStations();  // Ensure method in repo
        List<StationSummaryDTO> summaryDTOs = new ArrayList<>();

        for (Station station : stations) {
            StationSummaryDTO summary = new StationSummaryDTO();
            summary.setStationId(station.getStationId());
            summary.setStationName(station.getStationName());

            // Calculate earnings and total passengers for the station
            double earnings = 0;
            int totalPassengers = 0;
            for (Ticket ticket : station.getStartTickets()) {
                earnings += ticket.getPrice();
                totalPassengers++;
            }
            for (Ticket ticket : station.getEndTickets()) {
                earnings += ticket.getPrice();
                totalPassengers++;
            }

            summary.setEarnings(earnings);
            summary.setTotalPassengers(totalPassengers);
            summary.setDate(new Date());

            summaryDTOs.add(summary);
        }

        return summaryDTOs;
    }

    @Override
    public List<TrainSummaryDTO> getTrainSummary() {
        List<Trains> trains = adminRepository.findAllTrains();  // Ensure method in repo
        List<TrainSummaryDTO> summaryDTOs = new ArrayList<>();

        for (Trains train : trains) {
            TrainSummaryDTO summary = new TrainSummaryDTO();
            summary.setTrainId(train.getTrainNumber());
            summary.setTrainName(train.getTrainName());

            // Calculate earnings and total passengers for the train
            double earnings = 0;
            int totalPassengers = 0;
            for (Booking ticket : train.getBookings()) {
                for (Ticket tickets : ticket.getTickets())
                    earnings += tickets.getPrice();
                totalPassengers++;
            }

            summary.setEarnings(earnings);
            summary.setTotalPassengers(totalPassengers);
            summary.setDate(new Date());

            summaryDTOs.add(summary);
        }

        return summaryDTOs;
    }

    // New method for booking history
    @Override
    public List<BookingResponseDTO> getAllUsersWithBookings() {
        List<Object[]> rawBookings = bookingDAO.findAllBookingsRaw();

        return rawBookings.stream().map(obj -> new BookingResponseDTO(
                obj[0] != null ? ((Number) obj[0]).intValue() : null,  // userId (Integer)
                obj[1] != null ? obj[1].toString() : null,  // trainNumber (String)
                obj[2] != null ? obj[2].toString() : null,  // trainName (String)
                obj[3] != null ? obj[3].toString() : null,  // dateOfJourney (String)
                obj[4] != null ? obj[4].toString() : null,  // sourceStation (String)
                obj[5] != null ? obj[5].toString() : null,  // departureTimeFromStart (String)
                obj[6] != null ? obj[6].toString() : null,  // destinationStation (String)
                obj[7] != null ? obj[7].toString() : null,  // arrivalTimeAtDestination (String)
                obj[8] != null ? ((Number) obj[8]).intValue() : null,  // numOfTicketsBooked (Integer)
                obj[9] != null ? ((Number) obj[9]).doubleValue() : null, // totalAmount (Double)
                obj[10] != null ? obj[10].toString() : null, // paymentMethod (String)
                obj[11] != null ? obj[11].toString() : null  // paymentStatus (String)
        )).collect(Collectors.toList());
    }

}
