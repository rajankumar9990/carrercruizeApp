package com.example.carrercruize;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
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
        SignInButton signinbygoogle=findViewById(R.id.button2);
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
        GoogleSignInAccount aat=GoogleSignIn.getLastSignedInAccount (this);
        if(aat!=null){
            startActivity (new Intent ( MainActivity.this,dashboard.class ));
        }
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
        if(requestCode==1000){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent (data);
            handleSignInResult (task);

        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText (MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show ();
            startActivity (new Intent ( MainActivity.this,dashboard.class ));

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            Log.d ("login Failed",e.toString ());
        }
    }
}