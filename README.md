# Data-Farmer

A robust, production-ready **Financial Data Aggregator** built with Spring Boot and MongoDB. Data-Farmer provides RESTful APIs and a dashboard for uploading, analyzing, and visualizing financial transaction data. Designed for easy deployment (e.g., on Railway), it supports CSV/JSON uploads, analytics, and customizable dashboards.

---

## 🚀 Features

- **Upload Transactions**: Import financial data via CSV or JSON (file or path).
- **RESTful API**: Endpoints for uploading, retrieving, and analyzing transactions.
- **Dashboard**: Visualize analytics and summaries at `/dashboard`.
- **Swagger UI**: Interactive API docs and file upload at `/swagger-ui.html`.
- **MongoDB Integration**: Persistent, scalable data storage.
- **Configurable**: Environment-based configuration for local and cloud deployment.

---

## 🗂️ Project Structure

```
fin-data-service/
├── src/
│   ├── main/
│   │   ├── java/com/ADI_Farmer/fin_data_service/
│   │   │   ├── controller/         # REST and web controllers
│   │   │   ├── model/              # Data models (Transaction, Analytics, etc.)
│   │   │   ├── repository/         # MongoDB repositories
│   │   │   ├── service/            # Business logic
│   │   │   └── FinDataServiceApplication.java
│   │   ├── resources/
│   │   │   ├── application.yml     # App configuration
│   │   │   ├── templates/          # Dashboard HTML
│   │   │   └── static/             # Static assets
│   └── test/                       # Unit and integration tests
├── sample_transactions.csv         # Example data
├── pom.xml                         # Maven build file
└── README.md
```

---

## 🛠️ Getting Started

### Prerequisites

- Java 17+ (recommended: Java 24)
- Maven 3.8+
- MongoDB (local or cloud, e.g., MongoDB Atlas)

### Local Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/aryanpatil2703/data-farmer.git
   cd data-farmer/fin-data-service
   ```

2. **Configure MongoDB**
   - Set your MongoDB URI in `src/main/resources/application.yml` or as an environment variable:
     ```
     MONGODB_URI=mongodb://localhost:27017/financial_data
     ```

3. **Build and Run**
   ```bash
   mvn clean package -DskipTests
   java -jar target/fin-data-service-0.0.1-SNAPSHOT.jar
   ```

4. **Access the App**
   - Dashboard: [http://localhost:8082/dashboard](http://localhost:8082/dashboard)
   - Swagger UI: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

---

## 📦 API & Endpoints

- `POST /api/transactions/upload/csv` — Upload CSV file
- `POST /api/transactions/upload/json` — Upload JSON file
- `POST /api/transactions/upload/csv/path` — Upload CSV by server file path
- `POST /api/transactions/upload/json/path` — Upload JSON by server file path
- `GET /api/transactions` — List all transactions
- `GET /api/analytics/summary` — Get analytics summary

See full interactive docs at `/swagger-ui.html`.

---

## 🌐 Deployment

### Railway (Recommended)

1. Push your code to GitHub.
2. Create a new project on [Railway](https://railway.app).
3. Add a MongoDB service.
4. Set environment variables (`MONGODB_URI`, `PORT`, etc.).
5. Use the following build and start commands:
   ```bash
   mvn clean package -DskipTests
   java -jar target/fin-data-service-0.0.1-SNAPSHOT.jar
   ```

See `RAILWAY_DEPLOYMENT_GUIDE.md` for detailed steps.

---

## 🧰 Technologies Used

- Java, Spring Boot
- MongoDB
- Maven
- Swagger/OpenAPI
- Railway (deployment)

---

## 🤝 Contributing

Contributions are welcome! Please open issues or submit pull requests for new features, bug fixes, or improvements.

---

## 📄 License

This project is licensed under the MIT License.

---

## 📬 Contact

Maintainer: [aryanpatil2703](https://github.com/aryanpatil2703)

---