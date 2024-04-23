package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.ListNote;
import com.example.myapplication.database.BaseDeDonne;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private BaseDeDonne connBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.connBDD=new BaseDeDonne(this);
        List<ListNote> listNoteList=connBDD.findAll();//recuperation des donne dans la base de donne

        ListView listView= findViewById(R.id.note);
        ListNoteAdapteur listNote= new ListNoteAdapteur(this,listNoteList,this);
        listView.setAdapter(listNote);
    }
    public void ajoutNote(View view){
        Intent intent= new Intent(getApplicationContext(), EditNoteActivity.class);
        String id=connBDD.ajouterNote();
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
    }
}