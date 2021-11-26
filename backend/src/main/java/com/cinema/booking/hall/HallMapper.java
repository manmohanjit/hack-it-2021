package com.cinema.booking.hall;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HallMapper {

    HallMapper INSTANCE = Mappers.getMapper(HallMapper.class);

    HallResponseData fromHall(Hall hall);

    List<HallResponseData> fromHalls(List<Hall> halls);

}
