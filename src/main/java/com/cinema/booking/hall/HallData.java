package com.cinema.booking.hall;

import com.cinema.booking.seat.Seat;
import com.cinema.booking.seat.SeatData;
import com.cinema.booking.show.Show;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class HallData {

    private Long id;
    private String title;
    private Set<SeatData> seats = new HashSet<>();

}
