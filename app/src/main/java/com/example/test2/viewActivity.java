
package com.example.test2;


import android.content.Intent;
        import android.os.Bundle;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import com.google.firebase.database.ValueEventListener;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.inputmethod.EditorInfo;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.Toast;
        import java.util.ArrayList;

public class viewActivity extends AppCompatActivity implements MyAdapter.OnNoteListener
{
    private DatabaseReference ref;
    RecyclerView recyclerView;
    private Button button;
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
        toolbar.setTitle("Students Detail");
        setSupportActionBar(toolbar);

        button = findViewById(R.id.btnSearch);
        final EditText filterCgpa = findViewById(R.id.tvCgpa);
        filterCgpa.setSelected(false);
        filterCgpa.setSingleLine();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewActivity.this));

        ref = FirebaseDatabase.getInstance().getReference("user");
        ref.addValueEventListener(valueEventListener);

        listener = this;

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(filterCgpa.getText().toString().equals(""))
              {

              }
              else
              {
                  ref.removeEventListener(valueEventListener);
                  DatabaseReference searchRef;
                  searchRef = FirebaseDatabase.getInstance().getReference("user");
                  searchRef.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          list = new ArrayList<Model_basicDetails>();
                          list2 = new ArrayList<Model_PGDetail>();

                          if (dataSnapshot.exists()) {
                              for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                  Model_PGDetail detail = dataSnapshot2.child("PG").getValue(Model_PGDetail.class);
                                  try
                                  {
                                      float c = Float.parseFloat(detail.getCGPA());
                                      float d = Float.parseFloat(filterCgpa.getText().toString());//input from EditText
                                      if (c > d) {
                                          Model_basicDetails detail2 = dataSnapshot2.child("Basic").getValue(Model_basicDetails.class);
                                          list.add(detail2);
                                      }
                                  } catch (Exception e) {

                                  }
                                  adapter = new MyAdapter(viewActivity.this, list, listener);
                                  recyclerView.setAdapter(adapter);
                              }
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });
              }

          }
      });



    }
    ValueEventListener valueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {

            list = new ArrayList<Model_basicDetails>();
            list2 = new ArrayList<Model_PGDetail>();

            if(dataSnapshot.exists())
            {
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren())
                {
                    //Model_PGDetail detail2 = dataSnapshot2.child("PG").getValue(Model_PGDetail.class);
                    Model_basicDetails detail = dataSnapshot2.child("Basic").getValue(Model_basicDetails.class);
                    list.add(detail);
                   // list2.add(detail2);

                }
                adapter = new MyAdapter(viewActivity.this, list, listener);
                recyclerView.setAdapter(adapter);

            }
            else
            {
                Toast.makeText(viewActivity.this, "Nothing to Display", Toast.LENGTH_SHORT).show();
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
        searchView.setQueryHint("Type student name to search...");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                try
                {
                    adapter.getFilter().filter(newText);
                }
                catch (Exception e)
                {

                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNoteClick(int position)
    {
        Model_basicDetails obj = list.get(position);
        String id = obj.getId();
        Intent intent = new Intent(viewActivity.this,DetailedViewActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }


}



