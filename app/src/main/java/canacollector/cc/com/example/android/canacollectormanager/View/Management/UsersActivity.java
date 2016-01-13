package canacollector.cc.com.example.android.canacollectormanager.View.Management;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import canacollector.cc.com.example.android.canacollectormanager.R;

public class UsersActivity extends AppCompatActivity {

    private ArrayAdapter usersListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                final String user = usersListAdapter.getItem(position).toString();

                AlertDialog.Builder userSelectedAlert = new AlertDialog.Builder(UsersActivity.this);
                userSelectedAlert.setMessage("O que deseja fazer?");
                userSelectedAlert.setPositiveButton("Editar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                    }

                });
                userSelectedAlert.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        AlertDialog.Builder deleteUserAlert = new AlertDialog.Builder(UsersActivity.this);
                        deleteUserAlert.setMessage("Deseja deletar " + user + " ?");

                        deleteUserAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.cancel();
                            }
                        });

                        deleteUserAlert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.cancel();
                            }
                        });

                        AlertDialog alert = deleteUserAlert.create();
                        alert.show();
                    }
                });
                userSelectedAlert.setNeutralButton("Detalhes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        createDetailsView(user);
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
        menu.findItem(R.id.button_refresh).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.button_new_user){
            Intent intent = new Intent(this,NewUserActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDetailsView(String user){
        String message = "Funcionário: " + user;
        AlertDialog.Builder userDetails = new AlertDialog.Builder(UsersActivity.this);
        userDetails.setMessage(message);
        userDetails.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });

        AlertDialog alert = userDetails.create();
        alert.show();
    }

}
