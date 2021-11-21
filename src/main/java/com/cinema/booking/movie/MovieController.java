package com.cinema.booking.movie;

import com.cinema.booking.category.CategoryData;
import com.cinema.booking.category.CategoryService;
import com.cinema.booking.show.ShowData;
import com.cinema.booking.show.ShowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/movies")
@AllArgsConstructor
public class MovieController {

    final private MovieService movieService;
    final private ShowService showService;
    final private CategoryService categoryService;

    @GetMapping
    public List<MovieData> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping(path = "{movieId}")
    public MovieData findMovie(@PathVariable("movieId") Long movieId) {
        return movieService
                .findMovie(movieId)
                .orElseThrow(() -> new IllegalStateException("Unable to find movie by id "+movieId));
    }

    @GetMapping(path = "{movieId}/shows")
    public List<ShowData> getShowsForMovie(@PathVariable("movieId") Long movieId) {
        return showService.getShowsForMovie(movieId);
    }

    @GetMapping(path = "{movieId}/categories")
    public List<CategoryData> getCategoriesForMovie(@PathVariable("movieId") Long movieId) {
        return categoryService.getCategoriesForMovie(movieId);
    }

}
