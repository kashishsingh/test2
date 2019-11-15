package com.example.test2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;



import java.util.ArrayList;

public class viewActivity extends AppCompatActivity implements MyAdapter.OnNoteListener
{
    private DatabaseReference ref;
    RecyclerView recyclerView;
    ArrayList<Model_basicDetails> list;
    ArrayList <Model_PGDetail> list2;
    MyAdapter adapter;
    MyAdapter.OnNoteListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewActivity.this));

        //Query query = FirebaseDatabase.getInstance().getReference().orderByChild("name").equalTo("kashish");

        ref = FirebaseDatabase.getInstance().getReference("user");
        ref.addValueEventListener(valueEventListener);

        listener = this;
    }

    ValueEventListener valueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {

            list = new ArrayList<Model_basicDetails>();
            list2 = new ArrayList<Model_PGDetail>();

               /* if(dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren())
                    {
                       // Model_basicDetails detail = dataSnapshot2.child("Basic").getValue(Model_basicDetails.class);
                        Model_PGDetail detail = dataSnapshot2.child("PG").getValue(Model_PGDetail.class);
                        if(detail.getYOJ().equals("346"))
                        {
                            Model_basicDetails detail2 = dataSnapshot2.child("Basic").getValue(Model_basicDetails.class);
                            list.add(detail2);
                        }
                    }
                    adapter = new MyAdapter(ViewActivity.this, list);
                    recyclerView.setAdapter(adapter); */
            if(dataSnapshot.exists())
            {
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren())
                {
                    Model_basicDetails detail = dataSnapshot2.child("Basic").getValue(Model_basicDetails.class);
                    list.add(detail);

                }
                adapter = new MyAdapter(viewActivity.this, list, listener);
                recyclerView.setAdapter(adapter);

            }
            else
            {
                Toast.makeText(viewActivity.this, "kuch nahe mela bosarike", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_Search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Type to search...");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return  true;
    }

    @Override
    public void onNoteClick(int position) {
        Model_basicDetails obj = list.get(position);
        String id = obj.getId();
        Intent intent = new Intent(viewActivity.this,DetailedViewActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);



    }
}


