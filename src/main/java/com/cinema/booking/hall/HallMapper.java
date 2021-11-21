package com.cinema.booking.hall;

import com.cinema.booking.movie.Movie;
import com.cinema.booking.movie.MovieData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HallMapper {

    HallMapper INSTANCE = Mappers.getMapper(HallMapper.class);

    HallData fromHall(Hall hall);

    List<HallData> fromHalls(List<Hall> halls);

}
