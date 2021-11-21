package com.cinema.booking.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieResponseData fromMovie(Movie movie);

    List<MovieResponseData> fromMovies(List<Movie> movies);

}
