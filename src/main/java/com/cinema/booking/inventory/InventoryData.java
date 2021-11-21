package com.cinema.booking.inventory;

import com.cinema.booking.category.Category;
import com.cinema.booking.category.CategoryData;
import com.cinema.booking.seat.Seat;
import com.cinema.booking.seat.SeatData;
import com.cinema.booking.show.Show;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
public class InventoryData {

    private Long id;
    private CategoryData category;
    private SeatData seat;
    private InventoryStatus status;
    private Boolean enabled;

}
