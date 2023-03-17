package com.example.svyrydenkonure4;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Note> {

    private Context mContext;

    ArrayList<Note> notes;

    public MyAdapter(Context context, int textViewResourceId,
                     ArrayList<Note> notes) {
        super(context, textViewResourceId, notes);
        mContext = context;
        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.note_view, parent,
                false);
        ImageView itemImage = row.findViewById(R.id.imageoutput);
        TextView programTitle = row.findViewById(R.id.titleoutput);
        TextView programDescription = row.findViewById(R.id.descriptionoutput);
        TextView programImportance = row.findViewById(R.id.importanceoutput);
        TextView programTime = row.findViewById(R.id.timeoutput);

        String formatedTime = DateFormat.getDateTimeInstance().format(notes.get(position).
                getCreatedTime());

        Uri imageUri = Uri.parse(notes.get(position).getUri());

        itemImage.setImageURI(imageUri);
        programTitle.setText(notes.get(position).getTitle());
        programDescription.setText(notes.get(position).getDescription());
        programImportance.setText(notes.get(position).getImportance());
        programTime.setText(formatedTime);

        switch (notes.get(position).getImportance()){
            case "so important":
                programImportance.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "quite important":
                programImportance.setTextColor(Color.parseColor("#FFFF00"));
                break;
            case "unimportant":
                programImportance.setTextColor(Color.parseColor("#00FF00"));
                break;
            case "дуже важливо":
                programImportance.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "доволі важливо":
                programImportance.setTextColor(Color.parseColor("#FFFF00"));
                break;
            case "неважливо":
                programImportance.setTextColor(Color.parseColor("#00FF00"));
                break;
            default:
                break;
        }

        return row;
    }
}
