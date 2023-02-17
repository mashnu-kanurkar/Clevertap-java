package com.example.clevertapjava;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clevertap.android.sdk.BuildConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InAppNotificationButtonListener;
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements InAppNotificationButtonListener {

    private Button btnEvent, btnLogin, btnGoToWeb, btnNativeDisplay, btnInbox;
    private CleverTapAPI clevertapDefaultInstance;
    private EditText edtName, edtEmail;
    private String eventName = "Charged";
    private String appsFlyerKey = "3FYoYANwUDTCv8ocLU6ZFZ";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S){
            Log.d("CleverTap", "On new intent fired on Version > S");
            Bundle bundle = intent.getExtras();
            clevertapDefaultInstance.pushNotificationClickedEvent(bundle);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEvent = findViewById(R.id.btn_event);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToWeb = findViewById(R.id.btnGoToWeb);
        btnNativeDisplay = findViewById(R.id.btnNativeDisplay);
        edtName = findViewById(R.id.editName);
        edtEmail = findViewById(R.id.editEmail);
        btnInbox = findViewById(R.id.btnInbox);

        // Creates a button that mimics a crash when pressed
        Button crashButton = new Button(this);
        crashButton.setText("Test Crash");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int test = Integer.parseInt("ABC");
            }
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        clevertapDefaultInstance.setInAppNotificationButtonListener(this);
        clevertapDefaultInstance.initializeInbox();

        CleverTapAPI.createNotificationChannel(getApplicationContext(),"general","general","general", NotificationManager.IMPORTANCE_MAX,true);

        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent(eventName);
                Toast.makeText(MainActivity.this, "Pushed event: "+eventName, Toast.LENGTH_SHORT).show();
//                ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
//                        "Loading", true);
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Write whatever to want to do after delay specified (1 sec)
//                        Log.d("Handler", "Running Handler");
//                        dialog.dismiss();
//                    }
//                }, 3000);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnGoToWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        btnNativeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NativeDisplayActivity.class);
                startActivity(intent);

            }
        });


        btnInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppInboxWebView.class);
                startActivity(intent);
            }
        });
        //clevertapDefaultInstance.getAllInboxMessages();


    }

    private void login() {
        if (edtName.getText() == null){
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtEmail.getText() == null){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String identity = name.replaceAll(" ", "");

        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Name", name);    // String
        profileUpdate.put("Identity", identity);      // String or number
        profileUpdate.put("Email", email); // Email address of the user
        //profileUpdate.put("Phone", "+14155551234");
        clevertapDefaultInstance.onUserLogin(profileUpdate );

        Toast.makeText(this, "Logged in with name: "+name+", identity: "+identity+", email: "+email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInAppButtonClick(HashMap<String, String> payload) {
        Log.d("Clevertap", payload.toString());
        if(payload != null){
            //Read the values
            if (payload.containsKey("action")){
                if (payload.get("action") == "dismiss"){
                    Log.d("Main", payload.toString());
                }
            }
        }
    }
}