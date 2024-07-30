package com.bunsen.tanklevelapi.controller;

import com.bunsen.tanklevelapi.api.body.TankBody;
import com.bunsen.tanklevelapi.api.body.TankLevelBody;
import com.bunsen.tanklevelapi.model.Tank;
import com.bunsen.tanklevelapi.model.TankLevel;
import com.bunsen.tanklevelapi.service.TankLevelService;
import com.bunsen.tanklevelapi.service.TankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tanks")
public class TankController {

    private final TankService tankService;
    private final TankLevelService tankLevelService;

    public TankController(TankService tankService, TankLevelService tankLevelService) {
        this.tankService = tankService;
        this.tankLevelService = tankLevelService;
    }

    @PostMapping
    public ResponseEntity<String> saveTank(@RequestBody TankBody tankBody) {
        String result = tankService.saveTankData(tankBody);
        if (result.equals("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(500).body(result);
        }
    }

    @GetMapping
    public ResponseEntity<List<Tank>> getAllTank() {
        List<Tank> tanks = tankService.findAllTanks();
        if (!tanks.isEmpty()) {
            return ResponseEntity.ok(tanks);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tank> getTank(@PathVariable Long id) {
        Tank tank = tankService.findTank(id);
        if (tank != null) {
            return ResponseEntity.ok(tank);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}/levels")
    public ResponseEntity<List<TankLevel>> getTankLevels(@PathVariable Long id) {
        Tank tank = tankService.findTank(id);
        if (tank != null) {
            List<TankLevel> tankLevels = tankService.tankLevelListByTank(tank);
            return ResponseEntity.ok(tankLevels);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/{id}/levels")
    public ResponseEntity<String> saveTankLevel(@PathVariable Long id, @RequestBody TankLevelBody tankLevelBody) {
        Tank tank = tankService.findTank(id);
        if (tank != null) {
            tankLevelBody.setTank(tank);
            String result = tankLevelService.saveTankLevelData(tankLevelBody);
            if (result.equals("success")) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(500).body(result);
            }
        } else {
            return ResponseEntity.status(404).body("Tank not found");
        }
    }
}
