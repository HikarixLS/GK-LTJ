#!/bin/bash
# Build script for Bookstore Management Application

echo "[INFO] Building Bookstore Management Application..."

# Create build directories
mkdir -p build/classes

# Compile Java sources
echo "[INFO] Compiling Java sources..."
javac -cp "lib/*" -d build/classes -sourcepath src/main/java \
    src/main/java/*.java \
    src/main/java/model/*.java \
    src/main/java/dao/*.java \
    src/main/java/service/*.java \
    src/main/java/gui/*.java

if [ $? -ne 0 ]; then
    echo "[ERROR] Compilation failed!"
    exit 1
fi

# Copy resources
echo "[INFO] Copying resources..."
mkdir -p build/classes/database
cp src/main/resources/database/* build/classes/database/
cp src/main/resources/*.properties build/classes/

# Create JAR file
echo "[INFO] Creating JAR file..."
cd build/classes
jar cfm ../bookstore-management.jar ../../MANIFEST.MF *
cd ../..

# Copy dependencies
echo "[INFO] Copying dependencies..."
mkdir -p build/lib
cp lib/* build/lib/

echo "[INFO] Build completed successfully!"
echo "[INFO] Run: java -cp \"build/bookstore-management.jar:build/lib/*\" BookstoreApp"
