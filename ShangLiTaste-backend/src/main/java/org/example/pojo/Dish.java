package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private Integer dishId;
    private String name;
    private String img;
    private Integer categoryId;
    private BigDecimal price;
    private String description;
    private Short isActive;
}

