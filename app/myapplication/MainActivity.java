package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    TextView changeSignupModeTextView;
    Boolean signupModeActive=true;
    EditText passwordEdit;
    public void ShowUserList(){
        Intent intent= new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
            Signup(view);
        }
        return false;
    }

    public void onClick(View view){
        if(view.getId()==R.id.changeSignupTextView){
            Button sigupButton=(Button) findViewById(R.id.button);

            if(signupModeActive){
                signupModeActive=false;
                sigupButton.setText("Login");
                changeSignupModeTextView.setText("Or,Signup");
            }
            else{
                signupModeActive=true;
                sigupButton.setText("Signup");
                changeSignupModeTextView.setText("Or,Login");
            }
            Log.i("App Info","ChangeSıgnupMode");
        }
        else if(view.getId()==R.id.constraitlayoutbackground || view.getId()==R.id.imageView){
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }

    }
    EditText usernameEdit;
    public void Signup(View view) {
        usernameEdit = (EditText) findViewById(R.id.editText);

            if ((usernameEdit.getText().toString().matches("")) || (passwordEdit.getText().toString().matches(""))) {
                Toast.makeText(MainActivity.this, "A username and password required", Toast.LENGTH_SHORT).show();
            } else {
                //kayıt alma

                if (signupModeActive) {
                    ParseUser user = new ParseUser();
                    user.setUsername(usernameEdit.getText().toString());
                    user.setPassword(passwordEdit.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Log.i("Signup", "Successfull");
                                ShowUserList();

                            } else {

                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                // yada giriş alma

                else{
                    ParseUser.logInInBackground(usernameEdit.getText().toString(), passwordEdit.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(user!=null){
                                Log.i("login","Succesfull");
                                ShowUserList();
                            }
                            else{
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
        ConstraintLayout constraintLayout=(ConstraintLayout) findViewById(R.id.constraitlayoutbackground);
        ImageView logoImageView=(ImageView) findViewById(R.id.imageView);
        constraintLayout.setOnClickListener(this);
        logoImageView.setOnClickListener(this);
        changeSignupModeTextView=(TextView) findViewById(R.id.changeSignupTextView);
        changeSignupModeTextView.setOnClickListener(this);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        passwordEdit = (EditText) findViewById(R.id.editText2);
        passwordEdit.setOnKeyListener(this);

    }



}
