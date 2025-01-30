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
@NamedQueries({
        @NamedQuery(name = Trains.Search_Trains, query = Trains.Search_Trains_JPQL)
}
)
public class Trains {
    public static final String Search_Trains = "Trains.searchTrains";

    public static final String Search_Trains_JPQL =
            "SELECT new com.railway.TicketManagement.dto.TrainSearchDTO(" +
            "t.trainNumber, t.trainName, " +
            "(r2.distanceFromStart - r1.distanceFromStart) AS totalDistance, " +
            "(r2.stopOrder - r1.stopOrder) * 100.0 AS farePrice, " +
            "s1.departureTime AS departureTimeFromSource, s2.arrivalTime AS arrivalTimeAtDestination, " +
            "CAST(t.numberOfSeats - COALESCE(SUM(ticket.ticketId), 0) AS int) AS availableSeats) " +
            "FROM Trains t " +
            "JOIN t.routes r1 " +
            "JOIN t.routes r2 " +
            "JOIN t.schedules s1 ON r1.station.stationId = s1.station.stationId AND t.trainNumber = s1.train.trainNumber " +
            "JOIN t.schedules s2 ON r2.station.stationId = s2.station.stationId AND t.trainNumber = s2.train.trainNumber " +
            "LEFT JOIN t.bookings b ON t.trainNumber = b.train.trainNumber " +
            "LEFT JOIN b.tickets ticket ON b.bookingId = ticket.booking.bookingId AND ticket.date = :dateOfJourney " +
            "WHERE r1.station.stationId = (SELECT st.stationId FROM Station st WHERE st.stationName = :source) " +
            "AND r2.station.stationId = (SELECT st.stationId FROM Station st WHERE st.stationName = :destination) " +
            "AND r1.stopOrder < r2.stopOrder " +
            "GROUP BY t.trainNumber, t.trainName, r2.distanceFromStart, r1.distanceFromStart, r2.stopOrder, r1.stopOrder, s1.departureTime, s2.arrivalTime, t.numberOfSeats";


    @Id
    @Column(name = "train_number", length = 10)
    private String trainNumber;

    @Column(name = "train_name")
    private String trainName;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    public enum Type {
        local, express
    }

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
     private List<Schedule> schedules;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Routes> routes;

    public List<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}