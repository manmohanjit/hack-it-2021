package com.cinema.booking.movie;

import com.cinema.booking.category.CategoryRepository;
import com.cinema.booking.show.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Get all available movies
     */
    public List<MovieResponseData> getMovies() {
        List<Movie> movies = movieRepository.findAll();

        return MovieMapper.INSTANCE.fromMovies(movies);
    }

    /**
     * Find a movie by movie ID
     *
     * @param showId
     */
    public Optional<MovieResponseData> findMovie(Long movieId) {
        return movieRepository
                .findById(movieId)
                .map(MovieMapper.INSTANCE::fromMovie);
    }

}
