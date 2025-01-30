package com.railway.TicketManagement.controller;

import com.railway.TicketManagement.dto.StationSummaryDTO;
import com.railway.TicketManagement.dto.TrainSummaryDTO;
import com.railway.TicketManagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;



    // Endpoint to get station summary
    @GetMapping("/stationSummary")
    public List<StationSummaryDTO> getStationSummary() {
        return adminService.getStationSummary();
    }

    // Endpoint to get train summary
    @GetMapping("/trainSummary")
    public List<TrainSummaryDTO> getTrainSummary() {
        return adminService.getTrainSummary();
    }
}
