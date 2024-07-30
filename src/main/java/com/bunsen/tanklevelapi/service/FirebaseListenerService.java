package com.bunsen.tanklevelapi.service;

import com.bunsen.tanklevelapi.api.body.TankLevelBody;
import com.google.firebase.database.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FirebaseListenerService {
    private final FirebaseDatabase firebaseDatabase;
    private static final Logger logger = LoggerFactory.getLogger(FirebaseListenerService.class);

    public FirebaseListenerService(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @PostConstruct
    public void initialize() {
        DatabaseReference ref = firebaseDatabase.getReference("/tanks/levels/data");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                String tankId = dataSnapshot.getKey();
                TankLevelBody data = dataSnapshot.getValue(TankLevelBody.class);
                if (data != null) {
                    data.setId(Long.parseLong(tankId));
                    saveData(data);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                onChildAdded(dataSnapshot, prevChildKey);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                logger.info(String.valueOf(dataSnapshot));
                // Handle removal if needed
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                logger.info(String.valueOf(dataSnapshot));
                // Handle move if needed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.error("Firebase read failed: " + databaseError.getMessage());
            }
        });
    }

    private void saveData(TankLevelBody data) {
        logger.info(String.valueOf(data));
        // Implement your logic to save the data
        // This could involve saving to a database, file, or other storage
    }
}