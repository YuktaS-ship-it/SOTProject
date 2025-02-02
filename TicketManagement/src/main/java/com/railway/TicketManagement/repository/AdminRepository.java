package com.railway.TicketManagement.repository;

import com.railway.TicketManagement.entities.Station;
import com.railway.TicketManagement.entities.Trains;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Station, Integer> {

    @Query("SELECT s FROM Station s")
    List<Station> findAllStations();  // Custom method to fetch all stations

    @Query("SELECT t FROM Trains t")
    List<Trains> findAllTrains();  // Custom method to fetch all trains
}

