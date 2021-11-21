package com.cinema.booking.show;

import com.cinema.booking.category.Category;
import com.cinema.booking.movie.Movie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;

    public Optional<Show> findShow(Long id) {
        Optional<Show> show = showRepository.findById(id);

        return show;
    }

    public List<ShowData> getShowsForMovie(Long movieId) {
        List<Show> shows = showRepository.findAllByMovieId(movieId);

        return ShowMapper.INSTANCE.fromShows(shows);
    }

}
