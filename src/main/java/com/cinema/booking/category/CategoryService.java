package com.cinema.booking.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseData> getCategoriesForMovie(Long movieId) {
        List<Category> categories = categoryRepository.findAllByMovieId(movieId);

        return CategoryMapper.INSTANCE.fromCategories(categories);
    }
}
