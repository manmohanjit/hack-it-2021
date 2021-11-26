package com.cinema.booking.seat;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SeatMapper {

    SeatMapper INSTANCE = Mappers.getMapper(SeatMapper.class);

    SeatResponseData fromSeat(Seat seat);

    List<SeatResponseData> fromSeats(List<Seat> seats);

}
