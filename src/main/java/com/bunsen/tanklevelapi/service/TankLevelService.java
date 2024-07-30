package com.bunsen.tanklevelapi.service;

import com.bunsen.tanklevelapi.api.body.TankLevelBody;
import com.bunsen.tanklevelapi.model.Tank;
import com.bunsen.tanklevelapi.model.TankLevel;
import com.bunsen.tanklevelapi.model.repo.TankLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TankLevelService {
    private final TankLevelRepository tankLevelRepository;
    private final TankService tankService;

    private static final Logger logger = LoggerFactory.getLogger(TankLevelService.class);

    public TankLevelService(TankLevelRepository tankLevelRepository, TankService tankService) {
        this.tankLevelRepository = tankLevelRepository;
        this.tankService = tankService;
    }

    @Transactional
    public String saveTankLevelData(TankLevelBody tankLevelBody) {
        try {
            Tank tank = tankService.findTank(tankLevelBody.getTank().getId());
            if (tank == null) {
                return "error: Tank not found";
            }

            TankLevel tankLevel = new TankLevel();
            tankLevel.setTank(tank);
            tankLevel.setPercentageLevel(tankLevelBody.getPercentageLevel());
            tankLevel.setComment(tankLevelBody.getComment());
            tankLevel.setLatitude(tankLevelBody.getLatitude());
            tankLevel.setLongitude(tankLevelBody.getLongitude());
            tankLevelRepository.save(tankLevel);
            return "success";
        } catch (Exception ex) {
            logger.error("Failed to save tank level data", ex);
            return "error: Failed to save tank level data due to " + ex.getMessage();
        }
    }
}
