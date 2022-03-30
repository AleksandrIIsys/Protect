package com.example.protect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class lab9_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab9);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Toast toastCopy = Toast.makeText(this, "Скопировано в буфер обмена", Toast.LENGTH_SHORT);
        Toast errorGenerate = Toast.makeText(this, "Что-то пошло не так, проверьте введённые данные", Toast.LENGTH_SHORT);

        EditText task1N = findViewById(R.id.Task1N);
        EditText task1K = findViewById(R.id.Task1K);
        EditText task1S = findViewById(R.id.Task1S);
        EditText task1M = findViewById(R.id.Task1M);
        EditText task1V = findViewById(R.id.Task1V);
        Button task1 = findViewById(R.id.Task1);
        TextView resTask1 = findViewById(R.id.resTask1);

        resTask1.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resTask1.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        task1.setOnClickListener(view ->{
            try {
                long n = Integer.parseInt(String.valueOf(task1N.getText()));
                long k = Integer.parseInt(String.valueOf(task1K.getText()));
                long s = Integer.parseInt(String.valueOf(task1S.getText()));
                long m = String.valueOf(task1M.getText()).length() != 0 ? Integer.parseInt(String.valueOf(task1M.getText())) : 0;
                long v = String.valueOf(task1V.getText()).length() != 0 ? Integer.parseInt(String.valueOf(task1V.getText())) : 0;
                resTask1.setText(String.valueOf(task1(n, k, s, m, v)));
            }catch (Exception e){
                errorGenerate.show();
            }
        });

        EditText task2N = findViewById(R.id.Task2N);
        EditText task2T = findViewById(R.id.Task2T);
        EditText task2S = findViewById(R.id.Task2S);
        Button task2 = findViewById(R.id.Task2);
        TextView resTask2 = findViewById(R.id.resTask2);

        resTask2.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resTask2.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        task2.setOnClickListener(view ->{
            try {
                long n =  Integer.parseInt(String.valueOf(task2N.getText()));
                long s = Integer.parseInt(String.valueOf(task2S.getText()));
                long t = Integer.parseInt(String.valueOf(task2T.getText()));
                resTask2.setText(String.valueOf(task2(n, t, s)));
            }catch (Exception e){
                errorGenerate.show();
            }
        });

        EditText task3K = findViewById(R.id.Task3K);
        EditText task3T = findViewById(R.id.Task3T);
        EditText task3S = findViewById(R.id.Task3S);
        Button task3 = findViewById(R.id.Task3);
        TextView resTask3 = findViewById(R.id.resTask3);

        resTask3.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resTask3.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        task3.setOnClickListener(view ->{
            try {
                long k = Integer.parseInt(String.valueOf(task3K.getText()));
                long s = Integer.parseInt(String.valueOf(task3S.getText()));
                long t = Integer.parseInt(String.valueOf(task3T.getText()));
                resTask3.setText(String.valueOf(task3(k, t, s)));
            }catch (Exception e){
                errorGenerate.show();
            }
        });
    }

    public static double task1(long n, long k, long s, long m, long v){
        double c = Math.pow(n,k);
        double t = c/s;
        double T;
        if(m == 0 || v == 0)
            T = 0;
        else T = t * v / m;
        double Tend;
        Tend = T + t;
        return  (Math.round(Tend/(3600*24)*100)/100.);
    }

    static double task2(long n, long t, long s){
        double c = t * s * 365 * 24 * 60 * 60;
        double k = Math.log(c)/Math.log(n);
        return  (Math.ceil(k));
    }
    static int task3(long k, long t, long s){
        double c = s * t * 365 * 24*60*60;;
        return  (int)Math.exp(Math.log(c)/k);
    }
}