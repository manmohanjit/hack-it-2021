package com.cinema.booking.category;

import com.cinema.booking.hall.Hall;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    final private CategoryService categoryService;

    @GetMapping
    public List<Category> index() {
        return categoryService.getCategories();
    }

    @GetMapping(path = "{categoryId}")
    public Category show(@PathVariable("categoryId") Long categoryId) {
        return categoryService
                .findCategory(categoryId)
                .orElseThrow(() -> new IllegalStateException("Unable to find category by id "+categoryId));
    }
}
