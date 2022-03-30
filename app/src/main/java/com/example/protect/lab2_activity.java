package com.example.protect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class lab2_activity extends AppCompatActivity {

    static int TypeSq = 0, IdSq = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Spinner IdSpinner = (Spinner) findViewById(R.id.IdSqSpinner);
        TextView textView = (TextView) findViewById(R.id.resMagicSquare);
        Toast toast = Toast.makeText(this,"Скопировано в буфер обмена",Toast.LENGTH_SHORT);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",textView.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                toast.show();
            }
        });

        Spinner TypeSpinner = (Spinner) findViewById( R.id.TypeSqSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"5", "6"});
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"1", "2", "3"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IdSpinner.setAdapter(adapter2);
        TypeSpinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int a = Integer.valueOf(adapterView.getItemAtPosition(i).toString());
                    TypeSq = a;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };

        AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IdSq = Integer.valueOf(adapterView.getItemAtPosition(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        TypeSpinner.setOnItemSelectedListener(itemSelectedListener);
        IdSpinner.setOnItemSelectedListener(itemSelectedListener2);
        EditText editText = (EditText) findViewById(R.id.EncryptText);
        Button butencrypt = (Button) findViewById(R.id.EncryptButton);
        Toast toast2 = Toast.makeText(this,"Неверный текст для расшифровки",Toast.LENGTH_SHORT);

        butencrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(MagicSquear(editText.getText().toString(),IdSq - 1,TypeSq));
            }
        });

        Button butdecrypt = (Button) findViewById(R.id.DecryptButton);

        butdecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length() != Math.pow(TypeSq,2))
                {
                    toast2.show();
                    return;
                }
                    textView.setText(AntiSquear(editText.getText().toString(),IdSq  - 1,TypeSq));
            }
        });

        EditText textForEncrypt = (EditText) findViewById(R.id.textForEncrypt);
        EditText wordForEncrypr = (EditText) findViewById(R.id.wordForEncrypr);
        TextView encryptingText = (TextView) findViewById(R.id.resCodeRable);

        encryptingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",encryptingText.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                toast.show();
            }
        });

        Button codeTable = (Button) findViewById(R.id.codeTable);
        Button decodeTable = (Button) findViewById(R.id.decodeTable);

        codeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                encryptingText.setText(String.valueOf(EncryptionTable(textForEncrypt.getText().toString(), wordForEncrypr.getText().toString())));
            }
        });

        decodeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = String.valueOf(DecryptionTable(textForEncrypt.getText().toString(), wordForEncrypr.getText().toString()));
                if(str == "")
                {
                    toast2.show();
                    return;
                }
                encryptingText.setText(str);
            }
        });

    }
        final static int[][][] x5 = {   {{21,24,2,3,15},{1,6,16,22,20},{14,12,19,7,13},{25,5,17,10,8},{4,18,11,23,9}},
                {{2,18,1,23,21},{12,25,5,4,19},{16,9,15,14,11},{13,3,24,17,8},{22,10,20,7,6}},
                {{4,24,10,15,12},{25,13,14,6,7},{3,18,22,20,2},{17,9,11,5,23},{16,1,8,19,21}}};

        final static int[][][] x6 = {
                {{22,36,7,2,9,35},{26,18,31,10,5,21},{13,23,15,24,28,8},{12,4,14,34,30,17},{6,1,33,25,19,27},{32,29,11,16,20,3}},
                {{18,28,3,12,15,35},{32,11,14,17,4,33},{20,9,24,13,16,29},{21,27,10,25,23,5},{1,30,34,8,31,7},{19,6,26,36,22,2}},
                {{8,10,24,4,32,33},{29,20,28,21,1,12},{36,5,22,14,3,31},{2,27,18,30,25,9},{17,26,6,35,16,11},{19,23,13,7,34,15}}};

        public static String MagicSquear(String str,int i,int size){
            char[][] fin = new char[size][size];
            int[][][] x = x5;
            switch (size){
                case 5:
                    x = x5;
                    break;
                case 6:
                    x = x6;
                    break;
            }
            for(int j = 0 ;j < size;j++) {
                for (int k = 0; k < size; k++) {
                    if (str.length() >= x[i][j][k]) {
                        fin[j][k] = str.charAt(x[i][j][k] - 1);
                    } else {
                        fin[j][k] = '.';
                    }
                }
            }
            String answer = "";
            for(int j = 0; j < size;j++)
                answer += String.valueOf(fin[j]);
            return answer;
        }
        public static String AntiSquear(String str,int i,int size){
            char[][] fin = new char[size][size];
            int t = 0;
            int[][][] x = x5;
            switch (size){
                case 5:
                    x = x5;
                    break;
                case 6:
                    x = x6;
                    break;
            }
            for(int j = 0; j< size;j++){
                for(int k = 0; k < size;k++){
                    fin[j][k] = str.charAt(t);
                    t++;
                }
            }
            char[] mass = new char[size*size];
            for(int j = 0 ;j < size;j++) {

                for (int k = 0; k < size; k++) {
                    if(fin[j][k] != '.'){
                        mass[x[i][j][k]-1] = fin[j][k];
                    }
                }

            }
            return String.valueOf(mass).replace("\0","");
        }

        public static String EncryptionTable(String str, String code) {
            int n = 0;
            for (int i = 0; i < code.length(); i++) {
                n += Integer.valueOf(code.charAt(i));
            }
            int sum = 0;
            while (n != 0) {
                sum += n % 10;
                n = n / 10;
                if (n == 0 && sum > 10) {
                    if(Math.ceil(Math.sqrt(str.length())) > 10 && sum <= Math.ceil(Math.sqrt(str.length())))
                    {
                        break;
                    }
                    n = sum;
                    sum = 0;
                }
            }
            sum = sum == 1 ? sum++ : sum;
            int m = (int) Math.ceil((double) str.length() / sum);
            char[][] arr = new char[sum][m];
            int count = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < sum; j++) {
                    if (count >= str.length()) {
                        arr[j][i] = '_';
                        count++;
                    } else {
                        arr[j][i] = str.charAt(count);
                        count++;
                    }
                }
            }
            String res = "";
            for (int i = 0; i < sum; i++) {
                res += String.valueOf(arr[i]);
            }
            return res;
        }

        public static String DecryptionTable(String str, String code) {
            int n = 0;
            for (int i = 0; i < code.length(); i++) {
                if(str.charAt(i) != '_')
                    n += Integer.valueOf(code.charAt(i));
            }
            int sum = 0;
            while (n != 0) {
                sum += n % 10;
                n = n / 10;
                if (n == 0 && sum > 10) {
                    if(Math.ceil(Math.sqrt(str.length())) > 10 && sum <= Math.ceil(Math.sqrt(str.length())))
                    {
                        break;
                    }
                    sum = sum == 1 ? sum++ : sum;
                    n = sum;
                    sum = 0;
                }
            }
            int m = (int) Math.ceil((double) str.length() / sum);
            char[][] arr = new char[sum][m];
            int count = 0;
            try {
                for (int i = 0; i < sum; i++) {
                    for (int j = 0; j < m; j++) {
                        if(str.charAt(count) != '_' )
                            arr[i][j] = str.charAt(count);
                        count++;

                    }
                }
            }catch (StringIndexOutOfBoundsException e){
                return "";
            }

            String res = "";
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < sum; j++) {
                    res += arr[j][i];
                }
            }
            return res;
        }
}