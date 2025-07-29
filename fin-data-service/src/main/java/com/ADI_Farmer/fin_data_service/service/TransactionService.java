package com.ADI_Farmer.fin_data_service.service;

import com.ADI_Farmer.fin_data_service.model.Transaction;
import com.ADI_Farmer.fin_data_service.model.AnalyticsData;
import com.ADI_Farmer.fin_data_service.repository.TransactionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public List<Transaction> loadCSV(String filePath) throws IOException, CsvValidationException {
        List<Transaction> transactions = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] header = reader.readNext(); // skip header
            String[] line;

            while ((line = reader.readNext()) != null) {
                if (line.length < 15) {
                    System.err.println("Skipping malformed line (expected 15 fields): " + String.join(",", line));
                    continue;
                }

                try {
                    Transaction txn = new Transaction();
                    txn.setTransactionId(line[0].trim());
                    txn.setSenderAccountId(line[1].trim());
                    txn.setReceiverAccountId(line[2].trim());
                    txn.setAmount(Double.parseDouble(line[3].trim()));
                    txn.setType(line[4].trim());
                    txn.setTimestamp(line[5].trim());
                    txn.setStatus(line[6].trim());
                    txn.setFraudFlag(Boolean.parseBoolean(line[7].trim()));

                    txn.setLatitude(line[8].trim());
                    txn.setLongitude(line[9].trim());

                    txn.setDeviceUsed(line[10].trim());
                    txn.setNetworkSliceId(line[11].trim());
                    txn.setLatency(Integer.parseInt(line[12].trim()));
                    txn.setBandwidth(Integer.parseInt(line[13].trim()));
                    txn.setPinCode(Integer.parseInt(line[14].trim()));

                    transactions.add(txn);
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + String.join(",", line));
                    e.printStackTrace();
                }
            }

            repository.saveAll(transactions);
        }

        return transactions;
    }

    public List<Transaction> loadCSVFromFile(MultipartFile file) throws IOException, CsvValidationException {
        List<Transaction> transactions = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] header = reader.readNext();
            String[] line;

            while ((line = reader.readNext()) != null) {
                if (line.length < 15) {
                    System.err.println("Skipping malformed line (expected 15 fields): " + String.join(",", line));
                    continue;
                }

                try {
                    Transaction txn = new Transaction();
                    txn.setTransactionId(line[0].trim());
                    txn.setSenderAccountId(line[1].trim());
                    txn.setReceiverAccountId(line[2].trim());
                    txn.setAmount(Double.parseDouble(line[3].trim()));
                    txn.setType(line[4].trim());
                    txn.setTimestamp(line[5].trim());
                    txn.setStatus(line[6].trim());
                    txn.setFraudFlag(Boolean.parseBoolean(line[7].trim()));

                    txn.setLatitude(line[8].trim());
                    txn.setLongitude(line[9].trim());

                    txn.setDeviceUsed(line[10].trim());
                    txn.setNetworkSliceId(line[11].trim());
                    txn.setLatency(Integer.parseInt(line[12].trim()));
                    txn.setBandwidth(Integer.parseInt(line[13].trim()));
                    txn.setPinCode(Integer.parseInt(line[14].trim()));

                    transactions.add(txn);
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + String.join(",", line));
                    e.printStackTrace();
                }
            }

            repository.saveAll(transactions);
        }

        return transactions;
    }

    public List<Transaction> loadJSON(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(filePath);

        if (!jsonFile.exists() || !jsonFile.isFile()) {
            throw new IOException("File not cfound: " + filePath);
        }

        try {
            List<Transaction> transactions = mapper.readValue(
                    jsonFile,
                    new TypeReference<List<Transaction>>() {
                    });
            repository.saveAll(transactions);
            return transactions;
        } catch (Exception e) {
            System.err.println("Error parsing JSON file: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Transaction> loadJSONFromFile(MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Transaction> transactions = mapper.readValue(
                    file.getInputStream(),
                    new TypeReference<List<Transaction>>() {
                    });
            repository.saveAll(transactions);
            return transactions;
        } catch (Exception e) {
            System.err.println("Error parsing JSON file: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public List<Transaction> getTransactionsForDashboard(String status, String type) {
        if (status != null) {
            return repository.findByStatus(status);
        }
        if (type != null) {
            return repository.findByType(type);
        }
        return repository.findAll();
    }

    // Analytics Methods for Dashboard
    public AnalyticsData getTransactionAmountByType() {
        List<Transaction> transactions = repository.findAll();

        Map<String, Double> amountByType = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getType,
                        Collectors.summingDouble(Transaction::getAmount)));

        List<String> labels = new ArrayList<>(amountByType.keySet());
        List<Object> data = new ArrayList<>(amountByType.values());

        return new AnalyticsData("bar", "Transaction Amount by Type", labels, data);
    }

    public AnalyticsData getTransactionCountByStatus() {
        List<Transaction> transactions = repository.findAll();

        Map<String, Long> countByStatus = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getStatus,
                        Collectors.counting()));

        List<String> labels = new ArrayList<>(countByStatus.keySet());
        List<Object> data = new ArrayList<>(countByStatus.values());

        return new AnalyticsData("pie", "Transaction Count by Status", labels, data);
    }

    public AnalyticsData getFraudAnalysis() {
        List<Transaction> transactions = repository.findAll();

        long fraudCount = transactions.stream().filter(t -> t.isFraudFlag() && "ATM".equalsIgnoreCase(t.getDeviceUsed())).count();
        long legitimateCount = transactions.size() - fraudCount;

        List<String> labels = Arrays.asList("Legitimate", "Fraudulent");
        List<Object> data = Arrays.asList(legitimateCount, fraudCount);

        return new AnalyticsData("doughnut", "Fraud Analysis", labels, data);
    }

    public AnalyticsData getAmountTrend() {
        List<Transaction> transactions = repository.findAll();

        // Group by date (assuming timestamp format is consistent)
        Map<String, Double> amountByDate = transactions.stream()
                .collect(Collectors.groupingBy(
                        txn -> txn.getTimestamp().substring(0, 10), // Get date part
                        Collectors.summingDouble(Transaction::getAmount)));

        // Sort by date
        List<String> labels = amountByDate.keySet().stream()
                .sorted()
                .collect(Collectors.toList());
        List<Object> data = labels.stream()
                .map(amountByDate::get)
                .collect(Collectors.toList());

        return new AnalyticsData("line", "Transaction Amount Trend", labels, data);
    }

    public AnalyticsData getDeviceUsageAnalysis() {
        List<Transaction> transactions = repository.findAll();

        Map<String, Long> deviceUsage = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDeviceUsed,
                        Collectors.counting()));

        List<String> labels = new ArrayList<>(deviceUsage.keySet());
        List<Object> data = new ArrayList<>(deviceUsage.values());

        return new AnalyticsData("bar", "Device Usage Analysis", labels, data);
    }

    public Map<String, Object> getDashboardSummary() {
        List<Transaction> transactions = repository.findAll();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalTransactions", transactions.size());
        summary.put("totalAmount", transactions.stream().mapToDouble(Transaction::getAmount).sum());
        summary.put("fraudCount", transactions.stream().filter(Transaction::isFraudFlag).count());
        summary.put("uniqueDevices", transactions.stream().map(Transaction::getDeviceUsed).distinct().count());
        summary.put("uniqueTypes", transactions.stream().map(Transaction::getType).distinct().count());

        return summary;
    }

    public List<AnalyticsData> getAllAnalytics() {
        List<AnalyticsData> analytics = new ArrayList<>();
        analytics.add(getTransactionAmountByType());
        analytics.add(getTransactionCountByStatus());
        analytics.add(getFraudAnalysis());
        analytics.add(getAmountTrend());
        analytics.add(getDeviceUsageAnalysis());
        return analytics;
    }
}
