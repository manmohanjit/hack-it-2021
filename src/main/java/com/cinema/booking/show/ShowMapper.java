package com.cinema.booking.show;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ShowMapper {
    ShowMapper INSTANCE = Mappers.getMapper(ShowMapper.class);

    ShowData fromShow(Show show);

    List<ShowData> fromShows(List<Show> shows);
}
