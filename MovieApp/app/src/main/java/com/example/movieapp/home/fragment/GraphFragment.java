package com.example.movieapp.home.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.model.BarGraphModel;
import com.example.movieapp.model.MoviesPerMonth;
import com.example.movieapp.model.TopRecentMovies;
import com.example.movieapp.restapi.RestApi;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment {

    private Button startDateIdBtn;
    private TextView startDateTxtView;
    private Button endDateTxtIdBtn;
    private TextView endDateTxtView;
    private Button showBarGrphBtn;
    private RestApi restApi;
    private PieChart pieChart;
    private PieData updatedData;
    private BarChart barChartGrph;
    private Typeface tf;
    private List<String> yearRange;
    private ArrayAdapter<String> rangeAdapter;
    private Spinner yearSpinner;
    private Button barChartBtnT;
    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restApi = new RestApi();
        yearRange = new ArrayList<>();
        yearRange.add("2015");
        yearRange.add("2016");
        yearRange.add("2017");
        yearRange.add("2018");
        yearRange.add("2019");
        yearRange.add("2020");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        yearSpinner = view.findViewById(R.id.mySpinnerYeRange);
        startDateIdBtn = view.findViewById(R.id.startDateId);
        startDateTxtView = view.findViewById(R.id.startDateTxt);
        endDateTxtIdBtn = view.findViewById(R.id.endDateTxtId);
        endDateTxtView = view.findViewById(R.id.endDateTxt);
        showBarGrphBtn = view.findViewById(R.id.showBarGrphId);
        pieChart = view.findViewById(R.id.pieChartId);
        barChartGrph = view.findViewById(R.id.barChartId);
        barChartBtnT = view.findViewById(R.id.barChartBtn);
        rangeAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item, yearRange);
        yearSpinner.setAdapter(rangeAdapter);

        pieChart.getDescription().setEnabled(false);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        pieChart.setCenterTextTypeface(tf);
        pieChart.setCenterText(generateCenterText());
        pieChart.setCenterTextSize(10f);
        pieChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(50f);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);


        barChartBtnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowBarGraph showBarGraph = new ShowBarGraph();
                showBarGraph.execute(yearSpinner.getSelectedItem().toString());
            }
        });


        startDateIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStartDateButton();
            }
        });

        endDateTxtIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEndDateButton();
            }
        });

        showBarGrphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDt = startDateTxtView.getText().toString();
                String endDt = endDateTxtView.getText().toString();
                ShowPieGraph showPieGraph = new ShowPieGraph();
                showPieGraph.execute(startDt, endDt);
            }
        });

        return view;
    }
    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Revenues\nQuarters 2015");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }
    private void handleStartDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("yyyy-MM-dd", calendar1).toString();

                startDateTxtView.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    private void handleEndDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("yyyy-MM-dd", calendar1).toString();

                endDateTxtView.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    private class ShowPieGraph extends AsyncTask<String, Void, List<BarGraphModel>> {
        @Override
        protected List<BarGraphModel> doInBackground(String... params) {
            return restApi.moviesWatchPerSuburb(params);
        }

        @Override
        protected void onPostExecute(List<BarGraphModel> result) {

            ArrayList<PieEntry> entries1 = new ArrayList<>();

            for(int i = 0; i < result.size(); i++) {
                entries1.add(new PieEntry((float) result.get(i).getCount(), "Suburb: " + result.get(i).getSuburb()));
            }
            PieDataSet ds1 = new PieDataSet(entries1, "No of movies watched per suburbs");
            ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            ds1.setSliceSpace(2f);
            ds1.setValueTextColor(Color.WHITE);
            ds1.setValueTextSize(12f);
            updatedData = new PieData(ds1);
            pieChart.setData(updatedData);

            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleRadius(7);
            pieChart.setTransparentCircleRadius(10);
            pieChart.invalidate();
        }

    }

    private class ShowBarGraph extends AsyncTask<String, Void, List<MoviesPerMonth>> {
        @Override
        protected List<MoviesPerMonth> doInBackground(String... params) {
            return restApi.moviesWatchedPerMonth(params);
        }

        @Override
        protected void onPostExecute(List<MoviesPerMonth> result) {

            ArrayList<BarEntry> entries1 = new ArrayList<>();

            ArrayList<String> labels = new ArrayList();

            for(int i = 0; i < result.size(); i++) {
                int count = result.get(i).getCount();
                String months = result.get(i).getMonth();
                labels.add(months);
                entries1.add(new BarEntry(getSpecifiedMonth(result.get(i).getMonth()),result.get(i).getCount()));
            }
            XAxis xAxis = barChartGrph.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            BarDataSet barDataSet = new BarDataSet(entries1, "BarDataSet");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(16f);

            BarData barData = new BarData(barDataSet);

            barChartGrph.setFitBars(true);
            barChartGrph.setData(barData);
            barChartGrph.getDescription().setText("Bar Chart");
            barChartGrph.animateY(2000);


            barChartGrph.invalidate();
        }

    }

    protected PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
        }
        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(100f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(120f);
        PieData d = new PieData(ds1);
        return d;
    }
    private int getSpecifiedMonth(String monthNumber){
        switch (monthNumber) {

            case "January":

                return 1;

            case "February":

                return 2;

            case "March":

                return 3;

            case "April":

                return 4;

            case "May":

                return 5;

            case "June":

                return 6;

            case "July":

                return 7;

            case "August":

                return 8;

            case "September":

                return 9;

            case "October":

                return 10;

            case "November":

                return 11;

            case "December":

                return 12;

        }
        return 0;

    }

}
