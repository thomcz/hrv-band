package hrv.band.app.ui.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import hrv.RRData;
import hrv.band.app.R;
import hrv.band.app.model.HRVParameterSettings;
import hrv.band.app.model.Measurement;
import hrv.band.app.ui.view.activity.IHistoryView;
import hrv.band.app.ui.view.activity.history.chartstrategy.AbstractChartDrawStrategy;
import hrv.band.app.ui.view.activity.history.chartstrategy.ChartDrawDayStrategy;
import hrv.band.app.ui.view.activity.history.chartstrategy.ChartDrawMonthStrategy;
import hrv.band.app.ui.view.activity.history.chartstrategy.ChartDrawWeekStrategy;
import hrv.band.app.ui.view.activity.history.measurementstrategy.AbstractParameterLoadStrategy;
import hrv.band.app.ui.view.activity.history.measurementstrategy.ParameterLoadDayStrategy;
import hrv.band.app.ui.view.activity.history.measurementstrategy.ParameterLoadMonthStrategy;
import hrv.band.app.ui.view.activity.history.measurementstrategy.ParameterLoadWeekStrategy;
import hrv.band.app.ui.view.fragment.HistoryFragment;
import hrv.calc.parameter.HRVParameterEnum;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Copyright (c) 2017
 * Created by Thomas on 22.04.2017.
 */

public class HistoryPresenter implements IHistoryPresenter {

    private List<Measurement> measurements;
    private AbstractChartDrawStrategy chartStrategy;
    private AbstractParameterLoadStrategy parameterStrategy;
    private Set<HRVParameterEnum> parameterSet;
    private Context context;
    private IHistoryView view;

    public HistoryPresenter(IHistoryView view, Context context) {
        this.view = view;
        this.context = context;
        parameterSet = HRVParameterSettings.DefaultSettings.visibleHRVParameters;
        chartStrategy = new ChartDrawDayStrategy();
        parameterStrategy = new ParameterLoadDayStrategy(context);
        this.measurements = getMeasurements(new Date());

    }

    private List<Measurement> getMeasurements(Date date) {
        return createMeasurements(parameterStrategy.loadParameter(date));
    }

    @Override
    public List<Measurement> getMeasurements() {
        return measurements;
    }

    @Override
    public void drawChart(ColumnChartView chart, HRVParameterEnum hrvValue, Context context) {
        chartStrategy.drawChart(measurements, chart, hrvValue, context);
    }

    @NonNull
    private List<Measurement> createMeasurements(List<Measurement> params) {
        List<Measurement> result = new ArrayList<>();
        for (Measurement parameter : params) {
            RRData.createFromRRInterval(parameter.getRRIntervals(), units.TimeUnit.SECOND);
            Measurement.MeasurementBuilder measurementBuilder = Measurement.from(parameter.getTime(), parameter.getRRIntervals())
                    .category(parameter.getCategory())
                    .rating(parameter.getRating())
                    .note(parameter.getNote());
            result.add(measurementBuilder.build());
        }
        return result;
    }

    @Override
    public List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();

        for (HRVParameterEnum parameter : parameterSet) {
            fragments.add(HistoryFragment.newInstance(parameter));
        }

        return fragments;
    }

    @Override
    public int getTitlePosition(HRVParameterEnum value) {
        int position = 0;
        for (HRVParameterEnum parameterEnum : parameterSet) {
            if (value.equals(parameterEnum)) {
                return position;
            }
            position++;
        }
        return 0;
    }

    @Override
    public String[] getPageTitles() {
        String[] titles = new String[parameterSet.size()];
        int i = 0;
        for (HRVParameterEnum parameter : parameterSet) {
            titles[i++] = parameter.toString();
        }
        return titles;
    }


    @Override
    public boolean setDrawStrategy(int id, Date date) {
        switch (id) {
            case R.id.menu_day:
                chartStrategy = new ChartDrawDayStrategy();
                parameterStrategy = new ParameterLoadDayStrategy(context);
                break;
            case R.id.menu_week:
                chartStrategy = new ChartDrawWeekStrategy();
                parameterStrategy = new ParameterLoadWeekStrategy(context);
                break;
            case R.id.menu_month:
                chartStrategy = new ChartDrawMonthStrategy(date);
                parameterStrategy = new ParameterLoadMonthStrategy(context);
                break;
            default: break;
        }
        updateMeasurements(date);
        view.updateFragments();
        return true;
    }

    @Override
    public void updateMeasurements(Date date) {
        measurements = getMeasurements(date);
    }
}
