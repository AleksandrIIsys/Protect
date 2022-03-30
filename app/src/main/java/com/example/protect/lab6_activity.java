package com.example.protect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class lab6_activity extends AppCompatActivity {
    //два открытых поля для показа е и m
    // получить число для шифровки/расшифровки
    // кнопки шифр/расшифр
    //результат

    static BigInteger qRSA;
    static BigInteger pRSA;
    static BigInteger nRSA;
    static BigInteger mRSA;
    static BigInteger eRSA;
    static BigInteger dRSA;
    static BigInteger textRSA;
    static int pEL;
    static int gEL;
    static int xEL;
    static int yEL;
    static int kEL;
    static int textEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab6);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Toast toastCopy = Toast.makeText(this, "Скопировано в буфер обмена", Toast.LENGTH_SHORT);
        Toast errorEn = Toast.makeText(this, "Зашифровать данное сообщение не удалось.\nПроверьте введенные данные", Toast.LENGTH_LONG);
        Toast errorDe = Toast.makeText(this, "Расшифровать данное сообщение не удалось.\nПроверьте введенные данные", Toast.LENGTH_LONG);

        EditText textForRSA = findViewById(R.id.textForRSA);
        TextView openKey1 = findViewById(R.id.OpenKey1);
        TextView openKey2 = findViewById(R.id.OpenKey2);
        Button encrRSA = findViewById(R.id.encryptRSA);
        Button decrRSA = findViewById(R.id.decryptRSA);
        TextView resRSA = findViewById(R.id.resultRSA);


        TextView textForEL = findViewById(R.id.textForElGam);
        EditText keyP = findViewById(R.id.Key1);
        EditText keyG = findViewById(R.id.Key2);
        EditText keyY = findViewById(R.id.Key3);
        Button encrEl = findViewById(R.id.encryptElGam);
        Button decrEl = findViewById(R.id.decryptElGam);
        TextView resElGam = findViewById(R.id.resultElGam);

        encrRSA.setOnClickListener(v -> {
            try {
                textRSA = new BigInteger(textForRSA.getText().toString());
                pRSA = PRSA();
                qRSA = PRSA();

                nRSA = pRSA.multiply(qRSA);
                mRSA = (pRSA.subtract(BigInteger.valueOf(1))).multiply(qRSA.subtract(BigInteger.valueOf(1)));
                eRSA = E();

                openKey1.setText(eRSA.toString());
                openKey2.setText(nRSA.toString());
                resRSA.setText(String.valueOf(RSA_Crypt()));
            }catch(Exception e) {
                errorEn.show();
            }
        });

        decrRSA.setOnClickListener(v-> {
            try {
                textRSA = new BigInteger(textForRSA.getText().toString());
                eRSA = new BigInteger(openKey1.getText().toString());
                nRSA = new BigInteger(openKey2.getText().toString());
                mRSA = (pRSA.subtract(BigInteger.valueOf(1))).multiply(qRSA.subtract(BigInteger.valueOf(1)));
                dRSA = DRSA();
                resRSA.setText(String.valueOf(RSA_Decrypt()));
            }
            catch(Exception e){
                errorDe.show();
            }
        });

        encrEl.setOnClickListener(v -> {
            try {
                textEL = Integer.parseInt(textForEL.getText().toString());
                pEL = PEL();
                gEL = GEL();
                kEL = KEL();
                xEL = XEL();
                yEL = YEL();

                keyP.setText(String.valueOf(pEL));
                keyG.setText(String.valueOf(gEL));
                keyY.setText(String.valueOf(yEL));
                resElGam.setText(Arrays.toString(EncElGam()));
            }catch(Exception e) {
                errorEn.show();
            }
        });

        decrEl.setOnClickListener(v-> {
            try {
                String str = textForEL.getText().toString();
                str = str.substring(1, str.length()-1).replace(" ", "");
                String[] arr = str.split(",");
                int[] forEl = new int[]{Integer.parseInt(arr[0]), Integer.parseInt(arr[1])};
                pEL = Integer.parseInt(keyP.getText().toString());
                gEL = Integer.parseInt(keyG.getText().toString());
                yEL = Integer.parseInt(keyY.getText().toString());

                resElGam.setText(String.valueOf(DecElGam(forEl)));
            }
            catch(Exception e){
                errorDe.show();
            }
        });

        resRSA.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resRSA.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        resElGam.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resElGam.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });
    }
            public static BigInteger RSA_Crypt(){
                return textRSA.modPow(eRSA, nRSA);
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
            BigInteger maxLimit = new BigInteger("429496729346356365");
            Random randNum = new Random();
            int nlen = maxLimit.bitLength(); // 32 бит
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


    // открытые ключи p, g, y
    //ответ в виде (a,b)
    public static int PEL(){
        int min = textEL+1;
        int max = 10000;
        Random r = new Random();
        int p = (r.nextInt(max-min+1)+min);
        while(true) {
            if(Deliteli(p)) return p;
            p = (r.nextInt(max-min+1)+min);
        }
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

    public static int gcd(int a,int b) {
        while (b !=0) {
            int tmp = a%b;
            a = b;
            b = tmp;
        }
        return a;
    }

        public static int XEL(){
            Random r = new Random();
            return r.nextInt(pEL-2)+1;
        }

        public static int KEL(){
            Random rand = new Random();
            int a = rand.nextInt(pEL-1);
            while(gcd(a,pEL-1) != 1){
                a = rand.nextInt(pEL-1);
            }
            return a;
        }

        public static int YEL(){
            BigInteger bigInteger = new BigInteger(String.valueOf(gEL));
            bigInteger = bigInteger.pow(xEL);
            bigInteger = bigInteger.mod( BigInteger.valueOf(pEL));
            return Integer.parseInt(bigInteger.toString());
        }

        public static int[] EncElGam(){
            BigInteger a = new BigInteger(String.valueOf(gEL));
            BigInteger b = new BigInteger(String.valueOf(yEL));
            a = a.pow(kEL);
            a = a.mod(BigInteger.valueOf(pEL));
            b = b.pow(kEL);
            b = b.multiply(BigInteger.valueOf(textEL));
            b = b.mod(BigInteger.valueOf(pEL));
            return new int[]{Integer.parseInt(a.toString()), Integer.parseInt(b.toString())};
        }

        public static int DecElGam(int[] arr){
            int a = arr[0];
            int b = arr[1];
            BigInteger m = new BigInteger(String.valueOf(a));
            m = m.pow(pEL-1-xEL);
            m= m.multiply(BigInteger.valueOf(b));
            m = m.mod(BigInteger.valueOf(pEL));
            return Integer.parseInt(m.toString());
        }

    public static int powmodForG (int a, int b, int p) {
        int res = 1;
        while (b != 0)
            if (b%2 == 1) {
                res = (int) (res * a % p);
                --b;
            }
            else {
                a = (int) (a * a % p);
                b >>= 1;
            }
        return res;
    }
//
        public static int GEL() {
            ArrayList<Integer> fact = new ArrayList<Integer>();
            int phi = pEL-1,  n = phi;
            for (int i=2; i*i<=n; ++i)
                if (n % i == 0) {
                    fact.add (i);
                    while (n % i == 0)
                        n /= i;
                }
            if (n > 1)
                fact.add (n);

            for (int res=2; res<=pEL; ++res) {
                boolean ok = true;
                for (int i=0; i<fact.size() && ok; ++i)
                    ok &= powmodForG (res, phi / fact.get(i), pEL) != 1;
                if (ok)  return res;
            }
            return -1;
        }
    }


