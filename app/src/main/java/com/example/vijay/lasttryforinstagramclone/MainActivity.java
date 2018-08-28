package com.example.vijay.lasttryforinstagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean checkSignUp=true;
    TextView textView;
    EditText password;

    public void showUserList(){
        Intent intent=new Intent(getApplicationContext(),userList.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_ENTER) && event.getAction()==KeyEvent.ACTION_DOWN){
            signup(v);
        }
        return false;
    }
    @Override
    public void onClick(View view){
        if (view.getId()==R.id.orLogIn){
            Button button=findViewById(R.id.button);
            if (checkSignUp){
                checkSignUp=false;
                textView.setText("or, Sign up");
                button.setText("Log in");
            }
            else{
                checkSignUp=true;
                textView.setText("or, Log in");
                button.setText("Sign up");
            }

        }else if (view.getId()==R.id.imageView || view.getId()==R.id.relativeLayout){
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }
    public void signup(View view){
        EditText username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        if (username.getText().toString().matches("") || password.getText().toString().matches("")){
            Toast.makeText(this, "Username and password is required!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (checkSignUp){
            ParseUser user=new ParseUser();
            user.setPassword(password.getText().toString());
            user.setUsername(username.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){
                        Toast.makeText(MainActivity.this,"Signed in successfully",Toast.LENGTH_SHORT).show();
                        showUserList();
                    }
                    else {
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });}
            else{
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user!=null){
                            Toast.makeText(MainActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                            showUserList();

                        }
                        else {
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instagram");
        textView=findViewById(R.id.orLogIn);
        textView.setOnClickListener(this);
        password=findViewById(R.id.password);
        password.setOnKeyListener(this);

        ImageView imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        RelativeLayout layout=findViewById(R.id.relativeLayout);
        layout.setOnClickListener(this);

        if (ParseUser.getCurrentUser()!=null){
            showUserList();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


}
