package com.cinema.booking.movie;

import com.cinema.booking.category.CategoryResponseData;
import com.cinema.booking.category.CategoryService;
import com.cinema.booking.show.ShowData;
import com.cinema.booking.show.ShowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/movies")
@AllArgsConstructor
public class MovieController {

    final private MovieService movieService;
    final private ShowService showService;
    final private CategoryService categoryService;

    @GetMapping
    public List<MovieResponseData> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping(path = "{movieId}")
    public MovieResponseData findMovie(@PathVariable("movieId") Long movieId) {
        return movieService
                .findMovie(movieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find movie with id " + movieId));
    }

    @GetMapping(path = "{movieId}/shows")
    public List<ShowData> getShowsForMovie(@PathVariable("movieId") Long movieId) {
        return showService.getShowsForMovie(movieId);
    }

    @GetMapping(path = "{movieId}/categories")
    public List<CategoryResponseData> getCategoriesForMovie(@PathVariable("movieId") Long movieId) {
        return categoryService.getCategoriesForMovie(movieId);
    }

}
