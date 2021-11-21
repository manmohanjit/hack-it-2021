package com.cinema.booking.category;

import com.cinema.booking.hall.Hall;
import com.cinema.booking.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByMovieId(Long movieId);

}
