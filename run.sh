#!/bin/bash
# Run script for Bookstore Management Application

echo "[INFO] Starting Bookstore Management Application..."

# Check Java
if ! command -v java &> /dev/null; then
    echo "[ERROR] Java not found! Please install Java JDK 11 or higher."
    exit 1
fi

# Run application
if [ -f "build/bookstore-management.jar" ]; then
    echo "[INFO] Running from JAR file..."
    java -cp "build/bookstore-management.jar:build/lib/*" BookstoreApp
else
    echo "[INFO] Running from class files..."
    java -cp "build/classes:lib/*" BookstoreApp
fi

if [ $? -ne 0 ]; then
    echo "[ERROR] Application failed to start!"
    echo "[INFO] Please check:"
    echo "  1. MySQL Server is running"
    echo "  2. Database 'bookstore' exists"
    echo "  3. Database connection settings in database.properties"
    echo "  4. MySQL JDBC driver is in lib/ folder"
fi
