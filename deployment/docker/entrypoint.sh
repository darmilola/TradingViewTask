#!/bin/sh

API_BASE_URL=${API_BASE_URL:-"http://localhost:8080"}
APP_ENV=${APP_ENV:-"production"}
ENABLE_LOGGING=${ENABLE_LOGGING:-"false"}

cat <<EOF > /usr/share/nginx/html/config.js
window.APP_CONFIG = {
    API_BASE_URL: "$API_BASE_URL",
    APP_ENV: "$APP_ENV",
    ENABLE_LOGGING: $ENABLE_LOGGING,
    APP_VERSION: "$APP_VERSION",
    BUILD_TIME: "$(date)"
};
EOF

echo "Runtime config generated at /usr/share/nginx/html/config.js"

exec nginx -g "daemon off;"
