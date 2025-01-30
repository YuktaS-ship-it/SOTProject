package com.railway.TicketManagement.service;

import com.railway.TicketManagement.dto.StationSummaryDTO;
import com.railway.TicketManagement.dto.TrainSummaryDTO;
import com.railway.TicketManagement.entities.Booking;
import com.railway.TicketManagement.entities.Station;
import com.railway.TicketManagement.entities.Ticket;
import com.railway.TicketManagement.entities.Trains;
import com.railway.TicketManagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

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
            summary.setDate(new Date());  // You can modify to show actual date of report

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
                //List<Ticket> tickets = ticket.getTickets();
                for(Ticket tickets : ticket.getTickets())
                    earnings += tickets.getPrice();
                totalPassengers++;
            }

            summary.setEarnings(earnings);
            summary.setTotalPassengers(totalPassengers);
            summary.setDate(new Date());  // You can modify to show actual date of report

            summaryDTOs.add(summary);
        }

        return summaryDTOs;
    }
}
