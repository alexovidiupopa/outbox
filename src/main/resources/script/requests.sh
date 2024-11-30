#!/bin/bash

# Configuration
BASE_URL="http://localhost:8080/api/orders"
TOTAL_REQUESTS=1000
CUSTOMER_ID=12345

echo "Sending $TOTAL_REQUESTS requests to $BASE_URL..."

# Track start time
START_TIME=$(date +%s)

# Loop to send requests
for i in $(seq 1 $TOTAL_REQUESTS); do
RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL" -G --data-urlencode "customerId=$CUSTOMER_ID")

if [ "$RESPONSE" -eq 201 ]; then
echo "Request $i: SUCCESS"
else
echo "Request $i: FAILURE - HTTP $RESPONSE"
fi
done

# Track end time
END_TIME=$(date +%s)

# Calculate and print total time taken
TOTAL_TIME=$((END_TIME - START_TIME))
echo "All requests completed in $TOTAL_TIME seconds.