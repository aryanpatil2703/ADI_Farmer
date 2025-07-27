package com.ADI_Farmer.fin_data_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String transactionId;
    private String senderAccountId;
    private String receiverAccountId;
    private double amount;
    private String type;
    private String timestamp;         // You can use LocalDateTime if you parse it properly
    private String status;
    private boolean fraudFlag;
    private String latitude;
    private String longitude;
    private String deviceUsed;
    private String networkSliceId;
    private int latency;              // in ms
    private int bandwidth;            // in Mbps
    private int pinCode;

    // Getters and Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getSenderAccountId() { return senderAccountId; }
    public void setSenderAccountId(String senderAccountId) { this.senderAccountId = senderAccountId; }

    public String getReceiverAccountId() { return receiverAccountId; }
    public void setReceiverAccountId(String receiverAccountId) { this.receiverAccountId = receiverAccountId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isFraudFlag() { return fraudFlag; }
    public void setFraudFlag(boolean fraudFlag) { this.fraudFlag = fraudFlag; }

    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }

    public String getDeviceUsed() { return deviceUsed; }
    public void setDeviceUsed(String deviceUsed) { this.deviceUsed = deviceUsed; }

    public String getNetworkSliceId() { return networkSliceId; }
    public void setNetworkSliceId(String networkSliceId) { this.networkSliceId = networkSliceId; }

    public int getLatency() { return latency; }
    public void setLatency(int latency) { this.latency = latency; }

    public int getBandwidth() { return bandwidth; }
    public void setBandwidth(int bandwidth) { this.bandwidth = bandwidth; }

    public int getPinCode() { return pinCode; }
    public void setPinCode(int pinCode) { this.pinCode = pinCode; }
}
