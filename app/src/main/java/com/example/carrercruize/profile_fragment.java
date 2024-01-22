package com.example.carrercruize;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class profile_fragment extends Fragment {
    TextView emailview,ageview,expview,skillview,nameview,titleview,bioview;
    CircleImageView imageView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private  static  String CUSTOMER_KEY;
    PopupWindow popupWindow;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance ().getReference ().child ("userProfile");
    private  static FirebaseStorage storage;
    private  static StorageReference storageReference;
    private ProgressDialog progressDialog;
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
        imageView=view.findViewById (R.id.profileimageview);
        storage=FirebaseStorage.getInstance ();
        storageReference=storage.getReference ();
        mAuth=FirebaseAuth.getInstance ();
        currentUser=mAuth.getCurrentUser ();
        if(currentUser!=null){
            CUSTOMER_KEY=currentUser.getUid ();
        }
        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(Color.parseColor("#020D55"));
        }
        databaseReference.child (CUSTOMER_KEY).addListenerForSingleValueEvent (valulisner);


        //edit profile..........
        nameview.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view){
                showPopup (nameview);
            }
        });
        ageview.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                showPopup (ageview);
            }
        });
        expview.setOnClickListener (v->showPopup (expview));
        skillview.setOnClickListener (v->showPopup (skillview));
        titleview.setOnClickListener (v->showPopup (titleview));
        bioview.setOnClickListener (v->showPopup (bioview));
        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbuilder=new AlertDialog.Builder (getContext ());
                alertbuilder.setTitle ("Select your action");
                alertbuilder.setMessage ("Select Yes to change image");
                alertbuilder.setPositiveButton ("Yes", new DialogInterface.OnClickListener ( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        opengallery();
                        dialogInterface.dismiss ();
                    }
                });
                alertbuilder.setNegativeButton ("No", new DialogInterface.OnClickListener ( ) {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss ();
                    }
                });
                AlertDialog dialog=alertbuilder.create ();
                dialog.show ();
            }
        });
        return view;

    }
    //openGallery....
    private void opengallery(){
        Intent galleryintent=new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult (galleryintent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            Uri selectedimage=data.getData ();
            uploadimage(selectedimage);
        }
    }
    private void uploadimage(Uri imageUri){
        if(imageUri!=null){
            progressDialog =new ProgressDialog (getContext ());
            progressDialog.setTitle ("Uploading image...");
            progressDialog.show ();

            StorageReference imageref=storageReference.child ("images/"+ UUID.randomUUID ().toString ());
            imageref.putFile (imageUri).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> ( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageref.getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> ( ) {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadurl=uri.toString ();
                            HashMap hashMap=new HashMap (  );
                            hashMap.put ("imagevalue",downloadurl);
                            databaseReference.child (CUSTOMER_KEY).updateChildren (hashMap).addOnSuccessListener (v->{
                                Toast.makeText (getContext (), "Successfully uploaded image!", Toast.LENGTH_SHORT).show ( );
                                Glide.with (getContext ()).load (imageUri).fitCenter ().into (imageView);
                            }).addOnFailureListener (v-> Toast.makeText (getContext (), "Field not found!", Toast.LENGTH_SHORT).show ( ));
                        }
                    }).addOnFailureListener (v->{
                        progressDialog.dismiss ();
                        Toast.makeText (getContext (), "Can't download url", Toast.LENGTH_SHORT).show ( );
                    });
                }
            }).addOnProgressListener (new OnProgressListener<UploadTask.TaskSnapshot> ( ) {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    float percentage=(100*snapshot.getBytesTransferred ())/snapshot.getTotalByteCount ();
                    progressDialog.setMessage ("Progress: "+percentage+"% / 100%");
                }
            }).addOnCompleteListener (v->progressDialog.dismiss ()).addOnFailureListener (v->
                    Toast.makeText (getContext (), "Failed!", Toast.LENGTH_SHORT).show ( ));
        }
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
                String uri=userProfile1.getImagevalue ();
                if(uri!=" "){
                    Glide.with (getContext ()).load (uri).fitCenter ().into (imageView);
                }
            }else{
                Toast.makeText (getContext (), "Profile Not Created!", Toast.LENGTH_SHORT).show ( );
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    public void showPopup(TextView textView){
        View popupView = LayoutInflater.from(getActivity ().getApplicationContext ()).inflate(R.layout.editviewlayout, null);
        // Initialize the PopupWindow
        popupWindow = new PopupWindow (
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setAnimationStyle(R.anim.fade_in);
        EditText newnameedit=popupView.findViewById (R.id.editTextTextPersonName);
        Button savebtn=popupView.findViewById (R.id.savebutton4);
        savebtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                String nop=newnameedit.getText ().toString ();

                if(TextUtils.isEmpty (nop)){
                    newnameedit.setError ("Enter Valid Input");
                    newnameedit.requestFocus ();
                }else{
                    HashMap hashmap=new HashMap();
                    if(textView==nameview){
                    hashmap.put("namevalue",nop);}
                    else if(textView==titleview){
                        hashmap.put ("titlevalue",nop);
                    }else if(textView==ageview){
                        hashmap.put("agevalue",nop);
                    }else if(textView==expview){
                        hashmap.put("expvalue",nop);
                    }else if(textView==skillview){
                        hashmap.put ("skillvalue",nop);
                    }else if(textView==bioview){
                        hashmap.put ("biovalue",nop);
                    }
                    updatedatabase(hashmap,textView,nop);
                }
                popupWindow.dismiss ();
            }
        });
        int[] location = new int[2];
        textView.getLocationOnScreen(location);
        popupWindow.showAtLocation (nameview, Gravity.TOP | Gravity.START, location[0], location[1]);
    }
    public void updatedatabase(HashMap hashMap,TextView textView,String nop){
        databaseReference.child (CUSTOMER_KEY).updateChildren (hashMap).addOnSuccessListener (new OnSuccessListener ( ) {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText (getContext (), "Successfully Updated", Toast.LENGTH_SHORT).show ( );
                textView.setText (nop);
            }
        });
    }
}