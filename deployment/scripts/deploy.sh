#!/bin/bash
set -e

APP_NAME=${1:-"app"}
ENV=${2:-"staging"}
IMAGE_TAG=$(git rev-parse --short HEAD 2>/dev/null || echo "latest")
REGISTRY="tradingviewtask"
CONTAINER_NAME="tradingviewtask-$APP_NAME"

echo "🚀 Deploying $APP_NAME to $ENV..."

if [ ! -f local.properties ]; then
    echo "⚠️ Warning: local.properties file missing. Fallback properties will be used."
fi

echo "📦 Step 1: Building production image..."
docker build -t $REGISTRY/$APP_NAME-wasm:$IMAGE_TAG \
             -t $REGISTRY/$APP_NAME-wasm:latest \
             --build-arg APP_NAME=$APP_NAME \
             -f deployment/docker/Dockerfile .

echo "🔍 Step 2: Validating build health..."
docker run --rm -d --name "${CONTAINER_NAME}-verify" -p 8099:8080 $REGISTRY/$APP_NAME-wasm:$IMAGE_TAG
sleep 5
if ./deployment/scripts/healthcheck.sh "http://localhost:8099/"; then
    echo "✅ Validation passed."
    docker stop "${CONTAINER_NAME}-verify"
else
    echo "❌ Validation failed. Check container logs."
    docker logs "${CONTAINER_NAME}-verify"
    docker stop "${CONTAINER_NAME}-verify"
    exit 1
fi

echo "🛳️ Step 3: Deploying to runtime..."
docker-compose -f deployment/docker-compose.yml up -d app

echo "🎉 Deployment Successful: $APP_NAME is now live."
