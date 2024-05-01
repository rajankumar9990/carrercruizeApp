package com.example.carrercruize;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class favorite_fragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<String> keys=new ArrayList<> (  );
    private static  joblistadapter jobAdapter;
    private static joblisting jobListings;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance ( ).getReference ( ).child ("favorateModel");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate (R.layout.fragment_job_fragment, container, false);
        recyclerView=view.findViewById (R.id.recyclerviewfav);
        jobListings=new joblisting ();
        jobAdapter=new joblistadapter (jobListings);
        jobAdapter.showfav=false;
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ( )));
        recyclerView.setAdapter (jobAdapter);
        databaseReference.addValueEventListener (vallisner);
        recyclerView.setOnLongClickListener (new View.OnLongClickListener ( ) {
            @Override
            public boolean onLongClick(View view) {
                int position=jobAdapter.getposition ();
                Toast.makeText (getContext (), String.valueOf (position), Toast.LENGTH_SHORT).show ( );
                databaseReference.child (keys.get (position)).removeValue ().addOnCompleteListener (new OnCompleteListener<Void> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText (getContext (), "Removed...", Toast.LENGTH_SHORT).show ( );
                        jobAdapter.showshimmer=true;
                        databaseReference.addValueEventListener (vallisner);
                    }
                });

                return false;
            }
        });
        return view;
    }
    public ValueEventListener vallisner=new ValueEventListener ( ) {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists ()){
                Log.d("Status checking:"," wait...");
                ArrayList<String> datelist=new ArrayList<> (  );
                ArrayList<String> locationlist=new ArrayList<> (  );
                ArrayList<String> salarylist=new ArrayList<> (  );
                ArrayList<String> linklists=new ArrayList<> (  );
                ArrayList<String> companylist=new ArrayList<> (  );
                ArrayList<String> jtitlelist=new ArrayList<> (  );
                ArrayList<ArrayList<String>> tagsList=new ArrayList<> (  );
                ArrayList<String>experienceList=new ArrayList<> (  );
                ArrayList<String> descriptionList=new ArrayList<> (  );
                for(DataSnapshot dataSnapshot:snapshot.getChildren ()){
                    Log.d("fetching data","sucess");
                    favorateModel favorateModell=dataSnapshot.getValue ( favorateModel.class );
                    datelist.add (favorateModell.getDate ());
                    descriptionList.add (favorateModell.getDescription ());
                    locationlist.add (favorateModell.getLocation ());
                    salarylist.add (favorateModell.getSalary ());
                    linklists.add (favorateModell.getLink ());
                    companylist.add (favorateModell.getCompany ());
                    jtitlelist.add (favorateModell.getJtitle ());
                    tagsList.add (favorateModell.getTagsList ());
                    experienceList.add (favorateModell.getExperience ());
                    keys.add (favorateModell.getKey ());
                }
                if(!datelist.isEmpty ()){
                    Log.d("Adding to joblistings","finalizing.......");
                    jobListings.setDatelist (datelist);
                    jobListings.setLocationlist (locationlist);
                    jobListings.setSalarylist (salarylist);
                    jobListings.setLinklists (linklists);
                    jobListings.setCompanylist (companylist);
                    jobListings.setJtitlelist (jtitlelist);
                    jobListings.setTagsList (tagsList);
                    jobListings.setExperienceList (experienceList);
                    jobListings.setDescriptionList (descriptionList);
                    jobAdapter.showshimmer=false;
                    jobAdapter.notifyDataSetChanged ();
                }
            }else{
                Toast.makeText (getContext (), "No item to show...", Toast.LENGTH_SHORT).show ( );
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText (getContext (), "Error in database!", Toast.LENGTH_SHORT).show ( );
        }
    };
}