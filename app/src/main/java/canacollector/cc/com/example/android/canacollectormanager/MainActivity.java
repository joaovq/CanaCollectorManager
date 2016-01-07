package canacollector.cc.com.example.android.canacollectormanager;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    private static int backButtonCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        backButtonCount++;
        if(backButtonCount == 1){
            Toast.makeText(this.getApplicationContext(), "Aperta mais uma vez para sair!", Toast.LENGTH_SHORT).show();
        }

        else if(backButtonCount > 1){
            ParseUser.logOut();
            showProgress();
            new WaitingMessage().execute();
        }
    }

    ProgressDialog pDialog;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage(getString(R.string.message_leaving_app));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
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
