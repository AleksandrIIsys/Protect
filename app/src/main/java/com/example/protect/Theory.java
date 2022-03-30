package com.example.protect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Theory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Theory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Theory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Theory.
     */
    // TODO: Rename and change types and number of parameters
    public static Theory newInstance(String param1, String param2) {
        Theory fragment = new Theory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ListView listView = view.findViewById(R.id.listLesson);
        ArrayList<Lesson> lessons = new ArrayList<>();
        for(int i = 0; i < name.size();i++){
            lessons.add(new Lesson(name.get(i).replace(" ","").replace("\t",""),pages.get(i)));
        }
        //listView.setAdapter(new ListAdapter(getContext(),R.layout.list_simple_button,lessons));
        listView.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,Arrays.asList("dffd","fadsf")));
    }
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theory, container, false);
        return inflater.inflate(R.layout.fragment_theory, container, false);
    }
}