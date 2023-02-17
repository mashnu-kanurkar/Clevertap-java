package com.example.clevertapjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.clevertap.android.sdk.CTWebInterface;
import com.clevertap.android.sdk.CleverTapAPI;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView mywebview = (WebView) findViewById(R.id.webView);
        mywebview.addJavascriptInterface(new CTWebInterface(CleverTapAPI.getDefaultInstance(this)),"CleverTap");
        mywebview.getSettings().setJavaScriptEnabled(true);

//        String html = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<body>\n" +
//                "<h1>HTML DOM Events</h1>\n" +
//                "<h2>The onclick Event</h2>\n" +
//                "\n" +
//                "<p>The onclick event triggers a function when an element is clicked on.</p>\n" +
//                "<p>Click to trigger a function that will output \"Hello World\":</p>\n" +
//                "\n" +
//                "<button onclick=\"myFunction()\">Click me</button>\n" +
//                "\n" +
//                "<p id=\"demo\"></p>\n" +
//                "\n" +
//                "<script>\n" +
//                "function myFunction() {\n" +
//                "  document.getElementById(\"demo\").innerHTML = \"Hello World\";\n" +
//                "  if (window.CleverTap) {\n" +
//                "  // Call Android interface             \n" +
//                " CleverTap.pushEvent(\"Webview Product Viewed\");          \n" +
//                "}\n" +
//                "}\n" +
//                "</script>\n" +
//                "\n" +
//                "</body>\n" +
//                "</html>\n";
//        mywebview.loadData(html, "text/html", "UTF-8");

        mywebview.loadUrl("https://mashnu-kanurkar.github.io/clever-tap-web-test/");

        mywebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                android.util.Log.d("WebViewLogs", consoleMessage.message());
                return true;
            }
        });


    }
}