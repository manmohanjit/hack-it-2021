package com.cinema.booking.seat;

import com.cinema.booking.hall.Hall;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
public class SeatResponseData {

    private Long id;
    private String label;

}