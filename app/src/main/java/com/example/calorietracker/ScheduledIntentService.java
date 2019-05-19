package com.example.calorietracker;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/*Class to call intent service*/
public class ScheduledIntentService extends IntentService {
    static int counter=0;
    public ScheduledIntentService() {
        super("ScheduledIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        counter++;
        UserSteps userSteps = new UserSteps();
        userSteps.updateReport();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }
}
