package com.example.protect;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class lab3_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText textCezar = (EditText) findViewById(R.id.textForCezar);
        EditText koefCezar = (EditText) findViewById(R.id.koefForCezar);
        TextView resCezar = (TextView) findViewById(R.id.resCezar);
        Button enCezar = (Button) findViewById(R.id.encryptCezar);
        Button deCezar = (Button) findViewById(R.id.decryptCezar);
        Toast toast2 = Toast.makeText(this,"Неверный текст для расшифровки",Toast.LENGTH_SHORT);
        Toast toastCopy = Toast.makeText(this,"Скопировано в буфер обмена",Toast.LENGTH_SHORT);
        enCezar.setOnClickListener(view -> {
            try {
                resCezar.setText(EncryptingString(textCezar.getText().toString(), Integer.parseInt(koefCezar.getText().toString())));
            }catch(Exception e){
                toast2.show();
            }
        });


        deCezar.setOnClickListener(view -> {
            try {
                String text = DecryptingString(textCezar.getText().toString(), Integer.parseInt(koefCezar.getText().toString()));

                resCezar.setText(text);
            }catch (Exception e) {
                toast2.show();
            }
        });

        resCezar.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resCezar.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        EditText A = (EditText) findViewById(R.id.TextA);
        EditText B = (EditText) findViewById(R.id.TextB);
        EditText C = (EditText) findViewById(R.id.TextC);
        EditText textForTrifemus = (EditText) findViewById(R.id.textForTrifemus);
        TextView resTrifemus = (TextView) findViewById(R.id.resTrifemus);
        Button encryptTrifemus = (Button) findViewById(R.id.encryptTrifemus);
        Button decryptTrifemus = (Button) findViewById(R.id.decryptTrifemus);


        resTrifemus.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resTrifemus.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        encryptTrifemus.setOnClickListener(view -> {
            try {
                resTrifemus.setText(TrifemusEnCrypring(textForTrifemus.getText().toString(), StringForCryptingTrifemus(String.valueOf(textForTrifemus.getText())), Integer.parseInt(A.getText().toString()), Integer.parseInt(B.getText().toString()), Integer.parseInt(C.getText().toString())));
            }catch (Exception e){
                toast2.show();
            }
        });

        decryptTrifemus.setOnClickListener(view -> {
            try {
                resTrifemus.setText(TrifemusDeCrypring(textForTrifemus.getText().toString(), StringForCryptingTrifemus(String.valueOf(textForTrifemus.getText())), Integer.parseInt(A.getText().toString()), Integer.parseInt(B.getText().toString()), Integer.parseInt(C.getText().toString())));
            }catch(Exception e){
            toast2.show();}
        });

        EditText keyPlayfor = (EditText) findViewById(R.id.wordForPlayfor);
        EditText textForPlayfor = (EditText) findViewById(R.id.textForPlayfor);
        TextView resPlayfor = (TextView) findViewById(R.id.resPlayfor);
        Button encryptPlayfor = (Button) findViewById(R.id.encryptPlayfor);
        Button decryptPlayfor = (Button) findViewById(R.id.decryptPlayfor);

        resPlayfor.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",resPlayfor.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        encryptPlayfor.setOnClickListener(view -> {
            try {
                resPlayfor.setText(PlayforEnCrypt(reBuildEnCryptingString(textForPlayfor.getText().toString()), reBuildStringTable(keyPlayfor.getText().toString())));
            }catch (Exception e){
            toast2.show();
            }
        });

        decryptPlayfor.setOnClickListener(view -> {
            try{
                resPlayfor.setText(PlayforDeCrypt(textForPlayfor.getText().toString(), reBuildStringTable(keyPlayfor.getText().toString())));
            } catch(Exception e){
                toast2.show();
            }
        });

    }
    public static int isValid1(String str){
        boolean rus = false, eng = false, dig = false;
        for (int i = 0; i < str.length(); i++) {
            if (Objects.equals(Character.UnicodeBlock.of(str.charAt(i)), Character.UnicodeBlock.CYRILLIC))
                rus = true;
            if (Objects.equals(Character.UnicodeBlock.of(str.charAt(i)), Character.UnicodeBlock.BASIC_LATIN))
                eng = true;
            if (Character.isDigit(str.charAt(i)))
                dig = true;
        }
        if (dig || (rus && eng))
            return 3;
        if (rus)
            return 2;
        return 1;
    }

    public static String EncryptingString(String str,int k){
        str = str.replace(" ", "").toUpperCase();
        StringBuilder stringBuilder = new StringBuilder();
        char first, last;
        switch (isValid1(str)) {
            case 1:
                first = 'A';
                last = 'Z';
                break;
            case 2:
                first = 'А';
                last = 'Я';
                break;
            default:
                return "";
        }
        for (int i = 0; i < str.length(); i++) {
            if ((int) str.charAt(i) <= last - k) {
                stringBuilder.append((char) (str.charAt(i) + k));
            } else {
                stringBuilder.append((char) (first - 1 + k - (last - str.charAt(i)) % 10));
            }
        }
        return stringBuilder.toString();
    }

    public static String DecryptingString (String str,int k){
        str = str.replace(" ", "").toUpperCase();
        StringBuilder stringBuilder = new StringBuilder();
        char first, last;
        switch (isValid1(str)) {
            case 1:
                first = 'A';
                last = 'Z';
                break;
            case 2:
                first = 'А';
                last = 'Я';
                break;
            default:
                return "";
        }
        for (int i = 0; i < str.length(); i++) {
            if ((int) str.charAt(i) >= first + k) {
                stringBuilder.append((char) (str.charAt(i) - k));
            } else {
                stringBuilder.append((char) (last + 1 - k + (str.charAt(i) - first) % 10));
            }
        }
        return stringBuilder.toString();
    }

    public static int isValid2(String str) {
        boolean rus = false, eng = false, dig = false;
        for (int i = 0; i < str.length(); i++) {
            if (Objects.equals(Character.UnicodeBlock.of(str.charAt(i)), Character.UnicodeBlock.CYRILLIC))
                rus = true;
            if (Objects.equals(Character.UnicodeBlock.of(str.charAt(i)), Character.UnicodeBlock.LATIN_1_SUPPLEMENT))
                eng = true;
            if (Character.isDigit(str.charAt(i)))
                dig = true;
        }
        if (dig || (rus && eng))
            return 3;
        if (rus)
            return 2;
        return 1;
    }

    public static String StringForCryptingTrifemus(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        switch (isValid2(str)) {
            case 1:
                for (int i = 0; i < 26; i++) {
                    stringBuilder.append((char) ('A' + i));
                }
                stringBuilder.append(' ');
                stringBuilder.append(',');
                stringBuilder.append('.');
                break;
            case 2:
                for (int i = 0; i < 32; i++) {
                    stringBuilder.append((char) ('А' + i));
                }
                stringBuilder.append(' ');
                stringBuilder.append(',');
                stringBuilder.append('.');
                break;
            default:
                return "";
        }
        return stringBuilder.toString();
    }

    public static String TrifemusEnCrypring(String str, String forCrypt, int A, int B, int C) {
        int k;
        int lang = forCrypt.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            k = (int) (A * Math.pow(i, 2) + B * i + C);
            stringBuilder.append(forCrypt.charAt((forCrypt.indexOf(Character.toUpperCase(str.charAt(i))) + k) % lang));
        }
        return stringBuilder.toString();
    }

    public static String TrifemusDeCrypring(String str, String forCrypt, int A, int B, int C) {
        int k;
        int lang = isValid2(str) == 1 ? 29 : 35;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            k = (int) (A * Math.pow(i, 2) + B * i + C);
            final int letter = (forCrypt.indexOf(Character.toUpperCase(str.charAt(i))) - k) % lang;
            char a = forCrypt.charAt(letter < 0 ? lang - Math.abs(letter) : letter);
            stringBuilder.append(a);
        }
        return stringBuilder.toString();
    }
    //Plaifor
    public static String reBuildStringTable(String str) {
        str = str.replace(" ", "");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (stringBuilder.indexOf(String.valueOf(Character.toUpperCase(str.charAt(i)))) == -1)
                stringBuilder.append(Character.toUpperCase(str.charAt(i)));
        }
        switch (isValid(str)){
            case 1:
                for (int i = 0; i <= 25; i++) {
                    if ('A' + i == 'J')
                        continue;
                    if (stringBuilder.indexOf(String.valueOf((char) ('A' + i))) == -1)
                        stringBuilder.append((char) ('A' + i));
                }
                break;
            case 2:
                for (int i = 0; i <= 32; i++) {
                    if (stringBuilder.indexOf(String.valueOf((char) ('А' + i))) == -1)
                        stringBuilder.append((char) ('А' + i));
                }
                break;
            default:
                return "";
        }

        return stringBuilder.toString();
    }
    public static int isValid(String str) {
        boolean rus = false, eng = false, dig = false;
        for(int i = 0; i < str.length();i++) {
            if (Objects.equals(Character.UnicodeBlock.of(str.charAt(i)), Character.UnicodeBlock.CYRILLIC))
                rus = true;
            if (Objects.equals(Character.UnicodeBlock.of(str.charAt(i)), Character.UnicodeBlock.LATIN_1_SUPPLEMENT))
                eng = true;
            if (Character.isDigit(str.charAt(i)))
                dig = true;
        }
        if (dig || (rus && eng))
            return 3;
        if(rus)
            return 2;
        return 1;
    }
    public static ArrayList<String> reBuildEnCryptingString(String str) {
        if(isValid(str) == 3)
            return new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(str.replace(" ", "").toUpperCase());
        for (int i = 0; i < stringBuilder.length() - 1; i += 2) {
            if (stringBuilder.charAt(i) == stringBuilder.charAt(i + 1)) {
                if(isValid(str) == 1)
                    if(stringBuilder.charAt(i) != 'X')
                        stringBuilder.insert(i + 1, 'X');
                    else
                        stringBuilder.insert(i+1,'Q');
                if(isValid(str) == 2)
                    stringBuilder.insert(i + 1, 'Ъ');
            }
        }
        if(isValid(str) == 1)
            if (stringBuilder.length() % 2 != 0)
                if(stringBuilder.charAt(stringBuilder.length()-1) != 'X')
                    stringBuilder.append('X');
                else
                    stringBuilder.append('Q');
        if(isValid(str) == 2)
            if (stringBuilder.length() % 2 != 0)
                stringBuilder.append('Ъ');
        for (int i = 2; i < stringBuilder.length(); i += 3) {
            stringBuilder.insert(i, " ");
        }
        return new ArrayList<>(Arrays.asList(stringBuilder.toString().split(" ")));
    }

    public static char[][] CryptingTableENG(String str) {
        char[][] playfor = new char[5][5];
        int l = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                playfor[i][j] = str.charAt(l);
                l++;
            }
        }
        return playfor;
    }

    public static char[][] CryptingTableRUS(String str) {
        char[][] playfor = new char[4][8];
        int l = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                playfor[i][j] = str.charAt(l);
                l++;
            }
        }
        return playfor;
    }

    public static String PlayforEnCrypt(ArrayList<String> cryptingString, String fortable) {
        ArrayList<String> strings = new ArrayList<>();
        char[][] playfor;
        if(isValid(cryptingString.get(0)) != isValid(fortable))
            return "";
        switch (isValid(String.valueOf(fortable.charAt(0)))) {
            case 1:
                playfor = CryptingTableENG(fortable);
                break;
            case 2:
                playfor = CryptingTableRUS(fortable);
                break;
            default:
                return "";
        }

        int i1 = 0, j1 = 0, j2 = 0, i2 = 0;
        StringBuilder create;
        for (String st2r :
                cryptingString) {
            create = new StringBuilder();
            for (int i = 0; i < playfor.length; i++) {
                for (int j = 0; j < playfor[i].length; j++) {
                    if (st2r.charAt(0) == playfor[i][j]) {
                        i1 = i;
                        j1 = j;
                    }
                    if (st2r.charAt(1) == playfor[i][j]) {
                        i2 = i;
                        j2 = j;
                    }
                }
            }
            if (i1 == i2) {
                if (j1 == playfor[i1].length - 1)
                    create.append(playfor[i1][0]);
                else
                    create.append(playfor[i1][j1 + 1]);

                if (j2 == playfor[i2].length - 1)
                    create.append(playfor[i2][0]);
                else
                    create.append(playfor[i2][j2 + 1]);
            }
            if (j1 == j2) {
                if (i1 == playfor.length - 1)
                    create.append(playfor[0][j1]);
                else
                    create.append(playfor[i1 + 1][j1]);
                if (i2 == playfor.length - 1)
                    create.append(playfor[0][j2]);
                else
                    create.append(playfor[i2 + 1][j2]);
            }
            if (create.length() == 0) {
                create.append(playfor[i1][j2]);
                create.append(playfor[i2][j1]);
            }
            strings.add(create.toString());
        }
        return String.join("", strings);
    }

    public static String PlayforDeCrypt(String str, String fortable){
        char[][] playfor;
        if(isValid(str) != isValid(fortable))
            return "";
        switch (isValid(String.valueOf(fortable.charAt(0)))) {
            case 1:
                playfor = CryptingTableENG(fortable);
                break;
            case 2:
                playfor = CryptingTableRUS(fortable);
                break;
            default:
                return "";
        }

        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 2; i < stringBuilder.length(); i += 3) {
            stringBuilder.insert(i, " ");
        }

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(stringBuilder.toString().split(" ")));
        ArrayList<String> strings2 = new ArrayList<>();

        int i1 = 0,j1 = 0,i2 =0 ,j2= 0;
        StringBuilder create;
        for (String st2r:
                strings) {
            create = new StringBuilder();
            for (int i = 0; i < playfor.length; i++) {
                for (int j = 0; j < playfor[i].length; j++) {
                    if (st2r.charAt(0) == playfor[i][j]) {
                        i1 = i;
                        j1 = j;
                    }
                    if (st2r.charAt(1) == playfor[i][j]) {
                        i2 = i;
                        j2 = j;
                    }
                }
            }
            if (i1 == i2) {
                if (j1 == 0)
                    create.append(playfor[i1][playfor[i2].length-1]);
                else
                    create.append(playfor[i1][j1 - 1]);

                if (j2 == 0)
                    create.append(playfor[i2][playfor[i2].length-1]);
                else
                    create.append(playfor[i2][j2 - 1]);
            }
            if (j1 == j2) {
                if (i1 == 0)
                    create.append(playfor[playfor.length - 1][j1]);
                else
                    create.append(playfor[i1 - 1][j1]);
                if (i2 == 0)
                    create.append(playfor[playfor.length - 1][j2]);
                else
                    create.append(playfor[i2 -1][j2]);
            }

            if (create.length() == 0) {
                create.append(playfor[i1][j2]);
                create.append(playfor[i2][j1]);
            }
            strings2.add(create.toString());
        }
        return String.join("", strings2).replace("Ъ","").replace("X","");

    }

}