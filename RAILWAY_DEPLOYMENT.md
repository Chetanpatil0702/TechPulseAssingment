# Railway Deployment Guide for Demo16

## Prerequisites
- Railway account (sign up at [railway.app](https://railway.app))
- GitHub repository with your code
- Railway CLI (optional but recommended)

## Step 1: Prepare Your Repository

### 1.1 Push to GitHub
```bash
git add .
git commit -m "Prepare for Railway deployment"
git push origin main
```

### 1.2 Verify Configuration Files
- ✅ `railway.json` - Railway deployment configuration
- ✅ `application.properties` - Updated with Railway environment variables
- ✅ `pom.xml` - Includes Spring Boot Actuator for health checks

## Step 2: Deploy on Railway

### 2.1 Create New Project
1. Go to [railway.app](https://railway.app)
2. Click "New Project"
3. Select "Deploy from GitHub repo"
4. Choose your repository

### 2.2 Add MySQL Database
1. In your Railway project dashboard
2. Click "New" → "Database" → "Add MySQL"
3. Railway will automatically create a MySQL database

### 2.3 Configure Environment Variables
In your Railway project settings, add these environment variables:

```
DATABASE_URL=jdbc:mysql://mysql.railway.internal:3306/railway
DATABASE_USERNAME=root
DATABASE_PASSWORD=[Your Railway MySQL Password]
PORT=8080
```

**Note**: Railway will provide the actual database credentials in your MySQL service settings.

### 2.4 Deploy
1. Railway will automatically detect your Spring Boot application
2. It will build and deploy your app
3. Your app will be available at the provided Railway URL

## Step 3: Verify Deployment

### 3.1 Health Check
Visit: `https://your-app.railway.app/actuator/health`

### 3.2 API Documentation
Visit: `https://your-app.railway.app/swagger-ui.html`

### 3.3 Test Endpoints
```bash
# Test login endpoint
curl -X POST https://your-app.railway.app/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"your-username","password":"your-password"}'
```

## Step 4: Database Setup

### 4.1 Initial Data
You may need to create initial users, roles, and permissions. You can do this by:

1. **Using the API endpoints** (if you have admin access)
2. **Creating a data initialization script**
3. **Using Railway's database console**

### 4.2 Create Initial Admin User
```sql
-- Insert into your database via Railway's database console
INSERT INTO users (username, email, password_hash, created_at, updated_at) 
VALUES ('admin', 'admin@example.com', '$2a$10$encrypted_password', NOW(), NOW());
```

## Step 5: Custom Domain (Optional)

1. In Railway project settings
2. Go to "Settings" → "Domains"
3. Add your custom domain
4. Configure DNS records as instructed

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Check environment variables in Railway
   - Verify MySQL service is running
   - Check database credentials

2. **Build Failed**
   - Ensure Java 21 is specified in pom.xml
   - Check for compilation errors
   - Verify all dependencies are available

3. **App Won't Start**
   - Check Railway logs
   - Verify PORT environment variable
   - Check application.properties configuration

### Viewing Logs
```bash
# Using Railway CLI
railway logs

# Or view in Railway dashboard
# Go to your project → Deployments → View logs
```

## Environment Variables Reference

| Variable | Description | Default |
|----------|-------------|---------|
| `DATABASE_URL` | MySQL connection URL | Railway internal URL |
| `DATABASE_USERNAME` | Database username | root |
| `DATABASE_PASSWORD` | Database password | Railway generated |
| `PORT` | Application port | 8080 |

## Security Notes

- Never commit database passwords to version control
- Use Railway's environment variables for sensitive data
- Consider using Railway's secrets management for production
- Enable HTTPS (Railway provides this by default)

## Monitoring

Railway provides:
- Application logs
- Performance metrics
- Health check monitoring
- Automatic restarts on failure

Your app is now deployed and ready to use! 🚀
