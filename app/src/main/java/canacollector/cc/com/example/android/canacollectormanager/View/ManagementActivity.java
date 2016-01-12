package canacollector.cc.com.example.android.canacollectormanager.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import canacollector.cc.com.example.android.canacollectormanager.R;

public class ManagementActivity extends AppCompatActivity {

    private ArrayAdapter usersListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usersListAdapter = new ArrayAdapter<String>(
                this,
                R.layout.content_item_user_list_view,
                R.id.list_item_user_textview,
                new ArrayList<String>());

        final ListView listView = (ListView) this.findViewById(R.id.list_view_users);
        listView.setAdapter(usersListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String forecast = usersListAdapter.getItem(i);
                AlertDialog.Builder userSelectedAlert = new AlertDialog.Builder(ManagementActivity.this);
                userSelectedAlert.setMessage("O que deseja fazer?");
                userSelectedAlert.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                userSelectedAlert.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

                AlertDialog alert = userSelectedAlert.create();
                alert.show();
            }
        });

        usersListAdapter.add("José");
        usersListAdapter.add("Antonio");
        usersListAdapter.add("Calors");
        usersListAdapter.add("Marcos");
        usersListAdapter.add("Rodrigo");
        usersListAdapter.add("Marcio");
        usersListAdapter.add("Geraldo");
        usersListAdapter.add("Claudio");
        usersListAdapter.add("Luis");
        usersListAdapter.add("Marcelo");
        usersListAdapter.add("Eduardo");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_management, menu);
        return true;
    }

}
