package canacollector.cc.com.example.android.canacollectormanager.View.Alambique;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;

/**
 * Created by joaovq on 11/01/16.
 */
public class AlambiqueAbaRendimento extends Fragment {
    private static TextView rendimento;
    private static TextView produtividadeMedia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alambique_aba_rendimento, container, false);

        rendimento          = (TextView) rootView.findViewById(R.id.rendimentoInput);
        produtividadeMedia  = (TextView) rootView.findViewById(R.id.produtividadeMediaInput);

        setTextView();

        return rootView;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Rendimento");
        }
    }

    public static void setTextView(){
        rendimento.setText("5584");
        produtividadeMedia.setText(AppQuery.getAreaTotal().toString());
    }
}
