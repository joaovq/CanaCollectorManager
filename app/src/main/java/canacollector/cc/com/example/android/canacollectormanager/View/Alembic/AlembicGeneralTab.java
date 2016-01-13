package canacollector.cc.com.example.android.canacollectormanager.View.Alembic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import canacollector.cc.com.example.android.canacollectormanager.R;

public class AlembicGeneralTab extends Fragment{
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alembic_general_tab, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if(!toolbar.getMenu().hasVisibleItems())
            toolbar.inflateMenu(R.menu.menu_alembic);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.button_refresh:
                        Log.w("Alembic General Tab", "Refresh button clicked");
                        return true;
                }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            toolbar.setTitle("Geral");
        }
    }
}
