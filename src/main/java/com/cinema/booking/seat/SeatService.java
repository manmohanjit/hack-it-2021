package com.cinema.booking.seat;

import com.cinema.booking.hall.Hall;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<SeatData> getSeatsByHall(Long hallId) {
        List<Seat> seats = seatRepository.findAllByHallId(hallId);

        return SeatMapper.INSTANCE.fromSeats(seats);
    }
}
