package org.example.controller;

import org.example.pojo.Reservation;
import org.example.pojo.Result;
import org.example.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public Result getAllReservations() {
        return Result.success(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public Result getReservationById(@PathVariable Integer id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return Result.success(reservation);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createReservation(@RequestBody Reservation reservation) {
        reservationService.addReservation(reservation);
        return Result.success(reservation);
    }

    @PutMapping("/{id}")
    public Result updateReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        Reservation existingReservation = reservationService.getReservationById(id);
        if (existingReservation != null) {
            reservation.setReservationId(id);
            reservationService.updateReservation(reservation);
            return Result.success(reservation);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteReservation(@PathVariable Integer id) {
        Reservation existingReservation = reservationService.getReservationById(id);
        if (existingReservation != null) {
            reservationService.deleteReservation(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

