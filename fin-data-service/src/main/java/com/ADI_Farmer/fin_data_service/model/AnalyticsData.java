package com.ADI_Farmer.fin_data_service.model;

import java.util.Map;
import java.util.List;

public class AnalyticsData {
    private String chartType;
    private String title;
    private List<String> labels;
    private List<Object> data;
    private Map<String, Object> options;
    private long timestamp;

    public AnalyticsData() {
    }

    public AnalyticsData(String chartType, String title, List<String> labels, List<Object> data) {
        this.chartType = chartType;
        this.title = title;
        this.labels = labels;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}