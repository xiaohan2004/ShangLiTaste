package org.example.mapper;

import org.example.pojo.Reservation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {
    @Select("SELECT * FROM reservations")
    List<Reservation> getAllReservations();

    @Select("SELECT * FROM reservations WHERE reservation_id = #{reservationId}")
    Reservation getReservationById(Integer reservationId);

    @Insert("INSERT INTO reservations(customer_id, table_id, reservation_time, status, special_requests) VALUES(#{customerId}, #{tableId}, #{reservationTime}, #{status}, #{specialRequests})")
    @Options(useGeneratedKeys = true, keyProperty = "reservationId")
    void insertReservation(Reservation reservation);

    @Update("UPDATE reservations SET customer_id = #{customerId}, table_id = #{tableId}, reservation_time = #{reservationTime}, status = #{status}, special_requests = #{specialRequests} WHERE reservation_id = #{reservationId}")
    void updateReservation(Reservation reservation);

    @Delete("DELETE FROM reservations WHERE reservation_id = #{reservationId}")
    void deleteReservation(Integer reservationId);
}

