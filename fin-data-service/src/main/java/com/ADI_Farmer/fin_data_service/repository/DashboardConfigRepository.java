package com.ADI_Farmer.fin_data_service.repository;

import com.ADI_Farmer.fin_data_service.model.DashboardConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DashboardConfigRepository extends MongoRepository<DashboardConfig, String> {
    List<DashboardConfig> findByEnabledOrderByPosition(boolean enabled);

    List<DashboardConfig> findByChartType(String chartType);
}