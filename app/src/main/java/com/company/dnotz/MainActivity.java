package com.company.dnotz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText et_addNote;
    FrameLayout frameLayout;

    FragmentManager myFragment = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    FragmentTransaction fragmentTransaction2;
    MakeNoteFragment makeNoteFragment = new MakeNoteFragment();

    RecyclerView savedNotesView;
    RecyclerAdapter adapter;

    public static WeakReference<MainActivity> weakActivity;

    PassDataToDatabase pdtd = new PassDataToDatabase(this);

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);



        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        navigationView = findViewById(R.id.nav_view);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        setNavigationViewListener();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        weakActivity = new WeakReference<>(MainActivity.this);

        long numRows = pdtd.getRowCount();
        savedNotesView = findViewById(R.id.savedNotesView);
        showAllNotes();
        if(numRows == 0){

            Toast.makeText(this, "Table is empty", Toast.LENGTH_LONG).show();

        }

        else{

            Toast.makeText(this, "Table is NOT empty", Toast.LENGTH_LONG).show();



        }

        et_addNote = findViewById(R.id.et_addNote);

        frameLayout = findViewById(R.id.frameLayout);





        et_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                visibilityStatus1();
                makeNoteFragment = new MakeNoteFragment();
                myFragment = getSupportFragmentManager();
                fragmentTransaction = myFragment.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, makeNoteFragment);
                fragmentTransaction.commit();

            }
        });


    }

    @Override
    public void onBackPressed() {

        fragmentTransaction2 = myFragment.beginTransaction();
        fragmentTransaction2.detach(makeNoteFragment).commit();
        visibilityStatus2();

    }

    public static MainActivity getmInstanceActivity(){

        return weakActivity.get();

    }

    public void showAllNotes(){

        savedNotesView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, pdtd.getNoteTitleList(), pdtd.getNoteDescriptionList(), pdtd.getPrimaryIdList(), pdtd.getFlagList());
        savedNotesView.setAdapter(adapter);

    }

    public void visibilityStatus1(){  //After clicking on et_addNote & particular cardView

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        navigationView.setVisibility(View.INVISIBLE);
        et_addNote.setVisibility(View.INVISIBLE);
        savedNotesView.setVisibility(View.INVISIBLE);
        frameLayout.setVisibility(View.VISIBLE);



    }

    public void visibilityStatus2(){ //After clicking on bt_saveNote & bt_deleteNote in fragment

        et_addNote.setVisibility(View.VISIBLE);
        savedNotesView.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.INVISIBLE);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

               return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_share){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Dnotz");
            intent.putExtra(Intent.EXTRA_TEXT, "Add link of play store here. This text message will be sent.");
            startActivity(Intent.createChooser(intent, "Choose one"));
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;

        }
        return false;
    }

    public void setNavigationViewListener(){

        navigationView.setNavigationItemSelectedListener(this);

    }
}