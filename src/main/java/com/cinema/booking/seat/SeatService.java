package com.cinema.booking.seat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    /**
     * Get a list of seats by hall ID
     *
     * @param hallId
     */
    public List<SeatResponseData> getSeatsByHall(Long hallId) {
        List<Seat> seats = seatRepository.findAllByHallId(hallId);

        return SeatMapper.INSTANCE.fromSeats(seats);
    }
}
