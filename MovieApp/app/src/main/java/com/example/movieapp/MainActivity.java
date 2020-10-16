package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.home.HomeActivity;
import com.example.movieapp.model.Person;
import com.example.movieapp.restapi.RestApi;
import com.example.movieapp.utility.Hashing;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    private RestApi restApi = null;
    private EditText userNameEditText;
    private EditText passwdEditText;
    private Button loginBtn;
    private String username;
    private String password;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button signUpButton;
    private Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText = findViewById(R.id.userName);
        passwdEditText = findViewById(R.id.userPassword);
        loginBtn = findViewById(R.id.loginBtn);
        signUpButton = findViewById(R.id.signUpBtn);
        //lyrichassan100@gmail.com
        userNameEditText.setText("");
        //lyrichasan
        passwdEditText.setText("");
        restApi = new RestApi();



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                //        intent.putExtra("userName",this.username);
                //intent.putExtra("person",p);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private class GetAllStudentsTask extends AsyncTask<Void, Void, String>
    {
        @Override protected String doInBackground(Void... params)
        {
            String hashedVal= "";
            try {
                hashedVal = Hashing.toHexString(Hashing.getSHA(password));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return restApi.verifyUserLogIn(username,hashedVal);
        }

        @Override protected void onPostExecute(String result)
        {
            Person p = null;
            if(result!=null){

                Gson g = new Gson();
                try {
                    p = g.fromJson(result, Person.class);
                }
                catch (Exception e){
                    p = null;
                }
                if(p!=null){
                    successfulLogIn(p);
                }else{
                    Toast.makeText(MainActivity.this,"Username or password is incorrect",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void successfulLogIn(Person p){
        Intent intent = new Intent(this, HomeActivity.class);
//        intent.putExtra("userName",this.username);
        //intent.putExtra("person",p);
        startActivity(intent);
    }


    private void validateUserNamePasswd() throws NoSuchAlgorithmException {
        username = userNameEditText.getText().toString().trim();
        password = passwdEditText.getText().toString().trim();
        if(username.length()==0 || password.length()==0){
            Toast.makeText(MainActivity.this,"Username or password can not be empty",Toast.LENGTH_LONG).show();
        }else{
            if(username.matches(emailPattern)){
                GetAllStudentsTask getAllStudentsTask = new GetAllStudentsTask ();
                getAllStudentsTask.execute();
            }else{
                Toast.makeText(MainActivity.this,"Please provide valid email address format",Toast.LENGTH_LONG).show();
            }
        }
        //Add some validation
        //Email format
        //Email and password not empty
        //password length must be greater than 4



    }
}
