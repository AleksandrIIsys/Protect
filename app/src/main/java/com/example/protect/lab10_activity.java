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
import org.w3c.dom.Text;

import java.math.BigInteger;
import java.util.Random;
import java.util.function.BiFunction;

public class lab10_activity extends AppCompatActivity {

    static BigInteger qRSA;
    static BigInteger pRSA;
    static BigInteger nRSA;
    static BigInteger mRSA;
    static BigInteger eRSA;
    static BigInteger dRSA;
    static BigInteger sRSA;
    static BigInteger hRSA;
    static BigInteger textRSA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab10);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Toast toastCopy = Toast.makeText(this, "Скопировано в буфер обмена", Toast.LENGTH_SHORT);
        Toast error = Toast.makeText(this, "Произошла ошибка отправки сообщения получателю.\nПроверьте введенные данные", Toast.LENGTH_LONG);

        EditText text = findViewById(R.id.textForRSA);
        TextView openKey1 = findViewById(R.id.OpenKey1);
        TextView openKey2 = findViewById(R.id.OpenKey2);
        Button button = findViewById(R.id.encryptRSA);
        TextView resh1 = findViewById(R.id.hashOtpr);
        TextView resh2 = findViewById(R.id.hashPol);
        TextView eds = findViewById(R.id.EDSs);

        resh1.setOnClickListener(v->{
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resh1.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        resh2.setOnClickListener(v->{
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resh2.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });
        eds.setOnClickListener(v->{
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",eds.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        button.setOnClickListener(v->{
            try {
                pRSA = PRSA();
                qRSA = PRSA();
                nRSA = pRSA.multiply(qRSA);
                mRSA = (pRSA.subtract(BigInteger.valueOf(1))).multiply(qRSA.subtract(BigInteger.valueOf(1)));
                eRSA = E();
                dRSA = DRSA();
                resh1.setText(new BigInteger(md5Apache(String.valueOf(text.getText())),16).toString());
                eds.setText(RSA_Crypt(new BigInteger(resh1.getText().toString()),new BigInteger(eRSA.toString()),new BigInteger(nRSA.toString())).toString());
                openKey1.setText(eRSA.toString());
                openKey2.setText(nRSA.toString());
                resh2.setText(RSA_Crypt(new BigInteger(String.valueOf(eds.getText())), new BigInteger(String.valueOf(dRSA)), new BigInteger(String.valueOf(nRSA))).toString());
            }catch (Exception e){
                error.show();
            }
        });

    }
    public static BigInteger RSA_Crypt(BigInteger text, BigInteger a, BigInteger b){
        return text.modPow(a, b);
    }

    public static BigInteger E(){
        BigInteger e  = new BigInteger("2");
        while(!e.gcd(mRSA).equals(BigInteger.valueOf(1))) {
            e = e.add(BigInteger.valueOf(1));
        }
        return e;
    }

    public static BigInteger Evklid(BigInteger a,BigInteger b, BigInteger[] xy){
        if (a.compareTo(BigInteger.valueOf(0)) == 0) {
            xy[0] = BigInteger.valueOf(0);
            xy[1] = BigInteger.valueOf(1);
            return b;
        }
        BigInteger[] xy1 = new BigInteger[2];
        BigInteger d = Evklid (b.mod(a), a,xy1);
        xy[0] = xy1[1].subtract(b.divide(a).multiply(xy1[0]));
        xy[1] = xy1[0];
        return d;
    }


    public static BigInteger DRSA(){
        BigInteger[] xy = new BigInteger[2];
        Evklid(eRSA,mRSA,xy);
        return xy[0].mod(mRSA).add(mRSA).mod(mRSA);
    }

    public static BigInteger RSA_Decrypt(){
        BigInteger a =  textRSA.modPow(dRSA, nRSA);
        return a;
    }


    public static BigInteger PRSA(){

        BigInteger maxLimit = new BigInteger("340282366920938463463374607431768211455");
        Random randNum = new Random();
        int nlen = maxLimit.bitLength(); // 128 бит
        BigInteger nm1 = maxLimit.subtract(BigInteger.ONE);
        BigInteger randomNumber, temp;
        do {
            temp = new BigInteger(nlen + 100, randNum);
            randomNumber = temp.mod(maxLimit);
        } while (temp.subtract(randomNumber).add(nm1).bitLength() >= nlen + 100);

        while(true) {
            if(randomNumber.isProbablePrime(100))
                return randomNumber;
            do {
                temp = new BigInteger(nlen + 100, randNum);
                randomNumber = temp.mod(maxLimit);
            } while (temp.subtract(randomNumber).add(nm1).bitLength() >= nlen + 100);
        }
    }

    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }

}