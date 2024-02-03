package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.example.finalproject.databinding.ActivitySigninBinding;
import com.example.finalproject.model.User;
import com.example.finalproject.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    ActivitySigninBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onSignin();
        binding.signInProgressBar.setVisibility(View.GONE);
    }

    private void onSignin() {
        binding.signinBtn.setOnClickListener(View -> {
            binding.signInProgressBar.setVisibility(View.VISIBLE);
            String password, mail, firstName, lastName, bio, avatar;
            password = String.valueOf(binding.passwordTp.getText());
            firstName = String.valueOf(binding.firstNameTp.getText());
            lastName = String.valueOf(binding.lastNameTp.getText());
            mail = String.valueOf(binding.mailTp.getText());
            bio = String.valueOf(binding.bioTp.getText());
            avatar = "";
            if(!isFieldsEmpty(password, mail, firstName, lastName, bio)){
                User userToAdd = new User(password, mail, firstName,
                        lastName, bio, UserModel.instance.userId, avatar);

               saveUser(userToAdd);
            }
        });
    }

    private boolean isFieldsEmpty(String password,  String mail,
                                  String firstName, String lastName,
                                  String bio){

        if(TextUtils.isEmpty(password)){
            Toast.makeText(SigninActivity.this, "enter password", Toast.LENGTH_SHORT).show();
            return  true;
        }
        if(TextUtils.isEmpty(firstName)){
            Toast.makeText(SigninActivity.this, "enter first name", Toast.LENGTH_SHORT).show();
            return  true;
        }
        if(TextUtils.isEmpty(lastName)){
            Toast.makeText(SigninActivity.this, "enter last name", Toast.LENGTH_SHORT).show();
            return  true;
        }
        if(TextUtils.isEmpty(mail)){
            Toast.makeText(SigninActivity.this, "enter mail", Toast.LENGTH_SHORT).show();
            return  true;
        }
        if(TextUtils.isEmpty(bio)){
            Toast.makeText(SigninActivity.this, "enter bio", Toast.LENGTH_SHORT).show();
            return  true;
        }

        return false;
    }

    private void changeActivity(Class activityClass) {
        Intent intent = new Intent(SigninActivity.this, activityClass);
        startActivity(intent);
        finish();
    }

    private void saveUser(User userToAdd) {
        mAuth.createUserWithEmailAndPassword(userToAdd.getMail(), userToAdd.getPassword())
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            userToAdd.setUid(uid);
                            UserModel.instance.setUserId(uid);
                            UserModel.instance.createUser(userToAdd, (unused) -> changeActivity(MainActivity.class));
                        } else {
                            Toast.makeText(SigninActivity.this, "can not sign you",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }
}