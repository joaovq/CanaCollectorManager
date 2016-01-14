package canacollector.cc.com.example.android.canacollectormanager.View.Alambique;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.parse.ParseObject;

import java.util.Arrays;
import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.Model.Cachaca;
import canacollector.cc.com.example.android.canacollectormanager.Model.Mosto;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;

/**
 * Created by joaovq on 11/01/16.
 */
public class AlambiqueAbaProducao extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alambique_aba_producao, container, false);

        XYPlot grafico1 = (XYPlot) rootView.findViewById(R.id.graficoQtdMostoPorTempo);
        gerarGraficoMoagem(grafico1);
        XYPlot grafico2 = (XYPlot) rootView.findViewById(R.id.graficoMoagemPorTempo);
        XYPlot grafico3 = (XYPlot) rootView.findViewById(R.id.graficoProducaoDiariaPorTempo);

        gerarGrafico1(grafico1);
        gerarGrafico2(grafico2);
        gerarGrafico3(grafico3);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Produção");
        }
    }

    private void gerarGraficoMoagem(XYPlot grafico){
        List<ParseObject> mostos = AppQuery.getAllMosto();

        Log.w("Alambique Aba Geral", "" + mostos.size());

        Number[] series1Numbers = new Number[mostos.size()];
        for(int i = 0; i < mostos.size(); i++)
            series1Numbers[i] = i+1;

        Number[] series2Numbers = new Number[mostos.size()];
        for(int i = 0; i < mostos.size(); i++){
            Mosto mosto = (Mosto) mostos.get(i);
            series2Numbers[i] = mosto.getCaldo();
        }

        XYSeries series = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                Arrays.asList(series2Numbers),
                "Mosto vs Tempo");


        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());

        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
//        series1Format.configure(getActivity().getApplicationContext(),
//                R.layout.line_point_formatter_with_labels);
        

        // add a new series' to the xyplot:
        grafico.addSeries(series, series1Format);

        // reduce the number of range labels
        grafico.setTicksPerRangeLabel(3);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        grafico.getGraphWidget().setDomainLabelOrientation(-45);
    }

    private void gerarGrafico2(XYPlot grafico){
        List<ParseObject> mostos = AppQuery.getAllMosto();

        Number[] series1Numbers = new Number[mostos.size()];
        for(int i = 0; i < mostos.size(); i++)
            series1Numbers[i] = i+1;

        Number[] series2Numbers = new Number[mostos.size()];
        for(int i = 0; i < mostos.size(); i++){
            Mosto mosto = (Mosto) mostos.get(i);
            series2Numbers[i] = mosto.getCana();
        }

        XYSeries series = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                Arrays.asList(series2Numbers),
                "Moagem vs Tempo");


        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());

        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
//        series1Format.configure(getActivity().getApplicationContext(),
//                R.layout.line_point_formatter_with_labels);


        // add a new series' to the xyplot:
        grafico.addSeries(series, series1Format);

        // reduce the number of range labels
        grafico.setTicksPerRangeLabel(3);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        grafico.getGraphWidget().setDomainLabelOrientation(-45);
    }

    private void gerarGrafico3(XYPlot grafico){
        List<ParseObject> allCachaca = AppQuery.getAllCachaca();

        Log.w("Produção", "" + allCachaca.size());

        Number[] series1Numbers = new Number[allCachaca.size()];
        for(int i = 0; i < allCachaca.size(); i++)
            series1Numbers[i] = i+1;

        Number[] series2Numbers = new Number[allCachaca.size()];
        for(int i = 0; i < allCachaca.size(); i++){
            Cachaca cachaca = (Cachaca) allCachaca.get(i);
            series2Numbers[i] = cachaca.getQuantidade();
        }

        XYSeries series = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                Arrays.asList(series2Numbers),
                "Produção Diária vs Tempo");


        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());

        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
//        series1Format.configure(getActivity().getApplicationContext(),
//                R.layout.line_point_formatter_with_labels);


        // add a new series' to the xyplot:
        grafico.addSeries(series, series1Format);

        // reduce the number of range labels
        grafico.setTicksPerRangeLabel(3);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        grafico.getGraphWidget().setDomainLabelOrientation(-45);
    }
}