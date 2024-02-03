package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.example.finalproject.databinding.ActivityLoginBinding;
import com.example.finalproject.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth;

        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                UserModel.instance.setUserId(currentUser.getUid());
                changeActivity(MainActivity.class);
            }
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onNotSingedIn();
        onLogin();

        binding.loginProgressBar.setVisibility(View.GONE);
    }

    private void onNotSingedIn() {
        binding.signinTv.setOnClickListener(View -> {
            changeActivity(SigninActivity.class);
        });
    }

   public void onLogin(){
       binding.loginBtn.setOnClickListener(View -> {
           binding.loginProgressBar.setVisibility(View.VISIBLE);
           String mail, password;
           mail = String.valueOf(binding.mailTp.getText());
           password = String.valueOf(binding.passwordTp.getText());
           if(TextUtils.isEmpty(mail)){
               Toast.makeText(LoginActivity.this, "enter mail", Toast.LENGTH_SHORT).show();
           } else if(TextUtils.isEmpty(password)){
               Toast.makeText(LoginActivity.this, "enter password", Toast.LENGTH_SHORT).show();
           } else {
               isAuth(mail, password);
           }
       });
    }

    private void changeActivity(Class activityClass) {
        Intent intent = new Intent(LoginActivity.this, activityClass);
        startActivity(intent);
    }

    public void isAuth(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserModel.instance.setUserId(task.getResult().getUser().getUid());
                            changeActivity(MainActivity.class);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "can not log you in.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}