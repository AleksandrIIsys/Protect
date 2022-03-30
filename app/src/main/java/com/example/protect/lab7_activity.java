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

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.util.Random;

public class lab7_activity extends AppCompatActivity {

    static int p;
    static int g;
    static int a;
    static int A;
    static int b;
    static int B;
    static int s1;
    static int s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab7);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Toast toastCopy = Toast.makeText(this, "Скопировано в буфер обмена", Toast.LENGTH_SHORT);
        Toast errorRand = Toast.makeText(this, "Рандом сегодня не рандомит", Toast.LENGTH_SHORT);
        Toast errorGenerate = Toast.makeText(this, "Что-то пошло не так, проверьте введённые данные", Toast.LENGTH_SHORT);


        EditText pKey = findViewById(R.id.pKey);
        EditText gKey = findViewById(R.id.gKey);
        Button pRand = findViewById(R.id.pRand);
        Button gRand = findViewById(R.id.gRand);

        Button encrDifi = findViewById(R.id.encryptDifi);
        TextView resS1 = findViewById(R.id.resS1);
        TextView resS2 = findViewById(R.id.resS2);

        resS1.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resS1.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        resS2.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resS2.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        pRand.setOnClickListener(view -> {
            try{
                int key = gKey.getText().toString().length() != 0 ? Integer.parseInt(gKey.getText().toString()) : 1;
                if(key <=0 ) key = 1;
                pKey.setText(String.valueOf(P(key + 1)));
            }catch (Exception e){
                errorRand.show();
            }
        });

        gRand.setOnClickListener(view -> {
            try{
                gKey.setText(String.valueOf(randomDigit(1, Integer.parseInt(pKey.getText().toString()))));
            }catch (Exception e){
                errorRand.show();
            }
        });

        encrDifi.setOnClickListener(view ->{
            try{
                p = Integer.parseInt(pKey.getText().toString());
                g = Integer.parseInt(gKey.getText().toString());
                if(g <= 0 || g >= p) throw new Exception();
                a = randomDigit();
                b = randomDigit();
                A = power(g, a, p);
                B = power(g, b, p);
                s1 = power(A, b, p);
                s2 = power(B, a, p);
                resS1.setText(String.valueOf(s1));
                resS2.setText(String.valueOf(s2));
            }catch(Exception e){
                errorGenerate.show();
            }
        });

        EditText textMD5 = findViewById(R.id.textForMD5);
        Button encrMD5 = findViewById(R.id.encryptMD5);
        TextView resMD5 = findViewById(R.id.resultMD5);

        resMD5.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resMD5.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        encrMD5.setOnClickListener(view -> {
            try{
                resMD5.setText(md5Apache(String.valueOf(textMD5.getText())));
            }catch (Exception e){
                errorGenerate.show();
            }
        });

    }

    public static int P(int min){
        int max = 10000;
        Random r = new Random();
        int p = (r.nextInt(max-min+1)+min);
        while(true) {
            if(Deliteli(p)) return p;
            p = (r.nextInt(max-min+1)+min);
        }
    }

    public static int randomDigit(){
        return randomDigit(1, 10000);
    }

    public static int randomDigit(int min, int max){
        Random r = new Random();
        return (r.nextInt(max-min)+min);
    }

    public static boolean Deliteli(int a){
        int i = 2;
        while(i < a){
            if (a % i == 0) {
                return false;
            }
            i++;
        }
        return true;
    }

        public static int power(int a, int b, int n){
            BigInteger big = new BigInteger(String.valueOf(1));
            BigInteger abs = new BigInteger(String.valueOf(a));
            while(b >= 2){
                if(b%2 == 1)
                {
                    big = big.multiply(abs);
                    b--;
                }
                b/=2;
                abs = abs.multiply(abs).mod(BigInteger.valueOf(n));
            }
            big = big.multiply(abs).mod(BigInteger.valueOf(n));
            return Integer.parseInt(big.toString());
        }


        public static void main(String[] args) {

            int g = (int) (Math.random()*100), p = (int) (Math.random()*100);
            int a = (int) (Math.random()*100), b = (int) (Math.random()*100);
            //p = 23; g = 5; a = 6; b = 15;
            int A = power(g,a, p),B= power(g,b, p);
            int s1 = power(A,b, p),s2 = power(B,a, p);

            System.out.println(s1 == s2);
            System.out.println(A+" " +B);
            System.out.println(s1 + " " + s2);
        }

    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }

}