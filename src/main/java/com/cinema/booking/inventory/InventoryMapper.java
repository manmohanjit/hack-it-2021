package com.cinema.booking.inventory;

import com.cinema.booking.category.CategoryMapper;
import com.cinema.booking.seat.SeatMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InventoryMapper {

    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    InventoryData fromInventory(Inventory inventory);

    List<InventoryData> fromInventory(List<Inventory> inventory);

}
