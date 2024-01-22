package com.example.carrercruize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class verifyOtp extends AppCompatActivity {
    TextView resendcodebtn;
    EditText verificationcodeView;
    TextInputEditText mobileview;
    Button sendotpbtn;
    Button verifybtn;
    String verificationId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_verify_otp);
        resendcodebtn=findViewById (R.id.textViewResendCodeOtp);
        verificationcodeView=findViewById (R.id.editTextVerificationCodeotp);
        verifybtn=findViewById (R.id.buttonVerifyCodeotp);
        mobileview=findViewById (R.id.mobileEditText);
        sendotpbtn=findViewById (R.id.sendCodeotp);
        mAuth=FirebaseAuth.getInstance ();
        sendotpbtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                String phoneNumber = mobileview.getText().toString().trim();
                if (!phoneNumber.isEmpty() & phoneNumber.length ()==10) {
                    // Start the verification process
                    sendVerificationCode(phoneNumber);
                } else {
                    Toast.makeText(verifyOtp.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        verifybtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                String verificationCode = verificationcodeView.getText().toString().trim();
                if (!TextUtils.isEmpty(verificationCode)) {
                    verifyPhoneNumberWithCode(verificationCode);
                } else {
                    Toast.makeText(verifyOtp.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
                }
            }
        });
        resendcodebtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                String phoneNumber = mobileview.getText().toString().trim();
                if (!phoneNumber.isEmpty() & phoneNumber.length ()==10) {
                    // Start the verification process
                    sendVerificationCode(phoneNumber);
                } else {
                    Toast.makeText(verifyOtp.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void verifyPhoneNumberWithCode(String verificationCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,
                60, // Timeout duration
                java.util.concurrent.TimeUnit.SECONDS,
                this, // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number.
                        // Save the verification ID and resending token so we can use them later.
                        verifyOtp.this.verificationId = verificationId;
                        verifybtn.setVisibility (View.VISIBLE);
                        verificationcodeView.setVisibility (View.VISIBLE);
                        resendcodebtn.setVisibility (View.VISIBLE);
                        sendotpbtn.setVisibility (View.GONE);
                        Toast.makeText(verifyOtp.this, "Verification Code Sent", Toast.LENGTH_SHORT).show();
                        // Now, you can show a UI to let the user enter the verification code.
                        // Typically, you'd use a dialog or another activity to handle this.
                        // For simplicity, we'll skip this step in this example.
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance, if the the phone number format is not valid.
                        Toast.makeText(verifyOtp.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity (new Intent ( verifyOtp.this,signup_page.class ));
                        Toast.makeText(verifyOtp.this, "Authentication successful!", Toast.LENGTH_SHORT).show();
                        finish ();
                        // You can navigate to the next screen or perform other actions here
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(verifyOtp.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}