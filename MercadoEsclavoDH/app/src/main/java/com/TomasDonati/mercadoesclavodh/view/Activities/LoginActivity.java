package com.TomasDonati.mercadoesclavodh.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.model.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "authTag";
    private Button loginButton;
    private EditText loginEmailEditText;
    private EditText loginPasswordEditText;
    private Button registerButton;
    private EditText registerEmailEditText;
    private EditText registerPasswordEditText;
    private EditText registerFullNameEditText;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private static final String USERS_COLLECTION = "users";

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
    }

    private void updateUI(FirebaseUser firebaseUser){
    }

    private void signIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "signInWithEmailAndPassword: success");
                    loginEmailEditText.setText(null);
                    loginPasswordEditText.setText(null);
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUi(user);
                }else{
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Error al iniciar sesion, revise su mail y contraseña e intentelo denuevo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createAccount(String email, String password){
        if(validEmail(email) && validPassword(password)){

            String userFullName = registerFullNameEditText.getText().toString();
            String userEmail = registerEmailEditText.getText().toString();
            firebaseFirestore = FirebaseFirestore.getInstance();
            final User newUser = new User(userEmail, userFullName);

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d( TAG,"createUserWithEmailAndPassword:success");
                        registerEmailEditText.setText(null);
                        registerPasswordEditText.setText(null);
                        registerFullNameEditText.setText(null);
                        Toast.makeText(LoginActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        firebaseFirestore.collection(USERS_COLLECTION).document(user.getUid()).set(newUser);
                    }else{
                        Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
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
        registerFullNameEditText = findViewById(R.id.loginActivity_editText_fullName);

    }

    private void updateUi(FirebaseUser currentUser){
        if(currentUser != null){
            Toast.makeText(this, "Has iniciado sesion", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
        }else{}
    }
}
