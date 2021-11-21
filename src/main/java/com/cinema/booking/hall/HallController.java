package com.cinema.booking.hall;

import com.cinema.booking.seat.SeatResponseData;
import com.cinema.booking.seat.SeatService;
import lombok.AllArgsConstructor;
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
    public List<HallResponseData> getHalls() {
        return hallService.getHalls();
    }

    @GetMapping(path = "{hallId}")
    public HallResponseData show(@PathVariable("hallId") Long hallId) {
        return hallService
                .findHall(hallId)
                .orElseThrow(() -> new IllegalStateException("Unable to find hall by id "+hallId));
    }

    @GetMapping(path = "{hallId}/seats")
    public List<SeatResponseData> seats(@PathVariable("hallId") Long hallId) {
        return seatService.getSeatsByHall(hallId);
    }
}
