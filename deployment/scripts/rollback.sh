#!/bin/bash
set -e

APP_NAME=${1:-"app"}

echo "🔄 Starting rollback for $APP_NAME..."

docker-compose -f deployment/docker-compose.yml stop app
docker pull tradingviewtask/$APP_NAME-wasm:stable
docker tag tradingviewtask/$APP_NAME-wasm:stable tradingviewtask/$APP_NAME-wasm:latest
docker-compose -f deployment/docker-compose.yml up -d app

echo "✅ Rollback complete for $APP_NAME."
