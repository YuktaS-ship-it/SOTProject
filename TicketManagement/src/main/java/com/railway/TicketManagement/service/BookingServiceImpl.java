package com.railway.TicketManagement.service;

import com.railway.TicketManagement.dto.BookingRequestDTO;
import com.railway.TicketManagement.dto.BookingResponseDTO;
import com.railway.TicketManagement.entities.*;
import com.railway.TicketManagement.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDAO bookingDao;

    @Autowired
    private PaymentDAO paymentDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private TrainRepository trainDao;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StationRepository stationRepository;

    @Override
    public List<BookingResponseDTO> showAllBookings() {
        List<Object[]> results = bookingDao.findAllBookingsRaw();

        return results.stream().map(result -> {
            BookingResponseDTO dto = new BookingResponseDTO();
            dto.setUserId((Integer) result[0]);
            dto.setTrainNumber((String) result[1]);
            dto.setTrainName((String) result[2]);
            dto.setDateOfJourney(result[3] != null ? result[3].toString() : null);
            dto.setSourceStation(result[4] != null ? result[4].toString() : null);
            dto.setDepartureTimeFromStart(result[5] != null ? result[5].toString() : null);
            dto.setDestinationStation(result[6] != null ? result[6].toString() : null);
            dto.setArrivalTimeAtDestination(result[7] != null ? result[7].toString() : null);
            dto.setNumOfTicketsBooked(getIntValue(result[8]));
            dto.setTotalAmount(result[9] != null ? getDoubleValue(result[9]) : 0.0);
            dto.setPaymentMethod(result[10] != null ? result[10].toString() : null);
            dto.setPaymentStatus(result[11] != null ? result[11].toString() : null);
            return dto;
        }).collect(Collectors.toList());
    }

    private Long getLongValue(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        } else if (obj instanceof String) {
            return Long.parseLong((String) obj);
        }
        return null;
    }

    private Integer getIntValue(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else if (obj instanceof String) {
            return Integer.parseInt((String) obj);
        }
        return null;
    }

    private Double getDoubleValue(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        } else if (obj instanceof String) {
            return Double.parseDouble((String) obj);
        }
        return null;
    }




    @Override
    public ResponseEntity<Void> addNewBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        Booking bookingEntity = new Booking();
        bookingEntity.setDate(new Date());

        // Set User
        User user = userDao.findById(Long.valueOf(bookingRequestDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookingRequestDTO.getUserId()));
        bookingEntity.setUser(user);

        // Set Train
        Trains train = trainDao.findById(bookingRequestDTO.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + bookingRequestDTO.getTrainId()));
        bookingEntity.setTrain(train);

        Station sourceStation = stationRepository.findById(Math.toIntExact(bookingRequestDTO.getSourceStationId()))
                .orElseThrow(() -> new RuntimeException("Source Station not found with ID: " + bookingRequestDTO.getSourceStationId()));

        Station destinationStation = stationRepository.findById(Math.toIntExact(bookingRequestDTO.getDestinationStationId()))
                .orElseThrow(() -> new RuntimeException("Destination Station not found with ID: " + bookingRequestDTO.getDestinationStationId()));


        // Set Schedule
        Schedule schedule = new Schedule();
        schedule.setScheduleId(Math.toIntExact(bookingRequestDTO.getSourceStationId())); // Assuming Schedule ID is linked to source station
        bookingEntity.setSchedule(schedule);


        // Save Booking
        Booking savedBooking = bookingDao.save(bookingEntity);
        int numOfTickets = (bookingRequestDTO.getNumOfTicketsBooked() != null) ? bookingRequestDTO.getNumOfTicketsBooked() : 1;

        // Manually update totalAmount after saving the booking

        double calculatedAmount = calculateTotalAmount(numOfTickets);

        // Create Payment
        Payment payment = new Payment();
        payment.setBooking(savedBooking);
        payment.setDate(new Date());
        payment.setAmount(calculatedAmount);
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(bookingRequestDTO.getPaymentMethod())); // Convert to enum
        payment.setPaymentStatus(Payment.PaymentStatus.valueOf(bookingRequestDTO.getPaymentStatus())); // Convert to enum
        paymentDao.save(payment);

        List<Ticket> ticketList = new ArrayList<>();
        for (int i = 1; i <= numOfTickets; i++) {
            Ticket ticket = new Ticket();
            ticket.setBooking(savedBooking);
            ticket.setSeatNumber(i); // Assigning seat numbers sequentially
            ticket.setStartStation(sourceStation);
            ticket.setEndStation(destinationStation);
            ticket.setDate(new Date());
            ticket.setPrice(250.0);
            ticketList.add(ticket);
        }
        savedBooking.setTickets(ticketList);

        // Save All Tickets
        ticketRepository.saveAll(ticketList);

        // Convert saved booking into a response DTO
        /*BookingResponseDTO responseDTO = new BookingResponseDTO();
        responseDTO.setUserId(savedBooking.getUser().getUserId());
        responseDTO.setTrainId(savedBooking.getTrain().getTrainNumber());
        responseDTO.setDateOfJourney(savedBooking.getDate().toString());
        responseDTO.setSourceStationId(String.valueOf(sourceStation.getStationId()));
        responseDTO.setDestinationStationId(String.valueOf(destinationStation.getStationId()));
        responseDTO.setNumOfTicketsBooked(numOfTickets);
        responseDTO.setTotalAmount(calculatedAmount);
        responseDTO.setPaymentMethod(payment.getPaymentMethod().name());
        responseDTO.setPaymentStatus(payment.getPaymentStatus().name());

        return responseDTO;*/
        return ResponseEntity.ok().build();
    }

    private double calculateTotalAmount(int numOfTickets) {
        double ticketPrice = 250.0;
        return numOfTickets * ticketPrice;
    }
}
