# Note Calendar 
A simple calendar app to help you organize your goals, events, and todos—all in one place!  
# What Does It Do?
- **Track Your Goals**: Connect your personal goals with events and daily todos. 
- **Manage Your Time**: Add, view, and edit events and tasks on a weekly calendar.  
- **Stay Organized**: Works smoothly across months to keep your schedule clear.

![Image](https://github.com/user-attachments/assets/09b10042-1360-44de-98c1-8ee34817c0a6)
***
# How to use the app?
1. **Get your code**: Clone the whole repository
2. Copy *.env.example* to *.env* in the root folder and fill in your settings
```
VITE_API_URL=http://localhost:8080
MYSQL_ROOT_PASSWORD=your_password
MYSQL_DATABASE=noteschedule
DB_PASSWORD=your_password
ALLOWED_ORIGIN=http://localhost:5173
```
3. Build and run with Docker Compose (Make sure you have [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running.). Run the following command at the folder.
```
docker-compose up --build
```
4. Go to http://localhost:5173 in your browser and start planning!
***
# How this app was build?

- **Frontend**: Vue.js with Vite (fast and modern) and Nginx (serves the app).
- **Backend**: Spring Boot with MyBatis (handles your data).
- **Database**: MySQL (stores everything).
- **Docker**: Makes it easy to run anywhere with Docker Compose.
***
# How I Solved Challenges
- **Cross-Month Bug**: Adjusted SQL queries in MyBatis to handle DATETIME fields, ensuring events display correctly across months.
- **CORS Issue**: Configured Spring Boot to allow cross-origin requests from the frontend domain.
- **Database Connection**: Fixed connectivity by aligning environment variables and Docker settings.
- **Unified API Response**: Designed a consistent return structure (`Result`) in Spring Boot with `code`, `data`, and `message` fields, improving frontend error handling and data consistency across all endpoints.
