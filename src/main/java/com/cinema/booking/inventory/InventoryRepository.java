package com.cinema.booking.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findAllByShowId(Long showId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Inventory> findAllByShowIdAndIdInAndStatus(Long showId, List<Long> ids, InventoryStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Inventory> findAllByIdIn(List<Long> ids);
}
