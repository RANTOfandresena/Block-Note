package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.EditNoteActivity;
import com.example.myapplication.Model.ListNote;
import com.example.myapplication.R;
import com.example.myapplication.database.BaseDeDonne;

import java.util.List;

public class ListNoteAdapteur extends BaseAdapter {
    private Context context;
    private List<ListNote> listNoteList;
    private LayoutInflater layoutInflater;
    private View.OnClickListener itemClick;
    private Activity activity;

    public ListNoteAdapteur(Context context, List<ListNote> listNoteList, Activity activity){
        this.context=context;
        this.listNoteList=listNoteList;
        this.layoutInflater=LayoutInflater.from(context);
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return listNoteList.size();
    }

    @Override
    public ListNote getItem(int position) {
        return listNoteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.list_note,null);
        ListNote listNote = getItem(position);
        String id=listNote.getId();
        String titre=listNote.getTitre();
        String note=listNote.getNote();
        String date=listNote.getDate();

        TextView textTitre=convertView.findViewById(R.id.titreId);
        textTitre.setText(titre);

        TextView textNote=convertView.findViewById(R.id.noteId);
        textNote.setText(note);

        TextView textDate=convertView.findViewById(R.id.dateId);

        textDate.setText(date);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(parent.getContext(), EditNoteActivity.class);
                intent.putExtra("id",id);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder confirmationSuppre=new AlertDialog.Builder(activity);
                confirmationSuppre.setTitle("Confirmation");
                confirmationSuppre.setMessage("Vous voulez supprimer cette note?");
                confirmationSuppre.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BaseDeDonne connBDD=new BaseDeDonne(activity);
                        connBDD.deleteById(id);
                        Intent intent=new Intent(parent.getContext(),HomeActivity.class);
                        activity.startActivity(intent);
                        Toast.makeText(parent.getContext(),"Suppression reussi",Toast.LENGTH_SHORT).show();
                        confirmationSuppre.show();
                        activity.finish();
                    }
                });
                confirmationSuppre.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(parent.getContext(),"Suppression annuler",Toast.LENGTH_SHORT).show();
                    }
                });
                confirmationSuppre.show();
                return false;
            }
        });

        return convertView;
    }
}
