package com.example.carrercruize;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signup= findViewById(R.id.button);
        Button signinbygoogle=findViewById(R.id.button2);
        TextView loginbtn=findViewById(R.id.textView7);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,login_page.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,signup_page.class));
            }
        });

        //handling google signin
        gso=new GoogleSignInOptions.Builder ( GoogleSignInOptions.DEFAULT_SIGN_IN ).requestEmail ().build ();
        gsc= GoogleSignIn.getClient (this,gso);
        signinbygoogle.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

    }
    void signin(){
        Intent signinintent=gsc.getSignInIntent ();
        startActivityForResult (signinintent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if(resultCode==1000){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent (data);
            try{
                task.getResult (ApiException.class);
                finish ();
                startActivity (new Intent ( MainActivity.this,dashboard.class ));
            }
            catch (ApiException e){
                Toast.makeText (this, "Something went wrong", Toast.LENGTH_SHORT).show ( );
            }
        }
    }
}