package com.cinema.booking.hall;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HallService {

    private final HallRepository hallRepository;

    public List<Hall> getHalls() {
        return hallRepository.findAll();
    }

    public Optional<Hall> findHall(Long id) {
        return hallRepository.findById(id);
    }
}
