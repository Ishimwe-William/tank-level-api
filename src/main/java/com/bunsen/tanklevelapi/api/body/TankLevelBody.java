package com.bunsen.tanklevelapi.api.body;

import com.bunsen.tanklevelapi.model.Tank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TankLevelBody {
    private double percentageLevel;
    private String comment;
    private String latitude;
    private String longitude;
    private Tank tank;
}
