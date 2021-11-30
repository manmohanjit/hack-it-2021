package com.cinema.booking.hall;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HallService {

    private final HallRepository hallRepository;

    /**
     * Get all the available halls
     */
    public List<HallResponseData> getHalls() {
        List<Hall> halls = hallRepository.findAll();

        return HallMapper.INSTANCE.fromHalls(halls);
    }

    /**
     * Find a hall by hall ID
     *
     * @param hallId
     */
    public Optional<HallResponseData> findHall(Long hallId) {
        return hallRepository
                .findById(hallId)
                .map(HallMapper.INSTANCE::fromHall);
    }
}
