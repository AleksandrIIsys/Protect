package com.example.protect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class lab8_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab8);
        PDFView pdf = (PDFView) findViewById(R.id.pdf8);

        pdf.fromAsset("8lab.pdf")
                .defaultPage(0).load();
    }
}