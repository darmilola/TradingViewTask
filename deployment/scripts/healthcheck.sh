#!/bin/bash
set -e

URL=${1:-"http://localhost:8080/"}

echo "Checking health of TradingViewTask at $URL..."

MAX_RETRIES=5
COUNT=0

while [ $COUNT -lt $MAX_RETRIES ]; do
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$URL")
  if [ "$STATUS" -eq 200 ]; then
    echo "✅ Application is healthy!"
    exit 0
  fi
  echo "Still waiting... (Attempt $((COUNT+1))/$MAX_RETRIES)"
  sleep 5
  COUNT=$((COUNT+1))
done

echo "❌ Application health check failed with status $STATUS"
exit 1
