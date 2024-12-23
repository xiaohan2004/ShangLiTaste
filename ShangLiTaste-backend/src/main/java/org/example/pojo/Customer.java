package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer customerId;
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime registrationDate;
    private LocalDate birthday;
    private BigDecimal totalSpent;
}

