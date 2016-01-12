package canacollector.cc.com.example.android.canacollectormanager.View.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import canacollector.cc.com.example.android.canacollectormanager.Model.Alambique;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppUtils;

public class NewAccountActivity extends AppCompatActivity {

    //UI Elements
    private EditText userNameInput, alambiqueInput, passwordInput, retypePasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userNameInput  = (EditText) findViewById(R.id.userNameTextInput);
        passwordInput  = (EditText) findViewById(R.id.passwordTextInput);
        retypePasswordInput = (EditText) findViewById(R.id.retypeTextInput);
        alambiqueInput = (EditText) findViewById(R.id.alambiqueTextInput);
        alambiqueInput.requestFocus();

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
        String alambiqueNome = alambiqueInput.getText().toString();

        if(TextUtils.isEmpty(alambiqueNome)) {
            alambiqueInput.setError("Campo obrigatório");
            focusView = alambiqueInput;
            cancel = true;
        }

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

        if(TextUtils.isEmpty(alambiqueNome)){
            alambiqueInput.setError(getString(R.string.error_field_required));
            if(focusView == null)
                focusView = alambiqueInput;
            cancel = true;
        }

        if(cancel)
            focusView.requestFocus();
        else{
            if (AppUtils.isOnline(this.getApplicationContext())) {
                Alambique alambique = new Alambique();
                alambique.setName(alambiqueNome);
                try {
                    alambique.save();
                }
                catch (Exception e) {
                    Log.e("Criando o alambique:" , e.toString());
                }

                ParseUser user = new ParseUser();
                user.setUsername(userName);
                user.setPassword(password);
                user.put("alambique", alambique);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(NewAccountActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            System.out.println(e.toString());
                            return;
                        }
                    }
                });
            }
            else
                Toast.makeText(this, "Verifique sua conexao com a internet e tente novamente...", Toast.LENGTH_LONG).show();
        }

    }
}
