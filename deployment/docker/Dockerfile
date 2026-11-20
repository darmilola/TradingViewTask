# --- Stage 1: Build ---
FROM eclipse-temurin:21-jdk-jammy AS build

# Install necessary build dependencies
RUN apt-get update && apt-get install -y \
    git unzip wget curl libatomic1 libtinfo5 libc6 libc6-dev libstdc++6 \
    && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /home/gradle/project
COPY . .

# Force Kotlin to run in-process and increase memory
RUN echo "kotlin.compiler.execution.strategy=in-process" >> gradle.properties && \
    echo "org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=1g -XX:+UseSerialGC" >> gradle.properties && \
    echo "kotlin.native.ignoreDisabledTargets=true" >> gradle.properties && \
    # IMPORTANT: Force disable wasm optimizer globally via properties file
    echo "kotlin.wasm.optimizer.enabled=false" >> gradle.properties

# IMPORTANT: Ensure clean state for build logic and Gradle cache
RUN mkdir -p /root/.gradle && chmod -R 777 /root/.gradle && \
    ./gradlew :build_logics:clean :build_logics:assemble --no-daemon

ARG APP_NAME=app
ARG BUILD_ENV=production

# Run the build
RUN ./gradlew :apps:${APP_NAME}:composeApp:wasmJsBrowserDistribution \
    --stacktrace \
    --no-daemon \
    -Pkotlin.incremental=false \
    -Dorg.gradle.workers.max=1 \
    -Penv=${BUILD_ENV} 2>&1

# Collect files
RUN mkdir -p /home/gradle/project/final_dist && \
    cp -r apps/${APP_NAME}/composeApp/build/dist/wasmJs/productionExecutable/. /home/gradle/project/final_dist/ || true && \
    find . -type f \( -name "*.js" -o -name "*.mjs" -o -name "*.wasm" -o -name "*.html" \) \
    -exec cp -n {} /home/gradle/project/final_dist/ \;

# --- Stage 2: Production Runtime ---
FROM nginx:1.25-alpine-slim
RUN rm -rf /usr/share/nginx/html/*
COPY deployment/docker/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /home/gradle/project/final_dist/ /usr/share/nginx/html/

RUN if [ -f /usr/share/nginx/html/composeApp.mjs ]; then \
        mv /usr/share/nginx/html/composeApp.mjs /usr/share/nginx/html/composeApp.js; \
    fi

RUN touch /var/run/nginx.pid && \
    chown -R nginx:nginx /var/run/nginx.pid /usr/share/nginx/html /var/cache/nginx /var/log/nginx /etc/nginx

USER nginx
EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]
