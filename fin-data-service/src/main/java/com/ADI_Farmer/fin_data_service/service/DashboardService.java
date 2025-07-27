package com.ADI_Farmer.fin_data_service.service;

import com.ADI_Farmer.fin_data_service.model.DashboardConfig;
import com.ADI_Farmer.fin_data_service.model.AnalyticsData;
import com.ADI_Farmer.fin_data_service.repository.DashboardConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

    @Autowired
    private DashboardConfigRepository configRepository;

    @Autowired
    private TransactionService transactionService;

    public List<DashboardConfig> getAllConfigs() {
        return configRepository.findAll();
    }

    public List<DashboardConfig> getActiveConfigs() {
        return configRepository.findByEnabledOrderByPosition(true);
    }

    public DashboardConfig saveConfig(DashboardConfig config) {
        return configRepository.save(config);
    }

    public Optional<DashboardConfig> getConfigById(String id) {
        return configRepository.findById(id);
    }

    public void deleteConfig(String id) {
        configRepository.deleteById(id);
    }

    public void toggleConfig(String id) {
        Optional<DashboardConfig> configOpt = configRepository.findById(id);
        if (configOpt.isPresent()) {
            DashboardConfig config = configOpt.get();
            config.setEnabled(!config.isEnabled());
            configRepository.save(config);
        }
    }

    public AnalyticsData getAnalyticsForConfig(String dataSource) {
        switch (dataSource.toLowerCase()) {
            case "amount":
                return transactionService.getTransactionAmountByType();
            case "status":
                return transactionService.getTransactionCountByStatus();
            case "fraud":
                return transactionService.getFraudAnalysis();
            case "trend":
                return transactionService.getAmountTrend();
            case "device":
                return transactionService.getDeviceUsageAnalysis();
            default:
                return transactionService.getTransactionAmountByType();
        }
    }

    public List<AnalyticsData> getAllAnalytics() {
        return transactionService.getAllAnalytics();
    }

    public Object getDashboardSummary() {
        return transactionService.getDashboardSummary();
    }

    // Initialize default dashboard configurations
    public void initializeDefaultConfigs() {
        if (configRepository.count() == 0) {
            DashboardConfig config1 = new DashboardConfig();
            config1.setChartType("bar");
            config1.setTitle("Transaction Amount by Type");
            config1.setDataSource("amount");
            config1.setTimeRange("all");
            config1.setRefreshInterval(30);
            config1.setEnabled(true);
            config1.setPosition(1);
            configRepository.save(config1);

            DashboardConfig config2 = new DashboardConfig();
            config2.setChartType("pie");
            config2.setTitle("Transaction Status Distribution");
            config2.setDataSource("status");
            config2.setTimeRange("all");
            config2.setRefreshInterval(30);
            config2.setEnabled(true);
            config2.setPosition(2);
            configRepository.save(config2);

            DashboardConfig config3 = new DashboardConfig();
            config3.setChartType("doughnut");
            config3.setTitle("Fraud Analysis");
            config3.setDataSource("fraud");
            config3.setTimeRange("all");
            config3.setRefreshInterval(30);
            config3.setEnabled(true);
            config3.setPosition(3);
            configRepository.save(config3);

            DashboardConfig config4 = new DashboardConfig();
            config4.setChartType("line");
            config4.setTitle("Transaction Trend");
            config4.setDataSource("trend");
            config4.setTimeRange("all");
            config4.setRefreshInterval(30);
            config4.setEnabled(true);
            config4.setPosition(4);
            configRepository.save(config4);
        }
    }
}