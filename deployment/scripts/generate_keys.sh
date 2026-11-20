#!/bin/bash

ENVS=("DEV" "STAGING" "PROD")
KEY_DIR="./deployment/keys"
mkdir -p $KEY_DIR

echo "🔑 TradingViewTask Deployment Key Generator"
echo "--------------------------------------"

for ENV in "${ENVS[@]}"; do
    FILE_NAME="$KEY_DIR/id_ed25519_${ENV,,}"
    SECRET_NAME="${ENV}_SSH_PRIVATE_KEY"

    if [ ! -f "$FILE_NAME" ]; then
        echo "Creating key for $ENV..."
        ssh-keygen -t ed25519 -C "deploy@tradingviewtask-$ENV" -f "$FILE_NAME" -N ""

        echo ""
        echo "✅ Generated keys for $ENV:"
        echo "   1. Private Key (Add to GitHub Secret '$SECRET_NAME'):"
        cat "$FILE_NAME"
        echo ""
        echo "   2. Public Key (Add to $ENV server's ~/.ssh/authorized_keys):"
        cat "$FILE_NAME.pub"
        echo "--------------------------------------"
    else
        echo "ℹ️ Key for $ENV already exists at $FILE_NAME"
    fi
done

if ! grep -q "deployment/keys" .gitignore 2>/dev/null; then
    echo "" >> .gitignore
    echo "# Deployment keys" >> .gitignore
    echo "deployment/keys" >> .gitignore
fi
