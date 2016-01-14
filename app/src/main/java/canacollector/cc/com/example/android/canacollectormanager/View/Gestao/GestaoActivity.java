package canacollector.cc.com.example.android.canacollectormanager.View.Gestao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import canacollector.cc.com.example.android.canacollectormanager.R;

public class GestaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button usersButton = (Button) findViewById(R.id.usersButton);
        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestaoActivity.this, UsuariosActivity.class);
                startActivity(intent);
            }
        });
    }

}
