#!/bin/bash
set -e

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

APP_NAME=${1:-"app"}
BUILD_ENV=${2:-"production"}

echo -e "${GREEN}🚀 Starting TradingViewTask Build Suite [Env: $BUILD_ENV]${NC}"

build_app() {
    local APP=$1
    local ENV=$2
    echo -e "${GREEN}📦 Building Docker image for: $APP ($ENV)${NC}"
    docker build -t tradingviewtask/$APP-wasm:$ENV \
        --build-arg APP_NAME=$APP \
        --build-arg BUILD_ENV=$ENV \
        -f deployment/docker/Dockerfile .

    if [ "$ENV" == "production" ]; then
        docker tag tradingviewtask/$APP-wasm:production tradingviewtask/$APP-wasm:latest
    fi
}

build_app "$APP_NAME" "$BUILD_ENV"

echo -e "${GREEN}✅ Build Complete!${NC}"
echo -e "${YELLOW}Usage Examples:${NC}"
echo "  ./deployment/scripts/build.sh app staging    # Build app for staging"
echo "  ./deployment/scripts/build.sh app production # Build app for production"
