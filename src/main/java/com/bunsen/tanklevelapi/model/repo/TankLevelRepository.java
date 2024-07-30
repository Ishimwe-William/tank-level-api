package com.bunsen.tanklevelapi.model.repo;

import com.bunsen.tanklevelapi.model.Tank;
import com.bunsen.tanklevelapi.model.TankLevel;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TankLevelRepository extends ListCrudRepository<TankLevel,Long> {
    List<TankLevel> findByTankOrderByCreatedAtDesc(Tank tank);
}
