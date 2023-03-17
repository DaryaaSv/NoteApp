package com.example.svyrydenkonure4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ArrayList<Note> notes = new ArrayList<Note>();
    MaterialButton addNoteBtn;
    ListView listView;
    MyAdapter myAdapter;
    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search_bar);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.add_note:
                startActivity(new Intent(MainActivity.this,AddNoteActivity.class));
                return true;
            case R.id.sort_title:
                Collections.sort(notes, new SortByTitle());
                myAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_import:
                Collections.sort(notes, new SortByImportance());
                myAdapter.notifyDataSetChanged();
                return true;
            case R.id.search_bar:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.context_menu, menu);

    }

    private void filterList(String newText) {
        ArrayList<Note> filteredList = new ArrayList<>();
        for(Note note : notes) {
            if (note.getTitle().toLowerCase().contains(newText.toLowerCase()) ||
                    note.getDescription().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(note);
            }
        }
        if(filteredList.isEmpty()) {
            Toast.makeText(this, "Нічого не знайдено", Toast.LENGTH_LONG).show();
        }
        else {
            listView.setAdapter(null);
            myAdapter = new MyAdapter(getApplicationContext(), R.layout.note_view, filteredList);
            listView.setAdapter(myAdapter);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete:
                myAdapter.remove(notes.get(info.position));
                myAdapter.notifyDataSetChanged();
                JSONHelper.exportToJSON(this, notes);
                break;
            case R.id.edit:
                Intent intent = new Intent(this.getApplicationContext(), AddNoteActivity.class);
                intent.putExtra("title", notes.get(info.position).getTitle());
                intent.putExtra("description", notes.get(info.position).getDescription());
                intent.putExtra("importance", notes.get(info.position).getImportance());
                intent.putExtra("time", notes.get(info.position).getCreatedTime());
                intent.putExtra("uri", notes.get(info.position).getUri());
                intent.putExtra("id", info.position);
                startActivity(intent);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteBtn = (MaterialButton)findViewById(R.id.addnotebtn);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddNoteActivity.class));
            }
        });

        listView = (ListView) findViewById(R.id.list);

        open(new View(this.getApplicationContext()));

        if(notes != null) {
            myAdapter = new MyAdapter(getApplicationContext(), R.layout.note_view, notes);
            listView.setAdapter(myAdapter);
        }

        registerForContextMenu(listView);
    }

    public void open(View view) {
        notes = JSONHelper.importFromJSON(this);
        if(notes != null) {
            Toast.makeText(this, "Дані відновлено", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Не вдалося відновити дані", Toast.LENGTH_LONG).show();
        }
    }
}