package org.example.service.impl;

import org.example.mapper.ReservationMapper;
import org.example.pojo.Reservation;
import org.example.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationMapper.getAllReservations();
    }

    @Override
    public Reservation getReservationById(Integer id) {
        return reservationMapper.getReservationById(id);
    }

    @Override
    public void addReservation(Reservation reservation) {
        reservationMapper.insertReservation(reservation);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationMapper.updateReservation(reservation);
    }

    @Override
    public void deleteReservation(Integer id) {
        reservationMapper.deleteReservation(id);
    }
}

