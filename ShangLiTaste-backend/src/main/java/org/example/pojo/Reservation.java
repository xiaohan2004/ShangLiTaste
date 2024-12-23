package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Integer reservationId;
    private Integer customerId;
    private Integer tableId;
    private LocalDateTime reservationTime;
    private Short status;
    private String specialRequests;
}

