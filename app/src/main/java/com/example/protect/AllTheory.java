package com.example.protect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;
import java.util.Arrays;

public class AllTheory extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_theory);
        PDFView pdf = (PDFView) findViewById(R.id.allTheory);

        pdf.fromAsset(getIntent().getExtras().getString("pdfFile"))
                .defaultPage(getIntent().getExtras().getInt("page")-1).load();
    }
}