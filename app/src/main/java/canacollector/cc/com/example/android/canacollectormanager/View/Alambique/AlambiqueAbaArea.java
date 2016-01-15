package canacollector.cc.com.example.android.canacollectormanager.View.Alambique;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
public class AlambiqueAbaArea extends Fragment {

    private PieChart pie;

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
                        Log.w("PieChart","Clicked Segment: " + segment.getTitle());
                        Talhao talhao = AppQuery.findTalhao(segment.getTitle());
                        createDetailsView(talhao);
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

    private void createDetailsView(Talhao talhao){
        String message = "TALHAO: " + talhao.getName() + "\nAREA : " + talhao.getArea() + " hectares";
        AlertDialog.Builder userDetails = new AlertDialog.Builder(this.getActivity());
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