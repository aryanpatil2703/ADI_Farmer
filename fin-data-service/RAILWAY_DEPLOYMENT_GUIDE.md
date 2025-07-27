# Railway Deployment Guide for Financial Data Aggregator

## 🚀 Why Railway?

Railway is an excellent platform for deploying Spring Boot applications because it:

- ✅ **Supports full-stack applications** (not just serverless)
- ✅ **Built-in MongoDB support** with easy setup
- ✅ **Persistent storage** and continuous processes
- ✅ **Automatic deployments** from GitHub
- ✅ **Generous free tier** with good limits
- ✅ **Easy environment variable management**
- ✅ **Built-in monitoring** and logs

## 📋 Prerequisites

1. **GitHub Account** - Your code must be on GitHub
2. **Railway Account** - Sign up at [railway.app](https://railway.app)
3. **MongoDB Atlas** (optional) - Or use Railway's MongoDB service

## 🛠️ Step-by-Step Deployment

### Step 1: Prepare Your Repository

1. **Ensure your code is on GitHub:**
   ```bash
   git add .
   git commit -m "Prepare for Railway deployment"
   git push origin main
   ```

2. **Verify your project structure:**
   ```
   fin-data-service/
   ├── src/
   ├── pom.xml
   ├── sample_transactions.csv
   └── README.md
   ```

### Step 2: Set Up Railway Project

1. **Go to [railway.app](https://railway.app) and sign in**
2. **Click "New Project"**
3. **Select "Deploy from GitHub repo"**
4. **Choose your repository**
5. **Select the `fin-data-service` directory**

### Step 3: Add MongoDB Service

1. **In your Railway project, click "New Service"**
2. **Select "Database" → "MongoDB"**
3. **Choose a name (e.g., "financial-data-db")**
4. **Click "Deploy"**

### Step 4: Configure Your Spring Boot App

1. **Go back to your main service (Spring Boot app)**
2. **Click on "Variables" tab**
3. **Add these environment variables:**

   ```bash
   MONGODB_URI=mongodb://localhost:27017/financial_data
   SPRING_PROFILES_ACTIVE=railway
   PORT=8082
   JAVA_OPTS=-Xmx512m -Xms256m
   ```

4. **Connect to MongoDB:**
   - Click on your MongoDB service
   - Copy the connection string
   - Update `MONGODB_URI` in your Spring Boot service variables

### Step 5: Configure Build Settings

1. **In your Spring Boot service, go to "Settings"**
2. **Set the following:**

   **Build Command:**
   ```bash
   mvn clean package -DskipTests
   ```

   **Start Command:**
   ```bash
   java -jar target/fin-data-service-0.0.1-SNAPSHOT.jar
   ```

   **Health Check Path:**
   ```
   /api/health
   ```

### Step 6: Deploy

1. **Railway will automatically detect your changes and deploy**
2. **Monitor the build logs** for any issues
3. **Wait for deployment to complete**

## 🔧 Configuration Files

### Create `railway.toml` (Optional)

```toml
[build]
builder = "nixpacks"

[deploy]
startCommand = "java -jar target/fin-data-service-0.0.1-SNAPSHOT.jar"
healthcheckPath = "/api/health"
healthcheckTimeout = 300
restartPolicyType = "on_failure"
```

### Update `application.yml`

Add this to your existing `application.yml`:

```yaml
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:local}

---
spring:
  config:
    activate:
      on-profile: railway
  data:
    mongodb:
      uri: ${MONGODB_URI}
  jmx:
    enabled: false
  main:
    banner-mode: off

server:
  port: ${PORT:8082}

logging:
  level:
    com.ADI_Farmer: INFO
    org.springframework: WARN
```

## 🧪 Testing Your Deployment

Once deployed, test these endpoints:

1. **Health Check:**
   ```
   https://your-app.railway.app/api/health
   ```

2. **Swagger UI:**
   ```
   https://your-app.railway.app/swagger-ui.html
   ```

3. **Dashboard:**
   ```
   https://your-app.railway.app/dashboard
   ```

4. **API Endpoints:**
   ```
   https://your-app.railway.app/api/transactions
   https://your-app.railway.app/api/analytics/summary
   ```

## 📊 Monitoring and Logs

1. **View logs in Railway dashboard**
2. **Monitor resource usage**
3. **Check deployment status**
4. **View MongoDB metrics**

## 🔄 Continuous Deployment

Railway automatically:
- ✅ Deploys on every push to main branch
- ✅ Creates preview deployments for pull requests
- ✅ Rolls back on deployment failures

## 💰 Pricing

**Free Tier:**
- $5 credit monthly
- 500 hours of runtime
- 1GB RAM per service
- Shared CPU

**Paid Plans:**
- Pay-as-you-go pricing
- More resources available
- Custom domains
- Team collaboration

## 🔍 Troubleshooting

### Common Issues:

1. **Build Failures:**
   ```bash
   # Check build logs in Railway dashboard
   # Verify Maven dependencies
   # Check Java version compatibility
   ```

2. **Database Connection Issues:**
   - Verify MongoDB connection string
   - Check if MongoDB service is running
   - Ensure proper network access

3. **Memory Issues:**
   - Optimize JVM settings
   - Reduce dependency size
   - Consider upgrading plan

4. **Port Issues:**
   - Ensure `PORT` environment variable is set
   - Check if port 8082 is available

### Debug Commands:

```bash
# Check Railway CLI (if installed)
railway login
railway status
railway logs

# Local testing
mvn clean package -DskipTests
java -jar target/fin-data-service-0.0.1-SNAPSHOT.jar
```

## 🚀 Advanced Features

### Custom Domain

1. **Go to your service settings**
2. **Click "Custom Domains"**
3. **Add your domain**
4. **Update DNS records**

### Environment Variables

Railway makes it easy to manage different environments:

```bash
# Development
RAILWAY_ENVIRONMENT=development

# Production
RAILWAY_ENVIRONMENT=production
```

### Scaling

1. **Go to service settings**
2. **Adjust CPU and memory allocation**
3. **Enable auto-scaling if needed**

## 📈 Performance Optimization

1. **JVM Tuning:**
   ```bash
   JAVA_OPTS=-Xmx512m -Xms256m -XX:+UseG1GC
   ```

2. **MongoDB Optimization:**
   - Use indexes for frequently queried fields
   - Implement connection pooling
   - Monitor query performance

3. **Application Optimization:**
   - Enable compression
   - Use caching where appropriate
   - Optimize static asset delivery

## 🔒 Security

1. **Environment Variables:**
   - Never commit sensitive data
   - Use Railway's secure variable storage

2. **Database Security:**
   - Use strong passwords
   - Enable network access controls
   - Regular backups

3. **Application Security:**
   - Enable HTTPS
   - Implement proper CORS policies
   - Validate all inputs

## 🎯 Next Steps

After successful deployment:

1. **Set up monitoring** and alerting
2. **Configure backups** for MongoDB
3. **Set up CI/CD** pipelines
4. **Implement logging** and error tracking
5. **Add custom domain** and SSL
6. **Set up team access** if needed

## 🆘 Support

- **Railway Documentation:** [docs.railway.app](https://docs.railway.app)
- **Railway Discord:** [discord.gg/railway](https://discord.gg/railway)
- **GitHub Issues:** For application-specific issues

## 🎉 Congratulations!

Your Financial Data Aggregator is now deployed on Railway! 

**Your application URLs:**
- **Dashboard:** `https://your-app.railway.app/dashboard`
- **API:** `https://your-app.railway.app/api`
- **Swagger UI:** `https://your-app.railway.app/swagger-ui.html`

**Next steps:**
1. Test all endpoints
2. Upload sample data using Swagger UI
3. Verify dashboard functionality
4. Share your deployment URL for evaluation 