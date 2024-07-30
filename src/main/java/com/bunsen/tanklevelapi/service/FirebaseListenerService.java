package com.bunsen.tanklevelapi.service;

import com.bunsen.tanklevelapi.api.body.TankLevelBody;
import com.bunsen.tanklevelapi.model.Tank;
import com.google.firebase.database.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FirebaseListenerService {
    private final FirebaseDatabase firebaseDatabase;
    private static final Logger logger = LoggerFactory.getLogger(FirebaseListenerService.class);

    public FirebaseListenerService(FirebaseDatabase firebaseDatabase, TankLevelService tankLevelService) {
        this.firebaseDatabase = firebaseDatabase;
        if (firebaseDatabase == null) {
            logger.error("FirebaseDatabase instance is null");
        }
    }

    @PostConstruct
    public void initialize() {
        logger.debug("Initializing Firebase listener");
        DatabaseReference ref = firebaseDatabase.getReference("/tanks/levels/data");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                logger.debug("Data changed: " + dataSnapshot.getValue());
                // Existing implementation
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.error("Firebase read failed: " + databaseError.getMessage());
            }
        });
    }

    private void saveData(TankLevelBody tankLevelData) {
        logger.info("Most recent data: " + tankLevelData);
        Tank tank = new Tank();
        tank.setId(tankLevelData.getId());
        tankLevelData.setTank(tank);
//        tankLevelService.saveTankLevelData(tankLevelData);
    }
}
