package org.example.service;

import org.example.pojo.Reservation;
import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation getReservationById(Integer id);
    void addReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    void deleteReservation(Integer id);
    Reservation get0ReservationByCustomerId(Integer customerId);
}

