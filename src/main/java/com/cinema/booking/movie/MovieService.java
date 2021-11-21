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

    public List<MovieData> getMovies() {
        List<Movie> movies = movieRepository.findAll();

        return MovieMapper.INSTANCE.fromMovies(movies);
    }

    public Optional<MovieData> findMovie(Long movieId) {
        Optional<Movie> movie = movieRepository
                .findById(movieId);

        if(movie.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(MovieMapper.INSTANCE.fromMovie(movie.get()));
    }

}
