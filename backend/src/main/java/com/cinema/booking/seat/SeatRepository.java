package com.cinema.booking.seat;

import com.cinema.booking.hall.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByHall(Hall hall);

    List<Seat> findAllByHallId(Long hallId);
}
