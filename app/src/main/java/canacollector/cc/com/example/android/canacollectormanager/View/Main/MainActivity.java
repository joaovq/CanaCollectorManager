package canacollector.cc.com.example.android.canacollectormanager.View.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppUtils;
import canacollector.cc.com.example.android.canacollectormanager.Utils.MyProgressDialog;
import canacollector.cc.com.example.android.canacollectormanager.View.Alambique.AlembiqueActivity;
import canacollector.cc.com.example.android.canacollectormanager.View.Gestao.GestaoActivity;


public class MainActivity extends AppCompatActivity {

    private static int backButtonCount = 0;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Recupera dados do servidor e salva em cache para futuras consultas
        AppUtils.recoverDataInBackgroung();

        Button managementButton = (Button) findViewById(R.id.managemetButton);
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GestaoActivity.class);
                startActivity(intent);
            }
        });

        Button productionButton = (Button) findViewById(R.id.productionButton);
        productionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlembiqueActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        backButtonCount++;
        if(backButtonCount == 1){
            Toast.makeText(this.getApplicationContext(), getString(R.string.message_type_again), Toast.LENGTH_SHORT).show();
        }

        else if(backButtonCount > 1){
            ParseUser.logOut();
            pDialog = MyProgressDialog.getProgressDialog(this,"Finalizando! Aguarde");
            pDialog.show();
            new WaitingMessage().execute();
        }
    }

    private class WaitingMessage extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            long timeStarted = System.currentTimeMillis();
            while(System.currentTimeMillis() - timeStarted < 1500){
                // wait for 1.5 ms
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e("MainActivity", "thread interrupted", e);
                    return false;
                }
            }
            return  true;
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                pDialog.dismiss();
                finish();
                System.exit(0);
            }
        }
    };
}
