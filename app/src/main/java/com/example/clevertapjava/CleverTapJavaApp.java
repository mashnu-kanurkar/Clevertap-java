package com.example.clevertapjava;

import android.app.Application;
import android.widget.Toast;

import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CleverTapJavaApp extends Application implements CTPushNotificationListener, CTInboxListener {
    CleverTapAPI clevertapDefaultInstance;
    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        if (clevertapDefaultInstance != null) {
            clevertapDefaultInstance.setCTPushNotificationListener(this);
        }
    }


    @Override
    public void onNotificationClickedPayloadReceived(HashMap<String, Object> payload) {
        if(payload!=null){
            Toast.makeText(getApplicationContext(), "Clicked Payload received "+payload, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "payload is null", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void inboxDidInitialize(){
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Promotions");
        tabs.add("Offers");
        tabs.add("Others");//We support upto 2 tabs only. Additional tabs will be ignored

        CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
        styleConfig.setFirstTabTitle("First Tab");//By default, name of the first tab is "ALL"
        styleConfig.setTabs(tabs);//Do not use this if you don't want to use tabs
        styleConfig.setTabBackgroundColor("#FF0000");//provide Hex code in string ONLY
        styleConfig.setSelectedTabIndicatorColor("#0000FF");
        styleConfig.setSelectedTabColor("#000000");
        styleConfig.setUnselectedTabColor("#FFFFFF");
        styleConfig.setBackButtonColor("#FF0000");
        styleConfig.setNavBarTitleColor("#FF0000");
        styleConfig.setNavBarTitle("MY INBOX");
        styleConfig.setNavBarColor("#FFFFFF");
        styleConfig.setInboxBackgroundColor("#00FF00");

        clevertapDefaultInstance.showAppInbox(styleConfig); //Opens activity tith Tabs
        //OR
        clevertapDefaultInstance.showAppInbox();//Opens Activity with default style config
    }

    @Override
    public void inboxMessagesDidUpdate() {

    }
}
