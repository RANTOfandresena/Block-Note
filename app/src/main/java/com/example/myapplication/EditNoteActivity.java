package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Model.ListNote;
import com.example.myapplication.database.BaseDeDonne;

public class EditNoteActivity extends AppCompatActivity {
    private BaseDeDonne connBDD;
    private ListNote noteM;
    private EditText baliseTitre;
    private EditText baliseNote;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        this.connBDD=new BaseDeDonne(this);//connexion bdd

        this.id=getIntent().getStringExtra("id");//recuper l'id du note cliqué sur l'activity Home
        this.noteM=connBDD.findById(id);//recureper les donnes
        // selection des balises sur xml
        this.baliseTitre=findViewById(R.id.titre_v);
        this.baliseNote=findViewById(R.id.note_v);
        //
        baliseTitre.setText(noteM.getTitre());
        baliseNote.setText(noteM.getNote());

        EditNoteActivity self=this;
        baliseTitre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                self.noteM.setTitre(String.valueOf(self.baliseTitre.getText()));
                self.connBDD.upDateById(self.noteM);
            }
        });
        baliseNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                self.noteM.setNote(String.valueOf(self.baliseNote.getText()));
                self.connBDD.upDateById(self.noteM);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void suppremer(View view){
        AlertDialog.Builder confirmationSuppre=new AlertDialog.Builder(this);
        confirmationSuppre.setTitle("Confirmation");
        confirmationSuppre.setMessage("Vous voulez supprimer cette note?");
        confirmationSuppre.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                connBDD.deleteById(id);
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        confirmationSuppre.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Suppression annuler",Toast.LENGTH_SHORT).show();
            }
        });
        confirmationSuppre.show();
    }
}