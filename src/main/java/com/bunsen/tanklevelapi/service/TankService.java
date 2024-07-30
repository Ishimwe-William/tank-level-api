package com.bunsen.tanklevelapi.service;

import com.bunsen.tanklevelapi.api.body.TankBody;
import com.bunsen.tanklevelapi.model.Tank;
import com.bunsen.tanklevelapi.model.TankLevel;
import com.bunsen.tanklevelapi.model.repo.TankLevelRepository;
import com.bunsen.tanklevelapi.model.repo.TankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TankService {
    private static final Logger logger = LoggerFactory.getLogger(TankService.class);

    private final TankRepository tankRepository;
    private final TankLevelRepository tankLevelRepository;

    public TankService(TankRepository tankRepository, TankLevelRepository tankLevelRepository) {
        this.tankRepository = tankRepository;
        this.tankLevelRepository = tankLevelRepository;
    }

    @Transactional
    public String saveTankData(TankBody tankBody) {
        try {
            Tank tank = new Tank();
            tank.setTankName(tankBody.getTankName());
            tankRepository.save(tank);
            return "success";
        } catch (Exception ex) {
            logger.error("Failed to save tank data", ex);
            return "error: Failed to save tank data due to " + ex.getMessage();
        }
    }

    public List<TankLevel> tankLevelListByTank(Tank tank) {
        try {
            return tankLevelRepository.findByTankOrderByCreatedAtDesc(tank);
        } catch (Exception ex) {
            logger.error("Failed to get tank level data", ex);
            throw new RuntimeException("Failed to get tank level data", ex);
        }
    }

    public Tank findTank(Long id) {
        try {
            Optional<Tank> tank = tankRepository.findById(id);
            if (tank.isPresent()) {
                return tank.get();
            } else {
                throw new RuntimeException("Tank not found");
            }
        } catch (Exception ex) {
            logger.error("Failed to get tank", ex);
            throw new RuntimeException("Failed to get tank", ex);
        }
    }

    public List<Tank> findAllTanks() {
        try {
            return tankRepository.findAll();
        } catch (Exception ex) {
            logger.error("Failed to get tank list", ex);
            throw new RuntimeException("Failed to get tank list", ex);
        }
    }
}
