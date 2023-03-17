package com.example.svyrydenkonure4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    private ArrayAdapter<Note> adapter;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView image;
    EditText titleInput;
    EditText descriptionInput;
    Spinner sp1;
    MaterialButton addPic;
    MaterialButton saveNote;
    MaterialButton getBack;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addPic = (MaterialButton)findViewById(R.id.addpic);;
        saveNote = (MaterialButton)findViewById(R.id.save);
        image = (ImageView)findViewById(R.id.pic);
        titleInput = (EditText)findViewById(R.id.titleinput);
        descriptionInput = (EditText)findViewById(R.id.descriptioninput);
        sp1 = (Spinner) findViewById(R.id.importance);
        getBack = (MaterialButton)findViewById(R.id.back);

        notes = JSONHelper.importFromJSON(getApplicationContext());

        getBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNoteActivity.this,MainActivity.class));
            }
        });

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            String title = arguments.get("title").toString();
            String description = arguments.getString("description");
            String importance = arguments.getString("importance");
            long time = arguments.getLong("time");
            String uri = arguments.getString("uri");
            imageUri = Uri.parse(arguments.getString("uri"));
            int id = arguments.getInt("id");
            Note note = new Note(title, description, time, importance, uri);
            image.setImageURI(Uri.parse(uri));
            titleInput.setText(title);
            descriptionInput.setText(description);
            notes.remove(id);
            JSONHelper.exportToJSON(this, notes);

        }

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote(view);
                adapter.notifyDataSetChanged();
            }
        });

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.importance,
                R.layout.spinner_list
        );
        sp1.setAdapter(arrayAdapter);
    }

    private void openGallery() {
        Intent photoPicker = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, PICK_IMAGE);
    }

    public void saveNote(View view) {
        notes = JSONHelper.importFromJSON(getApplicationContext());
        if(notes == null) notes = new ArrayList<>();
        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        String importance = sp1.getSelectedItem().toString();
        long createdTime = System.currentTimeMillis();
        String uri = imageUri.toString();

        Note note = new Note(title, description, createdTime, importance, uri);

        notes.add(note);

        boolean result = JSONHelper.exportToJSON(this, notes);
        if(result){
            Toast.makeText(this, "Дані збережено", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не вдалося зберегти дані", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
            getContentResolver()
                    .takePersistableUriPermission(
                            imageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );
        }
    }
}