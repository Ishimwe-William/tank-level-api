package com.bunsen.tanklevelapi.model.repo;

import com.bunsen.tanklevelapi.model.Tank;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TankRepository extends ListCrudRepository<Tank,Long> {

}
