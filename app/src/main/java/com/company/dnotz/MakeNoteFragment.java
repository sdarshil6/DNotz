package com.company.dnotz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;


public class MakeNoteFragment extends Fragment {


    Context context;
    int primaryId;
    int flag;
    String noteTitle;
    String noteDescription;
    EditText et_addTitle;
    EditText et_addDescription;
    Button bt_saveNote;
    Button bt_deleteNote;
    Boolean b = false;

    public static final String NOTE_TITLE_DATA = "NOTE_TITLE_DATA";
    public static final String NOTE_DESCRIPTION_DATA = "NOTE_DESCRIPTION_DATA";


    public MakeNoteFragment() {
        // Required empty public constructor
    }

    public MakeNoteFragment(Context context, String noteTitle, String noteDescription, int primaryId, int flag){
        this.context = context;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.primaryId = primaryId;
        this.flag = flag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_make_note, container, false);

        et_addTitle = view.findViewById(R.id.et_addTitle);
        et_addDescription = view.findViewById(R.id.et_addDescription);
        bt_saveNote = view.findViewById(R.id.bt_saveNote);
        bt_deleteNote = view.findViewById(R.id.bt_deleteNote);



        if(noteTitle != null && noteDescription != null){

            et_addTitle.setText(noteTitle);
            et_addDescription.setText(noteDescription);

        }

        else if(noteTitle != null && noteDescription == null){

            et_addTitle.setText(noteTitle);
            et_addDescription.setText("");

        }

        else if(noteTitle == null && noteDescription != null){

            et_addTitle.setText("");
            et_addDescription.setText(noteDescription);

        }

        else{

            et_addTitle.setText("");
            et_addDescription.setText("");

        }


        bt_saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataItems dataItems;
                if(et_addTitle.getText().toString() != null && et_addDescription.getText().toString() != null) {
                    dataItems = new DataItems(et_addTitle.getText().toString(), et_addDescription.getText().toString());
                }
                else if(et_addTitle.getText().toString() != null && et_addDescription.getText().toString() == null){
                    dataItems = new DataItems(et_addTitle.getText().toString(), "");
                }
                else if(et_addTitle.getText().toString() == null && et_addDescription.getText().toString() != null){
                    dataItems = new DataItems("", et_addDescription.getText().toString());
                }
                else{
                    dataItems = new DataItems("", "");
                }

                PassDataToDatabase passDataToDatabase = new PassDataToDatabase(getActivity());
                if(flag == 1){

                    boolean b = passDataToDatabase.updateNote(dataItems.getNote_title(), dataItems.getNote_description(), primaryId);
                    if(b){

                        Toast.makeText(getActivity(), "Note Successfully Updated", Toast.LENGTH_LONG).show();

                    }
                    else{

                        Toast.makeText(getActivity(), "Note not updated", Toast.LENGTH_LONG).show();

                    }

                }
                else {

                    boolean b = passDataToDatabase.addNote(dataItems);
                    if (b) {

                        Toast.makeText(getActivity(), "Note Successfully Added", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(getActivity(), "Note not Added", Toast.LENGTH_LONG).show();

                    }

                }

                accessingOnBackPressMethodOfMainActivity();

            }
        });

        bt_deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(primaryId != 0){

                    PassDataToDatabase passDataToDatabase = new PassDataToDatabase(getActivity());
                    passDataToDatabase.deleteNote(primaryId);
                    accessingOnBackPressMethodOfMainActivity();

                }

                else{

                    accessingOnBackPressMethodOfMainActivity();

                }

            }
        });

        return view;
    }



    public void accessingOnBackPressMethodOfMainActivity(){

        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;

        mainActivity.onBackPressed();
        mainActivity.showAllNotes();

    }


}