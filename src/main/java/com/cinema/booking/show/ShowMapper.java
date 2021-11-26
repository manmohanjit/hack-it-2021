package com.cinema.booking.show;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ShowMapper {
    ShowMapper INSTANCE = Mappers.getMapper(ShowMapper.class);

    @Mapping(source = "hall.id", target = "hallId")
    ShowData fromShow(Show show);

    @Mapping(source = "hall.id", target = "hallId")
    List<ShowData> fromShows(List<Show> shows);
}
