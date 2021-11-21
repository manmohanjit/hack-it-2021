package com.cinema.booking.category;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryData {

    private Long id;
    private String label;
    private Integer price;
    private String colour;

}
