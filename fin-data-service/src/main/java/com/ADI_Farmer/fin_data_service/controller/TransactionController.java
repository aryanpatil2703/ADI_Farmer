package com.ADI_Farmer.fin_data_service.controller;

import com.ADI_Farmer.fin_data_service.model.Transaction;
import com.ADI_Farmer.fin_data_service.model.AnalyticsData;
import com.ADI_Farmer.fin_data_service.model.DashboardConfig;
import com.ADI_Farmer.fin_data_service.service.TransactionService;
import com.ADI_Farmer.fin_data_service.service.DashboardService;
import com.opencsv.exceptions.CsvValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Financial Data API", description = "APIs for managing financial transactions and analytics")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DashboardService dashboardService;

                // Transaction endpoints
            @PostMapping(value = "/transactions/upload/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
            @Operation(summary = "Upload CSV file", description = "Upload a CSV file containing transaction data to be stored in MongoDB")
            @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "CSV file uploaded successfully",
                    content = @Content(schema = @Schema(implementation = Transaction.class))),
                @ApiResponse(responseCode = "400", description = "Invalid CSV format"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
            public List<Transaction> uploadCSV(
                    @Parameter(description = "CSV file to upload", required = true)
                    @RequestParam("file") MultipartFile file) throws IOException, CsvValidationException {
                return transactionService.loadCSVFromFile(file);
            }

            @PostMapping(value = "/transactions/upload/csv/path", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            @Operation(summary = "Upload CSV by path", description = "Load CSV file from a file path on the server")
            @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "CSV file loaded successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid file path"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
            public List<Transaction> uploadCSVByPath(
                    @Parameter(description = "File path on server", required = true)
                    @RequestParam String path) throws IOException, CsvValidationException {
                return transactionService.loadCSV(path);
            }

            @PostMapping(value = "/transactions/upload/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
            @Operation(summary = "Upload JSON file", description = "Upload a JSON file containing transaction data")
            @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "JSON file uploaded successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid JSON format"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
            public List<Transaction> uploadJSON(
                    @Parameter(description = "JSON file to upload", required = true)
                    @RequestParam("file") MultipartFile file) throws IOException {
                return transactionService.loadJSONFromFile(file);
            }

            @PostMapping(value = "/transactions/upload/json/path", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            @Operation(summary = "Upload JSON by path", description = "Load JSON file from a file path on the server")
            @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "JSON file loaded successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid file path"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
            public List<Transaction> uploadJSONByPath(
                    @Parameter(description = "File path on server", required = true)
                    @RequestParam String path) throws IOException {
                return transactionService.loadJSON(path);
            }

                @GetMapping("/transactions")
            @Operation(summary = "Get all transactions", description = "Retrieve all transactions from the database")
            @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved all transactions")
            })
            public List<Transaction> getAll() {
                return transactionService.getAllTransactions();
            }

    @GetMapping("/transactions/dashboard")
    public List<Transaction> getDashboardData(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type) {
        return transactionService.getTransactionsForDashboard(status, type);
    }

    // Analytics endpoints
    @GetMapping("/analytics/summary")
    public Map<String, Object> getDashboardSummary() {
        return transactionService.getDashboardSummary();
    }

    @GetMapping("/analytics/all")
    public List<AnalyticsData> getAllAnalytics() {
        return transactionService.getAllAnalytics();
    }

    @GetMapping("/analytics/amount-by-type")
    public AnalyticsData getAmountByType() {
        return transactionService.getTransactionAmountByType();
    }

    @GetMapping("/analytics/status-distribution")
    public AnalyticsData getStatusDistribution() {
        return transactionService.getTransactionCountByStatus();
    }

    @GetMapping("/analytics/fraud-analysis")
    public AnalyticsData getFraudAnalysis() {
        return transactionService.getFraudAnalysis();
    }

    @GetMapping("/analytics/amount-trend")
    public AnalyticsData getAmountTrend() {
        return transactionService.getAmountTrend();
    }

    @GetMapping("/analytics/device-usage")
    public AnalyticsData getDeviceUsage() {
        return transactionService.getDeviceUsageAnalysis();
    }

    // Dashboard configuration endpoints
    @GetMapping("/dashboard/configs")
    public List<DashboardConfig> getAllConfigs() {
        return dashboardService.getAllConfigs();
    }

    @GetMapping("/dashboard/configs/active")
    public List<DashboardConfig> getActiveConfigs() {
        return dashboardService.getActiveConfigs();
    }

    @PostMapping("/dashboard/configs")
    public DashboardConfig saveConfig(@RequestBody DashboardConfig config) {
        return dashboardService.saveConfig(config);
    }

    @PutMapping("/dashboard/configs/{id}")
    public ResponseEntity<DashboardConfig> updateConfig(@PathVariable String id, @RequestBody DashboardConfig config) {
        return dashboardService.getConfigById(id)
                .map(existingConfig -> {
                    config.setId(id);
                    return ResponseEntity.ok(dashboardService.saveConfig(config));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/dashboard/configs/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable String id) {
        if (dashboardService.getConfigById(id).isPresent()) {
            dashboardService.deleteConfig(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/dashboard/configs/{id}/toggle")
    public ResponseEntity<Void> toggleConfig(@PathVariable String id) {
        if (dashboardService.getConfigById(id).isPresent()) {
            dashboardService.toggleConfig(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dashboard/analytics/{dataSource}")
    public AnalyticsData getAnalyticsForDataSource(@PathVariable String dataSource) {
        return dashboardService.getAnalyticsForConfig(dataSource);
    }

    // Health check endpoint
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "Financial Data Aggregator API");
    }
}
