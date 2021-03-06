package com.example.kr_pc.mysampleapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FormActivity extends AppCompatActivity {
    //    private Button sendRequestButton;
//    private EditText receiptIdText;
//    private EditText sessionTokenText;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        //receiptIdText = findViewById(R.id.receipt_id_text);
        //sessionTokenText = findViewById(R.id.session_token_text);
        //sendRequestButton = findViewById(R.id.send_request_button);

        Intent intent = getIntent();

        if (intent != null) {
            String uri = String.valueOf(intent.getData());
            Toast.makeText(getApplicationContext(), uri, Toast.LENGTH_SHORT).show();
            setReceiptIdAndSesionToken(uri);
            final DownloadTask downloadTask = new DownloadTask(FormActivity.this);
            downloadTask.execute("http://eagle.sysitrex.com/webapi/api/printers/GetPrintouts?receiptId=Receipt636564612484060000.pdf&sessionId=123");
        }
    }


    private void setReceiptIdAndSesionToken(String uri) {
        if (uri == null) {
            return;
        }
        //Toast.makeText(getApplicationContext(), "Uri " + uri, Toast.LENGTH_SHORT).show();
        String receiptId = "";
        String sessionToken = "";
        int RECEIPT_INDEX = 31;
        int SESSION_TOKEN_INDEX = 0;

        for (int i = RECEIPT_INDEX; i < uri.length(); ++i) {
            if (uri.charAt(i) != '/') {
                receiptId += Character.valueOf(uri.charAt(i)).toString();
            } else {
                SESSION_TOKEN_INDEX = i + 1;
                break;
            }
        }

        for (int j = SESSION_TOKEN_INDEX; j < uri.length(); ++j) {
            sessionToken += Character.valueOf(uri.charAt(j)).toString();
        }

        //Toast.makeText(getApplicationContext(), receiptId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), sessionToken,Toast.LENGTH_SHORT).show();

        //receiptIdText.setText(receiptId);
        //sessionTokenText.setText(sessionToken);
    }
}