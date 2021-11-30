package com.cinema.booking.show;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;

    /**
     * Find a show by show id
     *
     * @param showId
     */
    public Optional<ShowData> findShow(Long showId) {
        return showRepository
                .findById(showId)
                .map(ShowMapper.INSTANCE::fromShow);
    }

    /**
     * Get a list of shows by movie ID
     *
     * @param movieId
     */
    public List<ShowData> getShowsForMovie(Long movieId) {
        List<Show> shows = showRepository.findAllByMovieIdOrderByStartsAtAsc(movieId);

        return ShowMapper.INSTANCE.fromShows(shows);
    }

}
