package com.example.clevertapjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CTWebInterface;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InboxMessageButtonListener;
import com.clevertap.android.sdk.inbox.CTInboxMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class AppInboxWebView extends AppCompatActivity implements CTInboxListener, InboxMessageButtonListener {

    //WebView webView;
    CleverTapAPI cleverTapDefaultInstance;
    ArrayList<CTInboxMessage> inboxMessages = new ArrayList<>();
    ArrayList<String> msgList = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_inbox_webview);
        //webView = (WebView) findViewById(R.id.inboxWebView);
        //webView.addJavascriptInterface(new CTWebInterface(CleverTapAPI.getDefaultInstance(this)),"CleverTap");
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl("https://mashnu-kanurkar.github.io/clever-tap-web-test/");


//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                android.util.Log.d("WebViewLogs", consoleMessage.message());
//                return true;
//            }
//        });

        listView = findViewById(R.id.list_view);

        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        if(cleverTapDefaultInstance != null){
            cleverTapDefaultInstance.setCTNotificationInboxListener(this);
            cleverTapDefaultInstance.initializeInbox();
            Log.d("INBOX", "Initialising inbox");
        }


    }

    @Override
    public void inboxDidInitialize() {
        Log.d("INBOX: ", "inbox init done");
        inboxMessages = cleverTapDefaultInstance.getAllInboxMessages();
        Log.d("INBOX: ", inboxMessages.toString());
        if(inboxMessages != null) {

            Log.d("INBOX: ", inboxMessages.toString());
            if (inboxMessages != null) {
                for (int i = 0; i < inboxMessages.size(); i++) {
                    msgList.add(inboxMessages.get(i).getMessageId());
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, msgList);
                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void inboxMessagesDidUpdate() {

    }

    @Override
    public void onInboxButtonClick(HashMap<String, String> payload) {
        
    }
}