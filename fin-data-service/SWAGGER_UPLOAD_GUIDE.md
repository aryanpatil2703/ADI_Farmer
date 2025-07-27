# Swagger UI File Upload Guide

## Overview
This guide explains how to use Swagger UI to upload CSV and JSON files containing transaction data to your MongoDB database.

## Accessing Swagger UI

1. Start your Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

2. Open your browser and navigate to:
   ```
   http://localhost:8082/swagger-ui.html
   ```

## Available Upload Endpoints

### 1. CSV File Upload (Recommended for Swagger UI)
- **Endpoint**: `POST /api/transactions/upload/csv`
- **Content-Type**: `multipart/form-data`
- **Parameter**: `file` (CSV file)

### 2. JSON File Upload
- **Endpoint**: `POST /api/transactions/upload/json`
- **Content-Type**: `multipart/form-data`
- **Parameter**: `file` (JSON file)

### 3. CSV Upload by File Path (Server-side)
- **Endpoint**: `POST /api/transactions/upload/csv/path`
- **Content-Type**: `application/x-www-form-urlencoded`
- **Parameter**: `path` (string - file path on server)

### 4. JSON Upload by File Path (Server-side)
- **Endpoint**: `POST /api/transactions/upload/json/path`
- **Content-Type**: `application/x-www-form-urlencoded`
- **Parameter**: `path` (string - file path on server)

## How to Upload Files Using Swagger UI

### Step 1: Navigate to the Upload Endpoint
1. In Swagger UI, find the "Financial Data API" section
2. Locate the upload endpoint you want to use (e.g., `POST /api/transactions/upload/csv`)

### Step 2: Click "Try it out"
1. Click the "Try it out" button next to the endpoint
2. This will expand the endpoint and show input fields

### Step 3: Upload Your File
1. For file upload endpoints:
   - Click the "Choose File" button in the `file` parameter field
   - Select your CSV or JSON file from your computer
   - The file will be uploaded to the server

2. For path-based endpoints:
   - Enter the full file path in the `path` parameter field
   - Example: `/path/to/your/file.csv`

### Step 4: Execute the Request
1. Click the "Execute" button
2. Swagger UI will send the request to your API
3. You'll see the response with the uploaded transactions

## Sample CSV File Format

Your CSV file should have the following structure:
```csv
transactionId,senderAccountId,receiverAccountId,amount,type,timestamp,status,fraudFlag,latitude,longitude,deviceUsed,networkSliceId,latency,bandwidth,pinCode
TXN001,ACC001,ACC002,1500.50,TRANSFER,2024-01-15T10:30:00,COMPLETED,false,40.7128,-74.0060,Mobile App,NS001,50,100,10001
```

### Required Fields:
- `transactionId`: Unique transaction identifier
- `senderAccountId`: Sender's account ID
- `receiverAccountId`: Receiver's account ID
- `amount`: Transaction amount (numeric)
- `type`: Transaction type (e.g., TRANSFER, PAYMENT)
- `timestamp`: Transaction timestamp (ISO format)
- `status`: Transaction status (e.g., COMPLETED, FAILED)
- `fraudFlag`: Boolean flag for fraud detection
- `latitude`: Geographic latitude
- `longitude`: Geographic longitude
- `deviceUsed`: Device used for transaction
- `networkSliceId`: Network slice identifier
- `latency`: Network latency in milliseconds
- `bandwidth`: Network bandwidth in Mbps
- `pinCode`: PIN code (numeric)

## Sample JSON File Format

Your JSON file should contain an array of transaction objects:
```json
[
  {
    "transactionId": "TXN001",
    "senderAccountId": "ACC001",
    "receiverAccountId": "ACC002",
    "amount": 1500.50,
    "type": "TRANSFER",
    "timestamp": "2024-01-15T10:30:00",
    "status": "COMPLETED",
    "fraudFlag": false,
    "latitude": "40.7128",
    "longitude": "-74.0060",
    "deviceUsed": "Mobile App",
    "networkSliceId": "NS001",
    "latency": 50,
    "bandwidth": 100,
    "pinCode": 10001
  }
]
```

## Testing with Sample Data

A sample CSV file (`sample_transactions.csv`) is included in the project root. You can use this to test the upload functionality:

1. In Swagger UI, navigate to `POST /api/transactions/upload/csv`
2. Click "Try it out"
3. Click "Choose File" and select `sample_transactions.csv`
4. Click "Execute"

## Response Format

Successful uploads will return an array of `Transaction` objects:
```json
[
  {
    "id": "generated-mongodb-id",
    "transactionId": "TXN001",
    "senderAccountId": "ACC001",
    "receiverAccountId": "ACC002",
    "amount": 1500.50,
    "type": "TRANSFER",
    "timestamp": "2024-01-15T10:30:00",
    "status": "COMPLETED",
    "fraudFlag": false,
    "latitude": "40.7128",
    "longitude": "-74.0060",
    "deviceUsed": "Mobile App",
    "networkSliceId": "NS001",
    "latency": 50,
    "bandwidth": 100,
    "pinCode": 10001
  }
]
```

## Error Handling

The API will return appropriate error responses:
- **400 Bad Request**: Invalid file format or malformed data
- **500 Internal Server Error**: Server-side processing errors

## Verification

After uploading, you can verify the data was stored by:
1. Using the `GET /api/transactions` endpoint to retrieve all transactions
2. Checking your MongoDB database directly
3. Viewing the dashboard at `http://localhost:8082/dashboard`

## Tips

1. **File Size**: Keep your CSV/JSON files reasonably sized for better performance
2. **Data Validation**: Ensure your data matches the expected format
3. **Headers**: CSV files should include headers matching the field names
4. **Encoding**: Use UTF-8 encoding for your files
5. **Backup**: Always backup your data before bulk uploads

## Troubleshooting

### Common Issues:
1. **File not found**: Ensure the file path is correct and accessible
2. **Invalid format**: Check that your CSV/JSON matches the expected structure
3. **Permission errors**: Ensure the application has read access to the file
4. **MongoDB connection**: Verify MongoDB is running and accessible

### Debug Steps:
1. Check the application logs for detailed error messages
2. Verify the file format and content
3. Test with the sample file first
4. Ensure all required fields are present and properly formatted 