package com.cinema.booking.category;

import com.cinema.booking.movie.Movie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryData> getCategoriesForMovie(Long movieId) {
        List<Category> categories = categoryRepository.findAllByMovieId(movieId);

        return CategoryMapper.INSTANCE.fromCategories(categories);
    }
}
