package com.railway.TicketManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Integer stationId;

    @Column(name = "station_name")
    private String stationName;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "startStation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ticket> startTickets;

    @OneToMany(mappedBy = "endStation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ticket> endTickets;

    public int getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Ticket> getStartTickets() {
        return startTickets;
    }

    public void setStartTickets(List<Ticket> startTickets) {
        this.startTickets = startTickets;
    }

    public List<Ticket> getEndTickets() {
        return endTickets;
    }

    public void setEndTickets(List<Ticket> endTickets) {
        this.endTickets = endTickets;
    }
}