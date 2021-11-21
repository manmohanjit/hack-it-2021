package com.cinema.booking.category;

import com.cinema.booking.show.Show;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponseData fromShow(Show show);

    CategoryResponseData fromCategory(Category category);

    List<CategoryResponseData> fromCategories(List<Category> categories);

}
