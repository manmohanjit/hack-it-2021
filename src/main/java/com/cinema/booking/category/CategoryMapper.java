package com.cinema.booking.category;

import com.cinema.booking.show.Show;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryData fromShow(Show show);

    CategoryData fromCategory(Category category);

    List<CategoryData> fromCategories(List<Category> categories);

}
