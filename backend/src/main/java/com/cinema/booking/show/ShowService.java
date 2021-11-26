package com.cinema.booking.show;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;

    public Optional<ShowData> findShow(Long id) {
        return showRepository
                .findById(id)
                .map(ShowMapper.INSTANCE::fromShow);
    }

    public List<ShowData> getShowsForMovie(Long movieId) {
        List<Show> shows = showRepository.findAllByMovieId(movieId);

        return ShowMapper.INSTANCE.fromShows(shows);
    }

}
