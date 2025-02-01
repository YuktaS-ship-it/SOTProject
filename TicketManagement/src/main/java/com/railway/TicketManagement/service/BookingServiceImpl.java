package com.railway.TicketManagement.service;

import com.railway.TicketManagement.dto.TicketDTO;
import com.railway.TicketManagement.repository.TicketDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.railway.TicketManagement.dto.BookingDTO;
import com.railway.TicketManagement.entities.Booking;
import com.railway.TicketManagement.entities.Ticket;
import com.railway.TicketManagement.entities.Payment;
import com.railway.TicketManagement.repository.BookingDAO;
import com.railway.TicketManagement.repository.PaymentDAO;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDAO bookingDao;

    @Autowired
    private TicketDAO ticketDao;

    @Autowired
    private PaymentDAO paymentDao;

    @Autowired
    private ModelMapper modelMapper;


    /*@Override
    public BookingDTO addNewBooking(BookingDTO bookingDTO) {
        Booking bookingEntity = modelMapper.map(bookingDTO, Booking.class);

        // Convert TicketDTO to TicketEntity and associate with booking
        Set<Ticket> tickets = bookingDTO.getTickets().stream().map(ticketDTO -> {
            Ticket ticketEntity = modelMapper.map(ticketDTO, Ticket.class);
            ticketEntity.setBooking(bookingEntity);
            return ticketEntity;
        }).collect(Collectors.toSet());

        bookingEntity.setTickets((List<Ticket>) tickets);

        // Save booking
        Booking savedBooking = bookingDao.save(bookingEntity);

        Payment payment = Payment.builder()
                .booking(savedBooking)              // Associate with saved booking
                .date(new Date())                   // Set payment date to current date
                .amount(bookingDTO.getTotalAmount()) // Use amount from BookingDTO
                .paymentMethod(Payment.PaymentMethod.Card) // Default Payment Method (can be dynamic)
                .paymentStatus(Payment.PaymentStatus.success) // Default Payment Status
                .build();

        paymentDao.save(payment);

        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    @Override
    public List<BookingDTO> showAllBookings() {
        List<Booking> bookings = bookingDao.findAll();
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }
}*/

    @Override
    public BookingDTO addNewBooking(BookingDTO bookingDTO) {
        Booking bookingEntity = modelMapper.map(bookingDTO, Booking.class);
        bookingEntity.setTickets(null);
        Booking savedBooking = bookingDao.save(bookingEntity);

        for (int i = 1; i <= bookingDTO.getNumOfTicketsBooked(); i++) {
            Ticket ticket = Ticket.builder()
                    .booking(savedBooking)
                    .seatNumber(i)
                    .price(bookingDTO.getTotalAmount() / bookingDTO.getNumOfTicketsBooked())
                    .startStation(null) // Assign dynamically
                    .endStation(null) // Assign dynamically
                    .build();
            ticketDao.save(ticket);
        }

        Payment payment = Payment.builder()
                .booking(savedBooking)
                .date(new Date())
                .amount(bookingDTO.getTotalAmount())
                .paymentMethod(Payment.PaymentMethod.valueOf(bookingDTO.getPaymentMethod()))
                .paymentStatus(Payment.PaymentStatus.valueOf(bookingDTO.getPaymentStatus()))
                .build();
        paymentDao.save(payment);

        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    @Override
    public List<BookingDTO> showAllBookings() {
        List<Booking> bookings = bookingDao.findAll();
        return bookings.stream().map(booking -> {
            BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
            bookingDTO.setTickets(ticketDao.findByBooking_BookingId(Long.valueOf(booking.getBookingId()))
                    .stream().map(ticket -> modelMapper.map(ticket, TicketDTO.class))
                    .collect(Collectors.toSet()));
            return bookingDTO;
        }).collect(Collectors.toList());
    }
}
