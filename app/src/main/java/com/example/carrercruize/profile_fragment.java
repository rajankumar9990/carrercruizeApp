package com.example.carrercruize;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profile_fragment extends Fragment {
    TextView emailview,ageview,expview,skillview,nameview,titleview,bioview;
    String emailvalue,agevalue,expvalue,skillvalue;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance ().getReference ().child ("userProfile");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_profile_fragment,container,false);
        nameview=view.findViewById (R.id.textView83);
        titleview=view.findViewById (R.id.textView10);
        bioview=view.findViewById (R.id.textView12);
        emailview=view.findViewById (R.id.textView85);
        ageview=view.findViewById (R.id.textView88);
        expview=view.findViewById (R.id.textView92);
        skillview=view.findViewById (R.id.textView42);
        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(0xFF04147A);
        }
        String uniqueid=uniqueidGenerator.getUniqueId (view.getContext ());
        databaseReference.child (uniqueid).addListenerForSingleValueEvent (valulisner);
        return view;

    }


    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
    ValueEventListener valulisner=new ValueEventListener ( ) {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists ()){
                userProfile userProfile1=snapshot.getValue ( userProfile.class );
                emailview.setText (userProfile1.getEmailvalue ());
                ageview.setText (userProfile1.getAgevalue ());
                expview.setText (userProfile1.getExpvalue ());
                skillview.setText (userProfile1.getSkillvalue ());
                nameview.setText (userProfile1.getNamevalue ());
                titleview.setText (userProfile1.getTitlevalue ());
                bioview.setText (userProfile1.getBiovalue ());
            }else{
                Toast.makeText (getContext (), "Profile Not Created!", Toast.LENGTH_SHORT).show ( );
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}