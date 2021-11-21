package com.cinema.booking.movie;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieData fromMovie(Movie movie);

    List<MovieData> fromMovies(List<Movie> movies);

}
