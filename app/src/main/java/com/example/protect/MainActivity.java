package com.example.protect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm,getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("Lab"));
        tabLayout.addTab(tabLayout.newTab().setText("Theory"));
        Object array = getResources().getStringArray(R.array.labs);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }
    ArrayList<String> name = new ArrayList<>(Arrays.asList("Введение. Информационная безопасность, актуальность ее обеспечения																																																																			",
            "Угрозы информационной безопасности, их классификация. Основные методы реализации угроз, этапы осуществления атаки на информационную систему																														",
            "Средства и методы обеспечения целостности информации.Средства и методы обеспечения конфиденциальности информации																																												",
            "Законодательный уровень информационной безопасности. Подсистема организационно-правовой защиты. Защита программного обеспечения авторским правом																												",
            "Стандарт ISO/IEC 15408 «Критерии оценки безопасности информационных технологий». Критерии оценки надёжных компьютерных систем																																					",
            "Задачи идентификации, аутентификации, авторизации, методы их реализации. Методы биометрической аутентификации пользователей																																						",
            "Общие подходы к построению парольных систем и основные угрозы их безопасности																																																													",
            "Основные модели криптосистем. Требования к криптосистемам. Назначение и основные функции криптосистем																																																	",
            "Классические методы шифрования. Шифрование методами перестановки: простая перестановка, одиночная перестановка по ключу, двойная перестановка, магический квадрат,шифр Кардано, шифр Ришелье						",
            "Шифрование методами замены: полибианский квадрат, шифр Цезаря, шифр Цезаря с ключевым словом, аффинная система подстановок Цезаря, диск Альберти, шифр Гронсфельда, шифр Виженера, одноразовый блокнот	",
            "Понятие о генераторах псевдослучайной последовательности. Алгоритмы генерации																																																													",
            "Шифрование методом гаммирования. Потоковые шифры																																																																												",
            "Общие принципы построения современных симметричных криптосистем. Общая характеристика блочных шифров. Криптоалгоритм DES. Криптоалгоритм ГОСТ 28147-89																									",
            "Общие принципы построения современных асимметричных криптосистем. Асимметричные криптоалгоритмы RSA и Рабина																																														",
            "Функции хеширования и целостность данных. Криптографические функции хеширования. Хеш-функции на основе симметричных блочных алгоритмов																																	",
            "Обобщенная модель электронной цифровой подписи																																																																													",
            "Угрозы безопасности ПО. Программные закладки. Троянские программы. Клавишные шпионы																																																										",
            "Классификация компьютерных вирусов. Диагностика заражения компьютерным вирусом. Основы функционирования антивирусного ПО																																								",
            "Технологическая и эксплуатационная безопасность ПО. Классификация систем защиты ПО. Системы защиты от несанкционированного копирования и изменения																											",
            "Основные принципы обеспечения безопасности ПО на различных стадиях его жизненного цикла																																																								",
            "Понятие о политике безопасности: анализ риска; угрозы/видимость; уязвимость/последствия; учет информационных ценностей																																									",
            "Модель матрицы доступов Харрисона-Рузо-Ульмана. Модель системы безопасности Белла-ЛаПуды																																																								",
            "Назначение состав и архитектура информационно-справочных систем. Структура и состав подсистемы защиты информации. Методы и средства защиты информации в СУБД																						",
            "Назначение, состав и архитектура сложных корпоративных информационных систем. Угрозы информации, которые характерны им																																									",
            "Типовые удалённые атаки в Интернет и механизмы их реализации. Типовые уязвимости, позволяющие организовать удаленные атаки																																							",
            "Обеспечение безопасности систем, входящих в состав глобальных сетей: межсетевые экраны, виртуальные частные сети																																												",
            "Обеспечение безопасности электронной почты																																																																															",
            "Назначение, состав и архитектура систем электронного документа оборота. Угрозы информации, характерные для них																																													"

    ));
    ArrayList<Integer> pages = new ArrayList<>(Arrays.asList(5,13,19,23,30,35,42,49,56,60,63,65,69,74,76,81,86,92,97,103,107,111,135,140,152,156,164,170));

    public void Lab1_launch(View view){
        Intent intent = new Intent(this, lab1_activity.class);
        startActivity(intent);
    }
    public void Lab2_launch(View view){
        Intent intent = new Intent(this, lab2_activity.class);
        startActivity(intent);
    }
    public void Lab3_launch(View view){
        Intent intent = new Intent(this, lab3_activity.class);
        startActivity(intent);
    }

    public void Lab4_launch(View view){
        Intent intent = new Intent(this, lab4_activity.class);
        startActivity(intent);
    }

    public void Lab5_launch(View view){
        Intent intent = new Intent(this, lab5_activity.class);
        startActivity(intent);
    }

    public void Lab6_launch(View view){
        Intent intent = new Intent(this, lab6_activity.class);
        startActivity(intent);
    }

    public void Lab7_launch(View view){
        Intent intent = new Intent(this, lab7_activity.class);
        startActivity(intent);
    }

    public void Lab8_launch(View view){
        Intent intent = new Intent(this, lab8_activity.class);
        startActivity(intent);
    }

    public void Lab9_launch(View view){
        Intent intent = new Intent(this, lab9_activity.class);
        startActivity(intent);
    }
    public void Lab10_launch(View view){
        Intent intent = new Intent(this, lab10_activity.class);
        startActivity(intent);
    }
    public void Lab11_launch(View view){
        Intent intent = new Intent(this, lab11_activity.class);
        startActivity(intent);
    }

    public void Practic_launch(View view){
        Intent intent = new Intent(this, practic_activity.class);
        startActivity(intent);
    }
    public void full_theory(View view){
        Intent intent = new Intent(this, AllTheory.class);
        startActivity(intent);
    }
//    public void ClickHere(View view){
//            TextView tv = (TextView) findViewById(R.id.textView2);
//
//            tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())+1));
//            Toast.makeText(this, "BB",Toast.LENGTH_LONG).show();
//    }
}