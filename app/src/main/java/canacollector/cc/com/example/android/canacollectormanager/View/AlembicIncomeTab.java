package canacollector.cc.com.example.android.canacollectormanager.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import canacollector.cc.com.example.android.canacollectormanager.R;

/**
 * Created by joaovq on 11/01/16.
 */
public class AlembicIncomeTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alembic_income_tab, container, false);

        return rootView;
    }
}
