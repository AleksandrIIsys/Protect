package com.example.protect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public class lab5_activity extends AppCompatActivity {

    static char[][] squ = new char[5][5];
    //3. Решить проблему с List.of (заменила на Arrays.asList)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab5);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText textForCrypt = (EditText) findViewById(R.id.textForCrypt);
        EditText wordColumn = (EditText) findViewById(R.id.codeWordColumn);
        EditText wordString = (EditText) findViewById(R.id.codeWordString);
        TextView result = (TextView) findViewById(R.id.result);

        Toast toastCopy = Toast.makeText(this,"Скопировано в буфер обмена",Toast.LENGTH_SHORT);
        Toast error = Toast.makeText(this, "Не получается выполнить данное действие", Toast.LENGTH_LONG);
        Toast fewCodeWords = Toast.makeText(this, "Введите больше символов для перестановки строк и столбцов", Toast.LENGTH_SHORT);

        result.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("",result.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });

        Button encryptTable = (Button) findViewById(R.id.encrypt);
        Button decryptTable = (Button) findViewById(R.id.decrypt);

        encryptTable.setOnClickListener(view ->{
            try{
                if(String.valueOf(wordColumn.getText()).length() * String.valueOf(wordString.getText()).length() < String.valueOf(textForCrypt.getText()).replace(" ", "").length() ){
                    throw new Exception();
                }
            } catch (Exception e) {
                fewCodeWords.show();
            }
            try {
                result.setText(EnCryptCodeTable(String.valueOf(wordColumn.getText()), String.valueOf(wordString.getText()), String.valueOf(textForCrypt.getText())));
            }
            catch(Exception e){
                error.show();
            }
        });

        decryptTable.setOnClickListener(view ->{
            try {
                result.setText(DeCryptCodeTable(String.valueOf(wordColumn.getText()), String.valueOf(wordString.getText()), String.valueOf(textForCrypt.getText())));
            } catch (Exception e){
                error.show();
            }
        });

        EditText textForPolibiy = (EditText) findViewById(R.id.textForPolibiy);
        EditText wordPolibiy = (EditText) findViewById(R.id.codeWordForPolibiy);
        Switch sdvig = (Switch) findViewById(R.id.sdvigPolibiy);
        Button encryptPolibiy = (Button) findViewById(R.id.EncryptPolibiy);
        Button decryptPolibiy = (Button) findViewById(R.id.DecryptPolibiy);
        TextView resPolibiy = (TextView) findViewById(R.id.resPolibiy);

        encryptPolibiy.setOnClickListener(view->{
            try {
                String res = Polibiy(wordPolibiy.getText().toString(), textForPolibiy.getText().toString(), sdvig.isChecked());
                if(res.length() == 0) throw new Exception();
                resPolibiy.setText(res);
            }catch (Exception e){
                error.show();
            }
        });

        decryptPolibiy.setOnClickListener(view->{
            try {
                resPolibiy.setText(DeCryptPolibiy(textForPolibiy.getText().toString(), sdvig.isChecked()));
            }catch (Exception e){
                error.show();
            }
        });

        resPolibiy.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", resPolibiy.getText().toString());
            clipboardManager.setPrimaryClip(clip);
            toastCopy.show();
        });
    }
    public static String ConvertReplace(String str){
        return str.toUpperCase()
                .replace("Э","Е")
                .replace("Й","И")
                .replace("С","Р")
                .replace("Х","Ф")
                .replace("Щ","Ш")
                .replace("J","I")
                .replace("Ъ","")
                .replace("Ь","")
                .replace("Ё","");
    }

    public static int isValid(String str) {
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

    public static String BuildString(String str, String str2){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < str.length();i++){
            if(stringBuilder.indexOf(String.valueOf(str.charAt(i))) == -1)
                stringBuilder.append(str.charAt(i));
        }
        int k = isValid(str);
        if(stringBuilder.length() == 0)
            k = isValid(str2);
        switch (k){
            case 1:
                for(int i = 0;i < 26; i++){
                    if('A'+ i == 'J' || stringBuilder.indexOf(String.valueOf(Character.toChars('A'+ i))) != -1)
                        continue;
                    stringBuilder.append(Character.toChars('A'+i));
                }
                break;
            case 2:
                for(int i = 0;i < 32; i++){
                    if('А'+ i == 'Ъ' || 'А'+ i == 'Ь' || 'А'+ i == 'Э' || 'А'+ i == 'Й'|| 'А'+ i == 'Щ'|| 'А'+ i == 'С'|| 'А'+ i == 'Х'|| 'А'+ i == 'Ё' || stringBuilder.indexOf(String.valueOf(Character.toChars('А'+ i))) != -1)
                        continue;
                    stringBuilder.append(Character.toChars('А'+i));
                }
                break;
            default: break;
        }
        return stringBuilder.toString();
    }

    public static String CryptPolibiy(String str,boolean sdvig){
        StringBuilder part1 = new StringBuilder(),part2 = new StringBuilder();
        for(int i = 0;i < str.length();i++){
            for(int j = 0; j < 5;j++){
                for(int k = 0; k  <5;k++){
                    if(squ[j][k] == str.charAt(i))
                    {
                        part1.append(k);
                        part2.append(j);
                    }
                }
            }
        }
        StringBuilder itog = part1.append(part2);
        if(sdvig)
            itog.append(itog.charAt(0)).delete(0,1);
        for(int i = 2;i <itog.length();i+=3){
            itog.insert(i," ");
        }
        StringBuilder newS = new StringBuilder();

        for(int i = 0;i < itog.toString().split(" ").length;i++){
            newS.append(squ[Integer.parseInt(String.valueOf(itog.toString().split(" ")[i].charAt(1)))][Integer.parseInt(String.valueOf(itog.toString().split(" ")[i].charAt(0)))]);
        }
        return newS.toString();
    }
    public static String DeCryptPolibiy(String str,boolean sdvig){
        str = str.toUpperCase();
        StringBuilder part1 = new StringBuilder();
        StringBuilder part2 = new StringBuilder();
        for(int i = 0;i < str.length();i++){
            for(int j = 0; j < 5;j++){
                for(int k = 0; k  <5;k++){
                    if(squ[j][k] == str.charAt(i))
                    {
                        if(i+0.5 == str.length()/2.)
                        {
                            part1.append(k);
                            part2.append(j);
                            continue;
                        }
                        if(i < str.length()/2) {
                            part1.append(k);
                            part1.append(j);
                        }else {
                            part2.append(k);
                            part2.append(j);
                        }
                    }
                }
            }
        }
        if(sdvig) {
            part1.insert(0,part2.charAt(part2.length()-1));
            part2.insert(0,part1.charAt(part1.length()-1));
            part2.delete(part2.length()-1,part2.length());
            part1.delete(part1.length()-1,part1.length());
        }
        StringBuilder newS = new StringBuilder();
        for(int i = 0;i < part1.length();i++){
            newS.append(squ[Integer.parseInt(String.valueOf(part2.toString().charAt(i)))][Integer.parseInt(String.valueOf(part1.toString().charAt(i)))]);
        }
        return newS.toString();
    }
    public static String Polibiy(String codeWord, String textForCrypting, boolean sdvig){
        codeWord = ConvertReplace(codeWord);
        textForCrypting = ConvertReplace(textForCrypting);
        StringBuilder stringBuilder = new StringBuilder(BuildString(codeWord,textForCrypting));
        if(stringBuilder.length() == 0) return "";
        int l = 0;
        for(int i = 0 ;i  <5;i++){
            for(int j = 0; j <5;j++){
                squ[i][j] = stringBuilder.charAt(l);
                l++;
            }
        }
        StringBuilder newS = new StringBuilder(CryptPolibiy(textForCrypting,sdvig));
        return newS.toString();
    }


    public static String EnCryptCodeTable(String wordColumn, String wordString, String textForCrypt){
        ArrayList<String> newWordS = DeleteDoubleLetters(wordString);;
        ArrayList<String> newWordC = DeleteDoubleLetters(wordColumn);
        textForCrypt = textForCrypt.replace(" ","");
        int k = 0;
        ArrayList<ArrayList<String>> arr = new ArrayList<>();
        arr.add(new ArrayList<>());
        arr.get(0).add(".");
        for(int i = 0; i < wordColumn.length();i++){
            arr.get(0).add(String.valueOf(newWordC.get(i)));
        }
        for(int i = 0; i <wordString.length();i++){
            arr.add(new ArrayList<>());
            arr.get(i+1).add(String.valueOf(newWordS.get(i)));
        }
        for(int i = 1; i < arr.size();i++){
            for(int j = 1; j < arr.get(0).size();j++){
                if(k != textForCrypt.length()) {
                    arr.get(i).add(String.valueOf(textForCrypt.charAt(k)));
                    k++;
                }
                else
                    arr.get(i).add("#");
            }
        }
        ArrayList<String> temp;
        for(int i = 1;i <  arr.size();i++){
            for(int j = 1;j < arr.size();j++){
                if(arr.get(i).get(0).charAt(0) < arr.get(j).get(0).charAt(0)){
                    temp = arr.get(i);
                    arr.set(i,arr.get(j));
                    arr.set(j,temp);
                }
            }
        }

        Collections.sort(newWordC);
        ArrayList<String> fin = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for(int j = 1; j < arr.get(0).size();j++){
            for(int i = 1; i < arr.size();i++) {
                stringBuilder.append(arr.get(i).get(arr.get(0).indexOf(newWordC.get(j-1))));
                if(stringBuilder.length()%5 == 0){
                    fin.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            }
        }
        fin.add(stringBuilder.toString());
        StringBuilder stringBuilder1 = new StringBuilder();
        for(int i = 0;i < fin.size();i++)
            stringBuilder1.append(fin.get(i)).append(" ");
        return stringBuilder1.toString();
    }


    public static String DeCryptCodeTable(String wordColumn, String wordString, String crypt){
        //wordColumn = DeleteDoubleLetters(wordColumn);
        //wordString = DeleteDoubleLetters(wordString);
        ArrayList<String> newWordS = DeleteDoubleLetters(wordString);
        ArrayList<String> newWordC = DeleteDoubleLetters(wordColumn);
        crypt = crypt.replace(" ","");
        StringBuilder sort = new StringBuilder(wordColumn);
        ArrayList<ArrayList<String>> arr = new ArrayList<>();
        arr.add(new ArrayList<>());
        arr.get(0).add(".");
        //ArrayList<String> temp = new ArrayList<>(Arrays.asList((wordColumn.split(""))));
        Collections.sort(newWordC);
        for(int i = 0; i < wordColumn.length();i++){
            arr.get(0).add(String.valueOf(newWordC.get(i)));
        }
        //temp = new ArrayList<>(Arrays.asList(wordString.split("")));
        String temp;
        for(int i = 0;i <  newWordS.size();i++){
            for(int j = 0;j < newWordS.size();j++){
                if(newWordS.get(i).charAt(0) < newWordS.get(j).charAt(0)){
                    temp = newWordS.get(i);
                    newWordS.set(i,newWordS.get(j));
                    newWordS.set(j,temp);
                }
            }
        }
        for(int i = 0; i < wordString.length();i++){
            arr.add(new ArrayList<>());
            arr.get(i+1).add(String.valueOf(newWordS.get(i)));
        }
        int k =0;
        for(int j = 1; j < arr.get(0).size();j++){
            for(int i = 1; i < arr.size();i++){
                arr.get(i).add(String.valueOf(crypt.charAt(k)));
                k++;
            }
        }
        newWordS = DeleteDoubleLetters(wordString);
        newWordC = DeleteDoubleLetters(wordColumn);
        StringBuilder stringBuilder =new StringBuilder();
        for(int i =0;i < newWordS.size();i++){
            for(int j =0; j < newWordC.size();j++){
                for(int l = 1;l < arr.size();l++){
                    if(newWordS.get(i).equals(arr.get(l).get(0))){
                        stringBuilder.append(arr.get(l).get(arr.get(0).indexOf(String.valueOf(newWordC.get(j)))));
                        break;
                    }

                }
            }
        }
        return stringBuilder.toString().replace("#","");
    }
    public static int CountChar(String str, char s){
        int count = 0;
        for (int  i  = 0; i < str.length();i++) {
            if(str.charAt(i) == s)
                count++;

        }
        return count;
    }
    public static ArrayList<String> DeleteDoubleLetters(String str){
        ArrayList<String> newstr = new ArrayList<String>();
            for (int i = 0; i < str.length(); i++) {
                //newstr.append(Character.toChars(str.charAt(i) + CountChar(new StringBuilder(str), str.charAt(i))-1));
                if(CountChar(str,str.charAt(i)) > 1)
                    newstr.add(String.valueOf(str.charAt(i))+String.valueOf(Character.toChars(-CountChar(newstr.toString(),str.charAt(i))+'z')));
                else
                    newstr.add(String.valueOf(str.charAt(i)));
            }
        return newstr;
    }
}