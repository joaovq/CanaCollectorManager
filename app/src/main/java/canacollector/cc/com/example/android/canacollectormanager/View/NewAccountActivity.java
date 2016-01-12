package canacollector.cc.com.example.android.canacollectormanager.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import canacollector.cc.com.example.android.canacollectormanager.R;

public class NewAccountActivity extends AppCompatActivity {

    //UI Elements
    private EditText userNameInput;
    private EditText passwordInput;
    private EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userNameInput  = (EditText) findViewById(R.id.userNameTextInput);
        userNameInput.requestFocus();
        passwordInput  = (EditText) findViewById(R.id.passwordTextInput);
        emailInput     = (EditText) findViewById(R.id.emailTextInput);

        Button newAccountButton = (Button) findViewById(R.id.newAccountButton);
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempNewAccount();
            }
        });
    }

    private void attempNewAccount(){
        View focusView  = null;
        Boolean cancel  = false;
        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String email    = emailInput.getText().toString();

        if(TextUtils.isEmpty(userName)){
            userNameInput.setError(getString(R.string.error_field_required));
            focusView = userNameInput;
            cancel = true;
        }

        if(TextUtils.isEmpty(password)){
            passwordInput.setError(getString(R.string.error_field_required));
            if(focusView == null)
                focusView = passwordInput;
            cancel = true;
        }

        if(TextUtils.isEmpty(email)){
            emailInput.setError(getString(R.string.error_field_required));
            if(focusView == null)
                focusView = emailInput;
            cancel = true;
        }

        if(cancel)
            focusView.requestFocus();
        else{
            ParseUser user = new ParseUser();
            user.setUsername(userName);
            user.setPassword(password);
            user.setEmail(email);

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Intent intent = new Intent(NewAccountActivity.this,LoginActivity.class);
                        startActivity(intent);
                    } else {
                        System.out.println(e.toString());
                        return;
                    }
                }
            });
        }

    }
}
