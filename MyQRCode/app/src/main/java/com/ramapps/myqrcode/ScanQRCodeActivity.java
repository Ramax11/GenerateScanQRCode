package com.ramapps.myqrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramachandran on 3/13/18.
 */

public class ScanQRCodeActivity extends AppCompatActivity {

    private TextView mName, mAddress;
    private Button mBtnScanner;
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);

        mName = findViewById(R.id.textViewName);
        //mAddress = findViewById(R.id.textViewAddress);
        mBtnScanner = findViewById(R.id.btn_scanner);

        qrScan = new IntentIntegrator(this);

        mBtnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            System.out.println("result: "+ result.toString());
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    mName.setText(result.getContents());
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    /*mName.setText(obj.getString("name"));
                    mAddress.setText(obj.getString("address"));*/
                    System.out.println("resultContents: "+ result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
