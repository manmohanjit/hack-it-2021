package com.cinema.booking.show;

import com.cinema.booking.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findAllByMovie(Movie movie);

    List<Show> findAllByMovieIdOrderByStartsAtAsc(Long movieId);

}
