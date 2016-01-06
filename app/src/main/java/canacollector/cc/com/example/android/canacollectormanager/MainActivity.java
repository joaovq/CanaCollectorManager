package canacollector.cc.com.example.android.canacollectormanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
            finish();
        }
    }

}
