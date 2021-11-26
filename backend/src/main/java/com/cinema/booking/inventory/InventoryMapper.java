package com.cinema.booking.inventory;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InventoryMapper {

    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    InventoryResponseData fromInventory(Inventory inventory);

    List<InventoryResponseData> fromInventory(List<Inventory> inventory);

}
