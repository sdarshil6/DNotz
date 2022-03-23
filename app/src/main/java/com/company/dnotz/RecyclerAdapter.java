package com.company.dnotz;

import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.dataItemsHolder> {

    Context context;
    ArrayList<String> noteTitleList;
    ArrayList<String> noteDescriptionList;
    ArrayList<Integer> primaryIdList;
    ArrayList<Integer> flagList;





    public RecyclerAdapter(Context context, ArrayList<String> noteTitleList, ArrayList<String> noteDescriptionList, ArrayList<Integer> primaryIdList, ArrayList<Integer> flagList) {

        this.context = context;
        this.noteTitleList = noteTitleList;
        this.noteDescriptionList = noteDescriptionList;
        this.primaryIdList = primaryIdList;
        this.flagList = flagList;

    }

    @NonNull
    @Override
    public dataItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design, parent, false);
        return new dataItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dataItemsHolder holder, int position) {

        int pos = noteTitleList.size() - position - 1;
        holder.tv_displayNoteTitle.setText(noteTitleList.get(pos));
        holder.tv_displayNoteDescription.setText(noteDescriptionList.get(pos));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*int primaryIdValue = primaryIdList.get(holder.getAdapterPosition());
                new PassDataToDatabase(context).deleteNote(primaryIdValue);*/

                MainActivity.getmInstanceActivity().visibilityStatus1();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                MakeNoteFragment makeNoteFragment = new MakeNoteFragment(context, noteTitleList.get(pos),
                        noteDescriptionList.get(pos),
                        primaryIdList.get(pos), flagList.get(pos));

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, makeNoteFragment).commit();

            }
        });


    }

    @Override
    public int getItemCount() {

        return noteTitleList.size();

    }


    public static class dataItemsHolder extends RecyclerView.ViewHolder {

        private TextView tv_displayNoteTitle;
        private TextView tv_displayNoteDescription;
        private CardView cardView;

        public dataItemsHolder(@NonNull View itemView) {
            super(itemView);
            tv_displayNoteTitle = itemView.findViewById(R.id.tv_displayNoteTitle);
            tv_displayNoteDescription = itemView.findViewById(R.id.tv_displayNoteDescription);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
