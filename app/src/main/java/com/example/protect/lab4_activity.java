package com.example.protect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Random;

public class lab4_activity extends AppCompatActivity {
    int secretDig = DigitRand();
    int typeCode;
    TextView text;
    TextView cows;
    TextView bulls;
    ArrayList<Integer> HISTORY = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4);

        Toast toast = Toast.makeText(this, "Символы не могут повторяться", Toast.LENGTH_SHORT);
        Toast toast2 = Toast.makeText(this,"Неверный текст",Toast.LENGTH_SHORT);
        Toast toastCopy = Toast.makeText(this,"Скопировано в буфер обмена",Toast.LENGTH_SHORT);
        Toast toastDuplicate = Toast.makeText(this,"В истории уже есть такое число",Toast.LENGTH_SHORT);
        Toast toastWin = Toast.makeText(this, "Вам понадобилось " + HISTORY.size() + " попыток", Toast.LENGTH_LONG);
        Button generate = (Button) findViewById(R.id.generate);
        Spinner type  = (Spinner) findViewById(R.id.type);
        EditText number = (EditText) findViewById(R.id.NumberEl);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"3-12 целые", "{–3, 0, 6, 9, 12, 15}", "3-12 вещ", "(–2,3)-10,7 с шагом 0,1", "{–30; 10; 63; 59; 120; 175}" , "{1; 0,1; 0,01;…10^–15}"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        TextView resRandNum = (TextView) findViewById(R.id.resRandNum);

        resRandNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",resRandNum.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                toastCopy.show();
            }
        });

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String a = adapterView.getItemAtPosition(i).toString();
                switch(a){
                    case "3-12 целые": typeCode = 1; break;
                    case "{–3, 0, 6, 9, 12, 15}" : typeCode = 2; break;
                    case"3-12 вещ": typeCode = 3; break;
                    case "(–2,3)-10,7 с шагом 0,1": typeCode = 4; break;
                    case "{–30; 10; 63; 59; 120; 175}" : typeCode = 5; break;
                    case"{1; 0,1; 0,01;…10^–15}": typeCode = 6; break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        type.setOnItemSelectedListener(itemSelectedListener);

        generate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    resRandNum.setText(RandomDigitals(typeCode, Integer.parseInt(number.getText().toString())));
                }catch(Exception e){
                    toast2.show();
                    return;
                }
            }

        });
        Button tried = (Button) findViewById(R.id.tried);
        Button surrender = (Button) findViewById(R.id.surrender);

        TextView resCowsBulls = (TextView) findViewById(R.id.resCowsBulls);
        Button history = (Button) findViewById(R.id.history);
        text = (TextView) findViewById(R.id.textDigit);
        cows = (TextView) findViewById(R.id.Cows);
        bulls = (TextView) findViewById(R.id.Bulls);
        tried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources textColor = getResources();
                int resColor = textColor.getColor(R.color.blueForRes);
                int winColor = textColor.getColor(R.color.greenForWin, null);
                resCowsBulls.setTextColor(resColor);
                try {
                    if(CheckEnterText(text.getText().toString())) {
                        if(text.getText().toString().length() != 4)
                            throw new Exception();
                        cows.setText(String.valueOf(Cows(secretDig, Integer.parseInt(text.getText().toString())) - Bulls(secretDig, Integer.parseInt(text.getText().toString()))));
                        bulls.setText(String.valueOf(Bulls(secretDig, Integer.parseInt(text.getText().toString()))));

                        if (String.valueOf(bulls.getText()) == String.valueOf(4)) {
                            resCowsBulls.setText(String.valueOf(secretDig));
                            textForWin();
                            HISTORY.clear();
                            resCowsBulls.setTextColor(winColor);
                        } else{
                            resCowsBulls.setText("Секрет");
                            if(!HISTORY.contains(Integer.parseInt(text.getText().toString())))
                                HISTORY.add(Integer.parseInt(text.getText().toString()));
                            else
                                toastDuplicate.show();
                        }
                    }
                    else throw new InvalidKeyException();
                } catch(InvalidKeyException e){
                    toast.show();
                    return;
                } catch(Exception e){
                    toast2.show();
                    return;
                }
            }

        });

        surrender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resCowsBulls.setText(String.valueOf(secretDig));
                secretDig = DigitRand();
                HISTORY.clear();
                resCowsBulls.setTextColor(Color.RED);
            }
        });

        resCowsBulls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",resCowsBulls.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                toastCopy.show();
            }
        });
    history.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showPopuMenu(view);
        }
    });
    }
    public void textForWin(){
        Toast.makeText(this, "Вам понадобилось " + HISTORY.size() + " попыток", Toast.LENGTH_LONG).show();
    }
    private void showPopuMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        Menu menu = popupMenu.getMenu();
        if(HISTORY.size() == 0){
            Toast.makeText(this,"Вы не ввели еще ни одного числа", Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i = 0; i < HISTORY.size();i++){
            menu.add(i,i,i,String.valueOf(HISTORY.get(i)));
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                test(menuItem.getItemId());
                return false;
            }
        });
        popupMenu.show();

    }
    private void test(int i){
        text.setText(String.valueOf(HISTORY.get(i)));
        cows.setText(String.valueOf(Cows(secretDig, HISTORY.get(i)) - Bulls(secretDig, HISTORY.get(i))));
        bulls.setText(String.valueOf(Bulls(secretDig, HISTORY.get(i))));

    }
    public static boolean CheckEnterText(String text){
        StringBuilder str = new StringBuilder(text);
        for (int i=0;i<str.length();i++){
            if(str.lastIndexOf(String.valueOf(str.charAt(i))) != str.indexOf(String.valueOf(str.charAt(i)))){
              //if(str.indexOf(String.valueOf(str.charAt(i))) != -1 &&  str.indexOf(String.valueOf(str.charAt(i))) != i){
                return false;
            }
        }
        return true;
    }
    public static int DigitRand(){
        Random rand = new Random();
        boolean t = true;
        int dig;
        StringBuilder str = new StringBuilder();
        str.append(rand.nextInt(9) + 1);
        while(t) {
            dig = rand.nextInt(10);
            if(str.indexOf(String.valueOf(dig)) == -1){
                str.append(dig);
            }
            if(str.length() == 4) t = false;
        }
        return Integer.parseInt(str.toString());
    }
    public static int Cows(int dig, int trial){
        StringBuilder builder = new StringBuilder();
        builder.append(dig);
        String str = String.valueOf(trial);
        int cow = 0;
        for(int i = 0; i<str.length(); i++){
            if(builder.indexOf(String.valueOf(str.charAt(i))) != -1) cow++;
        }
        return cow;
    }

    public static int Bulls(int dig, int trial){
        StringBuilder builder = new StringBuilder();
        builder.append(dig);
        String str = String.valueOf(trial);
        int bull = 0;
        for(int i = 0; i<str.length(); i++){
            if(builder.indexOf(String.valueOf(str.charAt(i))) != -1 && i == builder.indexOf(String.valueOf(str.charAt(i)), i)) bull++;
        }
        return bull;
    }
    public static String RandomDigitals(int i, int n){
        Random random = new Random();
        double dig;
        int arr[] =  {-30, 10, 63, 59, 120, 175};
        ArrayList<String> str = new ArrayList<String>();
        while(str.size() != n)
            switch (i){
                case 1:
                    str.add(String.valueOf(random.nextInt(10)+3));
                    break;
                case 2:
                    dig = random.nextInt(19)-3;
                    if(dig%3 != 0) {
                        continue;
                    }
                    str.add(String.valueOf(dig));
                    break;
                case 3:
                    str.add(String.valueOf(random.nextDouble()*10+2));
                    break;
                case 4:
                    dig = Math.floor(10*(random.nextDouble()*13-2.3))/10;
                    if(dig < -2.3 || dig > 10.7)
                        continue;
                    str.add(String.valueOf(dig));
                    break;
                case 5:
                    str.add(String.valueOf(arr[random.nextInt(6)]));
                    break;
                case 6:
                    str.add(String.valueOf(1*Math.pow(10,-random.nextInt(16))));
                    break;

            }
        return String.join(",",str.toString());
    }
}