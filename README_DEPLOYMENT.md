# TradingViewTask Deployment Guide (WASM)

This repository contains a production-grade deployment pipeline for the Kotlin Multiplatform (Compose) WebAssembly application.

## 🚀 Quick Start (Local Docker)

1. **Configure Environment Properties**:
   Ensure the relevant properties file exists in `deployment/environments/` (e.g., `development.properties`, `staging.properties`, or `production.properties`).

2. **Build and Run**:
   ```bash
   # Build app for an environment (e.g., staging)
   ./deployment/scripts/build.sh app staging
   
   # Start the container
   docker-compose -f deployment/docker-compose.yml up -d
   ```

3. **Verify**:
   - Web App: [http://localhost:8080](http://localhost:8080)
   - Health Check: `curl http://localhost:8080/`

---

## ☁️ Publishing to Render

You can publish to Render using either **Automated Blueprint (`render.yaml`)** or **Manual Setup via the Render Dashboard**.

### Option A: Automated via Render Blueprint (`render.yaml`)

1. Push your repository to GitHub or GitLab.
2. Log into [Render Dashboard](https://dashboard.render.com).
3. Click **New +** > **Blueprint**.
4. Connect your repository containing `render.yaml`.
5. Render will automatically detect `render.yaml` and configure the Web Service with Docker runtime, build context (`.`), Dockerfile path (`deployment/docker/Dockerfile`), and required build arguments (`APP_NAME=app`, `BUILD_ENV=production`).
6. Click **Apply**.

---

### Option B: Manual Setup via Render Dashboard

1. Log into [Render Dashboard](https://dashboard.render.com).
2. Click **New +** > **Web Service**.
3. Connect your repository.
4. Configure the service settings:
   - **Name**: `tradingviewtask-app`
   - **Language / Runtime**: `Docker`
   - **Build Context**: `.` (Root directory)
   - **Dockerfile Path**: `deployment/docker/Dockerfile`
   - **Instance Type**: Select **Starter** or higher (WASM compilation requires sufficient RAM, at least 2GB-4GB).
5. Expand **Advanced** > **Docker Build Arguments**:
   - Add `APP_NAME`: `app`
   - Add `BUILD_ENV`: `production` (or `staging`)
6. Add **Environment Variables**:
   - `PORT`: `8080`
7. Click **Create Web Service**.

---

## 🏗️ Architecture Summary

### 1. Compile-time Configuration (Multi-Environment)
Configuration is managed via environment-specific `.properties` files located in `deployment/environments/`.
- During the Docker build, a `BUILD_ENV` argument is passed (`development`, `staging`, or `production`).
- The `Dockerfile` copies the corresponding `.properties` file to `local.properties` at the project root.

### 2. Security & Performance
- **Nginx Alpine**: Minimal footprint and attack surface.
- **Compression**: Gzip/static serving for WASM, JS, and CSS files.
- **SPA Support**: Nginx is configured to handle client-side routing by falling back to `index.html`.
