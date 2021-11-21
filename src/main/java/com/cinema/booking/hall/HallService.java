package com.cinema.booking.hall;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HallService {

    private final HallRepository hallRepository;

    public List<HallData> getHalls() {
        List<Hall> halls = hallRepository.findAll();

        return HallMapper.INSTANCE.fromHalls(halls);
    }

    public Optional<HallData> findHall(Long hallId) {
        Optional<Hall> hall = hallRepository.findById(hallId);

        if(hall.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(HallMapper.INSTANCE.fromHall(hall.get()));
    }
}
