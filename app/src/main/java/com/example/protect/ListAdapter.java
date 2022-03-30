package com.example.protect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Lesson> {

    Context context;
    int mRes;
    ArrayList<Lesson> obj;
    String fileName;

    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Lesson> objects, String fileName) {
        super(context, resource, objects);
        this.context = context;
        this.mRes = resource;
        this.fileName = fileName;
        this.obj = objects;
    }

    @Nullable
    @Override
    public Lesson getItem(int position) {
        return obj.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getCount(){
        return obj.size();
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        Integer page = getItem(position).getPage();

        ViewHolder mainview = null;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(mRes,parent,false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView4);
            viewHolder.textView.setText(name);
            viewHolder.button = (Button) convertView.findViewById(R.id.button2);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AllTheory.class);
                    intent.putExtra("page",page);
                    intent.putExtra("pdfFile",fileName);

                    context.startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        }else{
            mainview = (ViewHolder) convertView.getTag();
            mainview.textView.setText(name);

        }

        return convertView;
    }
    public class ViewHolder{
        TextView textView;
        Button button;
    }
}
