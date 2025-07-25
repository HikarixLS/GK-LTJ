#!/bin/bash

echo "=================================================="
echo "   Product Management System - Startup Script"
echo "=================================================="
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "[ERROR] Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    echo "Download from: https://adoptium.net/"
    echo
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "[ERROR] Maven is not installed or not in PATH"
    echo "Please install Maven 3.6+ or use the Maven Wrapper"
    echo "Download from: https://maven.apache.org/download.cgi"
    echo
    exit 1
fi

echo "[INFO] Java and Maven are available"
echo

# Clean and compile the project
echo "[INFO] Building the project..."
mvn clean compile
if [ $? -ne 0 ]; then
    echo "[ERROR] Build failed!"
    exit 1
fi

echo "[INFO] Build successful!"
echo

# Run the application
echo "[INFO] Starting Product Management System..."
echo "[INFO] Application will be available at: http://localhost:8080"
echo "[INFO] Press Ctrl+C to stop the application"
echo

mvn spring-boot:run
