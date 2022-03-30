package com.example.protect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class lab1_activity extends AppCompatActivity {
    // Объявление глобальных переменных
    Switch RusUp;
    Switch RusLow;
    Switch EngUp;
    Switch EngLow ;
    Switch Numb;
    Switch Spec;
    TextView textView;
    EditText ForV;
    EditText ForP1;
    EditText ForP2;
    EditText ForT;
    Button butV ;
    Button butT ;
    final int RUS = 33;
    final int ENG = 26;
    final int NUM = 10;
    final int SPEC = 10;
    @Override
    // Инициализация глобальных переменных
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        RusUp = (Switch) findViewById(R.id.RusUpSwitch);
        RusLow = (Switch) findViewById(R.id.RusLowSwitch);
        EngUp = (Switch) findViewById(R.id.EngUpSwitch);
        EngLow = (Switch) findViewById(R.id.EngLowSwitch);
        Numb = (Switch) findViewById(R.id.NumbSwitch);
        Spec = (Switch) findViewById(R.id.SpecSwitch);
        ForV = (EditText) findViewById(R.id.TextForV);
        ForP1 = (EditText) findViewById(R.id.TextForP1);
        ForP2 = (EditText) findViewById(R.id.TextForP2);
        ForT = (EditText) findViewById(R.id.TextForT);
        textView = (TextView) findViewById(R.id.password);
        butV = (Button) findViewById(R.id.buttonV);
        butT = (Button) findViewById(R.id.buttonT);

        Toast toastCopy = Toast.makeText(this,"Скопировано в буфер обмена",Toast.LENGTH_SHORT);
        EditText editText = (EditText) findViewById(R.id.EnterTextForPassword);
        TextView textview14 = (TextView) findViewById(R.id.LR1Variant11) ;
        TextView textview11 = (TextView) findViewById(R.id.LR1Variant14) ;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",textView.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                toastCopy.show();
            }
        });

        textview14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",textview14.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                toastCopy.show();
            }
        });

        textview11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("",textview11.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                toastCopy.show();
            }
        });
    }
    //Объявление переменных для алфавитов символов
    int RusUpS,RusLowS,EngUpS,EngLowS,NumbS,SpecS;
    //Смена единиц измерения при нажатии на кнопку
    public void ButT(View view){
        switch (butT.getText().toString()){
            case "день":
                butT.setText("неделя");
                break;
            case "неделя":
                butT.setText("месяц");
                break;
            case "месяц":
                butT.setText("день");
        }
    }
    //Смена единиц измерения при нажатии на кнопку
    public void ButV(View view){
        switch (butV.getText().toString()){
            case "день":
                butV.setText("мин");
                break;
            case "мин":
                butV.setText("день");
        }
    }
    //Сумма количества всех используемых символов из предоставленных алфавитов
    public int BuildRequier(){
        return  RusUpS + RusLowS + EngUpS + EngLowS + NumbS + SpecS;
    }
    //Проверка на выбор используемых алфавитов
    public void Task1(View view){
        if(RusUp.isChecked())
            RusUpS = RUS;
        else
            RusUpS = 0;
        if(RusLow.isChecked())
            RusLowS = RUS;
        else
            RusLowS = 0;
        if(EngUp.isChecked())
            EngUpS = ENG;
        else
            EngUpS = 0;
        if(EngLow.isChecked())
            EngLowS = ENG;
        else
            EngLowS = 0;
        if(Numb.isChecked())
            NumbS = NUM;
        else
            NumbS = 0;
        if(Spec.isChecked())
            SpecS = SPEC;
        else
            SpecS = 0;
        if(BuildRequier() == 0){

            return;
        }
        PassowordPower(BuildRequier());
    }
    //Генерирование пароля и его вывод
    public void PassowordPower(int a){
        double V,T,P;
        V = Double.valueOf(ForV.getText().toString()) ;
        if(butV.getText() == "мин")
            V*=24*60;
        T = Double.valueOf(ForT.getText().toString()) ;
        switch (butT.getText().toString()){
            case "неделя":
                T*=7;
                break;
            case "месяц":
                T*=30;
                break;
        }
        P = Math.pow(Integer.valueOf(ForP1.getText().toString()),Integer.valueOf(ForP2.getText().toString()));
        double S;
        S = Math.ceil(V*T/P);
        int L = 1;
        while(S > Math.pow(a,L)){
            L++;
        }
        String pass = "";
        int choose;
        while(pass.length() != L){
            choose = (int)((Math.random()*(7-1)) + 1);
            switch(choose){
                case 1:
                    if(RusLowS != 0)
                        pass += (char)((Math.random()*(1103-1072))+1072);
                    break;
                case 2:
                    if(RusUpS != 0)
                        pass += (char)((Math.random()*(1071-1040))+1040);
                    break;
                case 3:
                    if(EngLowS != 0)
                        pass += (char)((Math.random()*(122-97))+97);
                    break;
                case 4:
                    if(EngUpS != 0)
                        pass += (char)((Math.random()*(90-65))+65);
                    break;
                case 5:
                    if(NumbS != 0)
                        pass += String.valueOf((int)((Math.random()*9)));
                    break;
                case 6:
                    if(SpecS != 0)
                        pass += (char)((Math.random()*(42-33))+33);
                    break;
            }
        }
        textView.setText(pass);
    }



    //Вывод двух паролей при нажатии одной кнопки
    public void GeneratorRandomPassword(View view){
        GeneratorRandomPassword11(view);
        GeneratorRandomPassword14(view);
    }
    //Генерация пароля для 14 варианта
    public void GeneratorRandomPassword14(View view){
        EditText editText = (EditText) findViewById(R.id.EnterTextForPassword);
        TextView textview14 = (TextView) findViewById(R.id.LR1Variant14) ;
        String text = editText.getText().toString();
        Random rand = new Random();
        int N = text.length();
        int M = 6;
        String password = "";
        int b1 = rand.nextInt(32) + 1040;
        int b2 = rand.nextInt(32) + 1040;
        int b3 = N*N % 10;
        int b4 = rand.nextInt(10);
        int b5 = rand.nextInt(10) + 33;
        int b6 = rand.nextInt(32) + 1072;
        password += (char) b1;
        password += (char) b2;
        password += String.valueOf(b3);
        password += String.valueOf(b4);
        password += (char) b5;
        password += (char) b6;
        textview14.setText(password);
    }
    //Генерирование пароля для 11 варианта
    public void GeneratorRandomPassword11(View view){
        EditText editText = (EditText) findViewById(R.id.EnterTextForPassword);
        TextView textview11 = (TextView) findViewById(R.id.LR1Variant11) ;
        String text = editText.getText().toString();
        Random rand = new Random();
        int N = text.length();
        int M = 9;
        int Q = N % 5;
        String password = "";
        for(int i = 1; i <= Q + 1; i++){
            int b = rand.nextInt(10) + 33;
            password += (char) b;
        }
        for(int i = Q + 2; i < 9; i++){
            int b = rand.nextInt(32) + 1072;
            password += (char) b;
        }
        password += rand.nextInt(10);
        textview11.setText(password);
    }
}