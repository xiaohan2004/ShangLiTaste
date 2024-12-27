package org.example.service.impl;

import org.example.mapper.ReservationMapper;
import org.example.pojo.Reservation;
import org.example.pojo.Table;
import org.example.service.ReservationService;
import org.example.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private TableService tableService;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationMapper.getAllReservations();
    }

    @Override
    public Reservation getReservationById(Integer id) {
        return reservationMapper.getReservationById(id);
    }

    @Override
    @Transactional
    public void addReservation(Reservation reservation) {
        reservationMapper.insertReservation(reservation);
        tableService.updateTable(new org.example.pojo.Table(reservation.getTableId(), (short) 1, null));
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationMapper.updateReservation(reservation);
    }

    @Override
    public void deleteReservation(Integer id) {
        reservationMapper.deleteReservation(id);
    }

    @Override
    public Reservation get0ReservationByCustomerId(Integer customerId) {
        return reservationMapper.get0ReservationByCustomerId(customerId);
    }
}

