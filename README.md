# Nexus Onboarding Platform

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green)
![React](https://img.shields.io/badge/React-18-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![GCP](https://img.shields.io/badge/Google_Cloud-Deployed-red)

**Nexus Onboarding** is a full-stack enterprise application designed to streamline the employee onboarding process. Built with a modern microservices-ready architecture, it features a secure Spring Boot backend and a responsive React frontend, demonstrating strict environment separation and cloud-native deployment practices.

🔗 **[Live Demo](https://nexus-frontend-2026-37af6.web.app)** _(Click to view)_

---

## 📸 Screenshots

*(Screenshots coming soon)*

---

## 🛠 Tech Stack

### **Backend**
* **Language:** Java 25
* **Framework:** Spring Boot 3.3.0
* **Database:** PostgreSQL (Hibernate/JPA)
* **Security:** Environment-based configuration (Strict Local/Prod separation)
* **Build Tool:** Maven

### **Frontend**
* **Framework:** React (Vite)
* **Runtime:** Node.js 22
* **Styling:** CSS Modules / Tailwind
* **State Management:** React Hooks

### **DevOps & Cloud**
* **Backend Hosting:** Google Cloud Run (Containerized via Docker)
* **Frontend Hosting:** Firebase Hosting
* **Database:** Neon Tech (Serverless PostgreSQL)
* **CI/CD:** GitHub Actions (Automated build & deploy pipeline)

---

## 🚀 Key Features
* **Environment Parity:** Engineered with strict separation of concerns; Local environment uses local Postgres, Production uses Serverless Neon DB via SSL.
* **Automated CI/CD:** Commits to `main` automatically build, test, and deploy to Firebase and Cloud Run.
* **Secure Configuration:** Sensitive credentials are managed via Cloud Secret Injection (GCP) and are never hardcoded in the codebase.
* **Scalable Architecture:** Backend scales to zero when idle (Serverless) to optimize resource usage.

---

## ⚙️ Local Development Setup

Follow these steps to run the project locally.

### **Prerequisites**
* Java 25 (JDK)
* Node.js 22+
* PostgreSQL installed locally

### **1. Database Setup**
Ensure your local Postgres server is running, then create the local database:
```bash
createdb nexus_local
```

### **2. Backend Setup (Spring Boot)**
The application uses a **profile-based** configuration to keep local secrets safe.

1.  Navigate to the backend folder:
    ```bash
    cd backend
    ```
2.  Create a local configuration file to override production settings:
    * Create a file at: `src/main/resources/application-local.properties`
    * Add your local credentials (ensure this file is in `.gitignore`):
    ```properties
    DB_URL=jdbc:postgresql://localhost:5432/nexus_local
    DB_USER=postgres
    DB_PASSWORD=your_local_password
    ```
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```
    *The server will start on `http://localhost:8080`.*

### **3. Frontend Setup (React)**
1.  Navigate to the frontend folder:
    ```bash
    cd frontend
    ```
2.  Install dependencies and start:
    ```bash
    npm install
    npm run dev
    ```
    *The UI will launch at `http://localhost:5173`.*

---

## ☁️ Architecture & Deployment

This project uses a split-stack architecture:

* **Frontend:** The React SPA is hosted on **Firebase**, providing global CDN performance.
* **Backend:** The Spring Boot API runs on **Google Cloud Run**, utilizing a Docker container built via GitHub Actions.
* **Database:** **Neon (Postgres)** provides a serverless database tier with enforced SSL security (`?sslmode=require`) for production data.

---

## 👤 Author

**Max Blaschek**
* [LinkedIn](https://www.linkedin.com/in/maxblaschek/)
* [GitHub](https://github.com/MaxB-Coder)
