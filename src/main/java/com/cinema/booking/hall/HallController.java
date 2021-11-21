package com.cinema.booking.hall;

import com.cinema.booking.seat.Seat;
import com.cinema.booking.seat.SeatData;
import com.cinema.booking.seat.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/halls")
@AllArgsConstructor
public class HallController {

    final private HallService hallService;
    final private SeatService seatService;

    @GetMapping
    public List<HallData> getHalls() {
        return hallService.getHalls();
    }

    @GetMapping(path = "{hallId}")
    public HallData show(@PathVariable("hallId") Long hallId) {
        return hallService
                .findHall(hallId)
                .orElseThrow(() -> new IllegalStateException("Unable to find hall by id "+hallId));
    }

    @GetMapping(path = "{hallId}/seats")
    public List<SeatData> seats(@PathVariable("hallId") Long hallId) {
        return seatService.getSeatsByHall(hallId);
    }
}
