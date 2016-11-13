package com.whoami.moneytracker.ui.utils;

public class PieChartModel implements Comparable {
    public String name;
    public float sum;

    public PieChartModel(String name, float sum)
    {
        this.name = name;
        this.sum = sum;
    }

    @Override
    public int compareTo(Object vmodel) {
        PieChartModel tmp = (PieChartModel)vmodel;
        if (this.sum < tmp.sum)
        {
            return -1;
        }
        else if(this.sum > tmp.sum)
        {
            return 1;
        }
        return 0;
    }
}
