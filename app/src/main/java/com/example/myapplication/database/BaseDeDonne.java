package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Model.ListNote;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDonne extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "blocknote";
    private static  final int DATABASE_VERSION=1;
    public BaseDeDonne(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE lists (id INTEGER PRIMARY KEY AUTOINCREMENT,titre TEXT,note TEXT,datee TIMESTAMP)";
        db.execSQL(sql);
        //db.execSQL("INSERT INTO lists (titre,note,datee) VALUES ('test note','note note note note...',CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lists");
        onCreate(db);
    }

    public String ajouterNote(){
        String id="1";
        SQLiteDatabase db = getWritableDatabase();
        //ContentValues data=new ContentValues();
        db.execSQL("INSERT INTO lists (titre,note,datee) VALUES ('','',CURRENT_TIMESTAMP)");
        Cursor cursor = db.rawQuery("select MAX(id) FROM lists;", null);
        if (cursor.moveToFirst()) {
            id=cursor.getString(0);
            int idInt=Integer.parseInt(id);
            idInt+=1;
            //id=String.valueOf(idInt);
        }
        return id;
    }
    public List<ListNote> findAll() {
        List<ListNote> listeDonnees = new ArrayList<>();
        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lists ORDER BY datee DESC;", null);
        ListNote notyMod=null;

        if (cursor.moveToFirst()) {
            do {
                String id =cursor.getString(0);
                String titre=cursor.getString(1);
                String note=cursor.getString(2);
                String date=cursor.getString(3);
                notyMod=new ListNote(id,titre,note,date);
                listeDonnees.add(notyMod); // 1 est l'index de la colonne "nom"
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listeDonnees;
    }
    public ListNote findById(String id){
        ListNote noteM=null;
        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lists WHERE id="+id+";", null);
        if (cursor.moveToFirst()) {
            String idd =cursor.getString(0);
            String titre=cursor.getString(1);
            String note=cursor.getString(2);
            String date=cursor.getString(3);
            noteM=new ListNote(idd,titre,note,date);
        }
        cursor.close();
        db.close();
        return noteM;
    }
    public void upDateById(ListNote noteM){
        SQLiteDatabase db =getReadableDatabase();
        db.execSQL("UPDATE lists SET titre='"+noteM.getTitre()+"' , note='"+noteM.getNote()+"',datee=CURRENT_TIMESTAMP WHERE id="+noteM.getId()+";");
        db.close();
    }
    public void deleteById(String id){
        SQLiteDatabase db =getReadableDatabase();
        db.execSQL("DELETE FROM lists WHERE id="+id+";");
        db.close();
    }
}
