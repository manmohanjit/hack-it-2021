package com.cinema.booking.movie;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/movies")
@AllArgsConstructor
public class MovieController {

    final private MovieService movieService;

    @GetMapping
    public List<Movie> index() {
        return movieService.getMovies();
    }

    @GetMapping(path = "{movieId}")
    public Movie show(@PathVariable("movieId") Long movieId) {
        return movieService
                .findMovie(movieId)
                .orElseThrow(() -> new IllegalStateException("Unable to find movie by id "+movieId));
    }

}
