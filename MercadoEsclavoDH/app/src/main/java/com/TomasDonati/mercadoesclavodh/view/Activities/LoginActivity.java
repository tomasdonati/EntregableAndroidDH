package com.TomasDonati.mercadoesclavodh.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TomasDonati.mercadoesclavodh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "authTag";
    private FirebaseAuth firebaseAuth;
    private Button loginButton;
    private EditText loginEmailEditText;
    private EditText loginPasswordEditText;
    private Button registerButton;
    private EditText registerEmailEditText;
    private EditText registerPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewFinder();
        firebaseAuth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(loginEmailEditText.getText().toString(), loginPasswordEditText.getText().toString());
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(registerEmailEditText.getText().toString(), registerPasswordEditText.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void updateUI(FirebaseUser firebaseUser){
    }

    private void signIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "signInWithEmailAndPassword: success");
                    loginEmailEditText.setText(null);
                    loginPasswordEditText.setText(null);
                    Toast.makeText(LoginActivity.this, "Has iniciado sesion", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    //updateUI(user);
                }else{
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Error al iniciar sesion, revise su mail y contraseña e intentelo denuevo", Toast.LENGTH_LONG).show();
                    //updateUI(null);
                }
            }
        });
    }

    private void createAccount(String email, String password){
        if(validEmail(email) && validPassword(password)){
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d( TAG,"createUserWithEmailAndPassword:success");
                        registerEmailEditText.setText(null);
                        registerPasswordEditText.setText(null);
                        Toast.makeText(LoginActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        //updateUI(user);
                    }else{
                        Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                }
            });
        } else{
            Toast.makeText(this, "Por favor utilizar un mail y password valido", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean validEmail(String email){
        return email.length() >= 13 && email.contains("@");
    }
    private Boolean validPassword(String password){
        return password.length() >= 3;
    }

    private void viewFinder(){
        loginEmailEditText = findViewById(R.id.loginActivity_editText_loginEmail);
        loginPasswordEditText = findViewById(R.id.loginActivity_editText_loginPassword);
        loginButton = findViewById(R.id.loginActivity_button_loginButton);
        registerEmailEditText = findViewById(R.id.loginActivity_editText_registerEmail);
        registerPasswordEditText = findViewById(R.id.loginActivity_editText_registerPassword);
        registerButton = findViewById(R.id.loginActivity_button_registerButton);

    }

    private void updateUi(FirebaseUser currentUser){
        if(currentUser != null){

        }
    }
}
