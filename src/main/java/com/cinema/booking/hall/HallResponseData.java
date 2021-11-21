package com.cinema.booking.hall;

import com.cinema.booking.seat.SeatResponseData;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class HallResponseData {

    private Long id;
    private String title;
    private Set<SeatResponseData> seats = new HashSet<>();

}
