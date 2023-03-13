package com.moazzem.inappupdate;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.IntentSender;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

public class InAppUpdate {


    public static AppUpdateManager mAppUpdateManager;


    public static void initUpdate(Activity activity){
        mAppUpdateManager = AppUpdateManagerFactory.create(activity);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result){
                if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){

                    try{
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE, activity, 100);

                    } catch (IntentSender.SendIntentException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void initResult (Activity activity, int requestCode, int resultCode) {

        if(requestCode == 100 && resultCode != RESULT_OK){
            Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();
        }

    }


    public static void initResume(Activity activity){
    mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>(){
            @Override
            public void onSuccess(AppUpdateInfo result){
                if(result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                    try{
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE, activity ,100);
                    } catch (IntentSender.SendIntentException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }


}
