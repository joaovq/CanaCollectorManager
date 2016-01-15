package canacollector.cc.com.example.android.canacollectormanager.View.Alambique;

import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.Model.Talhao;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;

/**
 * Created by joaovq on 11/01/16.
 */
public class AlambiqueAbaArea extends Fragment implements AdapterView.OnItemSelectedListener {
    private static TextView areaReserva;
    private static TextView areaTotalTalhoes;
    private static TextView areaPorTalhao;
    private static Spinner talhoes;

    private PieChart pie;

    private Segment s1;
    private Segment s2;
    private Segment s3;
    private Segment s4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alambique_aba_area, container, false);

        // initialize our XYPlot reference:
        pie = (PieChart) rootView.findViewById(R.id.mySimplePieChart);

        // detect segment clicks:
        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie.getPieWidget().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        // handle the segment click...for now, just print
                        // the clicked segment's title to the console:
                        System.out.println("Clicked Segment: " + segment.getTitle());
                    }
                }
                return false;
            }
        });

        int r = 0, g= 0, b = 0;
        Talhao temp;
        List<ParseObject> objectList = AppQuery.getAllTalhoes();
        for(ParseObject parseObject : objectList) {
            SegmentFormatter sf1 = new SegmentFormatter(Color.rgb(r,g,b));
            temp = (Talhao) parseObject;
            Segment talhao = new Segment(temp.getName(), temp.getArea());
            pie.addSeries(talhao, sf1);
            r = (r + 30) % 245;
            g = (g + 75) % 245;
            b = (b + 100) % 245;
        }

        pie.getRenderer(PieRenderer.class).setDonutSize(30/100f, PieRenderer.DonutMode.PERCENT);
        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Área");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if(parent.getChildAt(0) != null) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            ((TextView) parent.getChildAt(0)).setTextSize(22);
        }

        String talhao = (String)parent.getSelectedItem();
        areaPorTalhao.setText(AppQuery.getAreaNoTalhao(talhao).toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        talhoes.setSelection(0);
    }

    public List<String> getTalhoes(List<Talhao> talhoes){
        List<String> nomeTalhoes = new ArrayList<String>();
        for(Talhao talhao : talhoes)
            nomeTalhoes.add(talhao.getName());

        return nomeTalhoes;
    }

    public void spinnerSetup(List<String> itens)
    {
        ArrayAdapter<String> talhaoAdapter;
        talhaoAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, itens);

        talhaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        talhoes.setAdapter(talhaoAdapter);
    }

    public static void setTextView(){
        String hectare = " hectare";
        areaReserva.setText(AppQuery.getAreaReserva().toString() + hectare);
        areaTotalTalhoes.setText(AppQuery.getAreaTotal().toString() + hectare);

        String talhao = (String)talhoes.getSelectedItem();
        areaPorTalhao.setText(AppQuery.getAreaNoTalhao(talhao).toString() + hectare);

    }

}