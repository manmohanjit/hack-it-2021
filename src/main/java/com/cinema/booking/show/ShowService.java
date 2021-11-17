package com.cinema.booking.show;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;

    public List<Show> getShows() {
        return showRepository.findAll();
    }

    public Optional<Show> findShow(Long id) {
        return showRepository.findById(id);
    }

}
