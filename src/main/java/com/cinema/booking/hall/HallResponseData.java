package com.cinema.booking.hall;

import com.cinema.booking.seat.SeatResponseData;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class HallResponseData {

    private Long id;
    private String title;
    private String seatMap;

}
