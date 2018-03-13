package com.ramapps.myqrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by ramachandran on 3/13/18.
 */

public class GenerateQRCodeActivity extends AppCompatActivity {

    private EditText mName, mAddress;
    private Button mGenerateQR;
    private ImageView mQRImage;
    private ProgressBar progressBar;
    String editTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        mName = findViewById(R.id.edit_name);
        mAddress = findViewById(R.id.edit_address);
        mGenerateQR = findViewById(R.id.btn_generate_qr_code);
        mQRImage = findViewById(R.id.iv_view_qr_image);
        progressBar = findViewById(R.id.progressbar);

        mGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                String name = mName.getText().toString().trim();
                String address = mAddress.getText().toString().trim();
                if (name.length() > 0 || address.length() > 0 ){
                    try {
                        editTextValue = name +" "+ address;
                        bitmap = TextToImageEncode(editTextValue);

                        mQRImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.required_text_field, Toast.LENGTH_SHORT).show();
                }
                //progressBar.setVisibility(View.GONE);

            }
        });

    }


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}
