package canacollector.cc.com.example.android.canacollectormanager.View.Alembic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import canacollector.cc.com.example.android.canacollectormanager.R;

/**
 * Created by joaovq on 11/01/16.
 */
public class AlembicProductionTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alembic_production_tab, container, false);

        return rootView;
    }
}