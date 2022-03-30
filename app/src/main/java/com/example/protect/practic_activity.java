package com.example.protect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class practic_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practic_theory);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ListView listView = findViewById(R.id.listLesson);
        ArrayList<Lesson> lessons = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.labs)));
        ArrayList<Integer> pages = new ArrayList<>(Arrays.asList(3,10,13,18,22,28,33,42,47,53,56));
        for(int i = 0; i < name.size();i++){
            lessons.add(new Lesson(name.get(i).replace("\t",""),pages.get(i)));
        }
        listView.setAdapter(new ListAdapter(this,R.layout.list_simple_button,lessons, "PracticPdf.pdf"));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}