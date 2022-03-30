package com.example.protect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class lab11_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab11);
        PDFView pdf = (PDFView) findViewById(R.id.pdf11);

        pdf.fromAsset("11lab.pdf")
                .defaultPage(0).load();
    }
}