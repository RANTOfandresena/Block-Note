package com.example.myapplication.Model;

public class ListNote {
    private String id;
    private String titre;
    private String note;
    private String date;

    public ListNote(String id, String titre, String note, String date)  {
        this.id=id;
        this.titre=titre;
        this.note=note;
        this.date=date;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
