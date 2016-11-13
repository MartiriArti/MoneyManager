package com.whoami.moneytracker.ui.fragments;

import android.graphics.Color;
import android.graphics.PointF;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.animation.OvershootInterpolator;

import com.activeandroid.query.Select;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.whoami.moneytracker.R;
import com.whoami.moneytracker.database.CategoryEntity;
import com.whoami.moneytracker.database.ExpenseEntity;
import com.whoami.moneytracker.ui.utils.PieChartModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment {

    private float[] datapoints;
    private String[] modelNames;
    private List<CategoryEntity> categoriesList;
    private List<ExpenseEntity> expensesList;

    @ViewById(R.id.dynamicArcView)
    DecoView arcView;

    @AfterViews
    void ready() {
        getActivity().setTitle(getString(R.string.nav_drawer_statistics));
        categoriesList = getDataList();
        expensesList = getExpensesList();

        if (expensesList.isEmpty() || categoriesList.isEmpty()) {
            Snackbar.make(getView(), R.string.empty_expenses, Snackbar.LENGTH_LONG).show();
        } else {
            createViewModel();
        }

    }

    private void createViewModel() {
        ArrayList<PieChartModel> viewModels = new ArrayList<>();
        for (CategoryEntity categoriesOne : categoriesList) {
            float viewModelSum = 0;
            String viewModelName = categoriesOne.name;

            for (ExpenseEntity expensesOne : expensesList) {

                if (expensesOne.category.name.equals(viewModelName)) {
                    viewModelSum += Float.parseFloat(expensesOne.price);
                }
            }

            if (viewModelSum != 0) {
                PieChartModel viewModel = new PieChartModel(viewModelName, viewModelSum);
                viewModels.add(viewModel);
            }

        }
        createDataForDeco(viewModels);
    }


    private List<CategoryEntity> getDataList() {
        return new Select()
                .from(CategoryEntity.class)
                .execute();
    }

    private List<ExpenseEntity> getExpensesList() {
        return new Select()
                .from(ExpenseEntity.class)
                .execute();
    }

    private void createDataForDeco(ArrayList<PieChartModel> viewModels) {
        Collections.sort(viewModels, Collections.reverseOrder());

        for (int c = 0; c < viewModels.size(); c++) {
            System.out.println("Output!!! " + viewModels.get(c).sum + "  " + viewModels.get(c).name);
        }


        modelNames = new String[viewModels.size()];
        datapoints = new float[viewModels.size()];

        for (int i = 0; i < viewModels.size(); i++) {
            modelNames[i] = viewModels.get(i).name;
            datapoints[i] = viewModels.get(i).sum;
        }

        performanceDecoView(datapoints, modelNames);

    }

    private void performanceDecoView(float[] datapoints, String[] modelNames) {
        float max = datapoints[0];
        arcView.configureAngles(360, 0);
        ArrayList<Float> tempList = new ArrayList<>();

        for (int a = 0; a < datapoints.length; a++) {
            tempList.add(datapoints[a]);
        }

        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, max, max)
                .setInitialVisibility(true)
                .setLineWidth(20f)
                .build());

        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setEffectRotations(1000)
                .setDuration(1500)
                .build());

        for (int sumIndex = 0; sumIndex < datapoints.length; sumIndex++) {

            Random random = new Random();

            SeriesItem seriesItem = new SeriesItem.Builder(Color.argb(100, random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                    .setRange(0, max, 0)
                    .setLineWidth(32f)
                    .setInset(new PointF(10f * (2 + sumIndex), 10f * (2 + sumIndex)))
                    .setSeriesLabel(new SeriesLabel.Builder(modelNames[sumIndex] + " " + datapoints[sumIndex]).build())
                    .setInterpolator(new OvershootInterpolator())
                    .setSpinDuration(4000)
                    .setChartStyle(SeriesItem.ChartStyle.STYLE_PIE)
                    .build();

            int seriesIndex = arcView.addSeries(seriesItem);
            arcView.addEvent(new DecoEvent.Builder(datapoints[sumIndex]).setIndex(seriesIndex).setDelay(1000).build());

            tempList.remove(datapoints[sumIndex]);

            if (tempList.contains(datapoints[sumIndex])) {
                arcView.addEvent(new DecoEvent.Builder(datapoints[sumIndex] + (5 + sumIndex)).setIndex(seriesIndex).setDelay(1500).build());
            }

        }
    }
}
