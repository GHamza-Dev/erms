#!/bin/bash

echo "Stopping and cleaning up existing containers..."
docker-compose down -v

echo "Removing any existing oracle data volumes..."
docker volume rm $(docker volume ls -q | grep oracle-data) 2>/dev/null || true

echo "Creating init-scripts directory..."
mkdir -p init-scripts

echo "Creating SQL initialization file..."
cat > init-scripts/01-create-user.sql << 'EOF'
ALTER SESSION SET CONTAINER = FREEPDB1;

-- Create user
CREATE USER ermsadmin IDENTIFIED BY erms123
    DEFAULT TABLESPACE USERS
    TEMPORARY TABLESPACE TEMP;

-- Grant necessary privileges
GRANT CREATE SESSION TO ermsadmin;
GRANT CREATE TABLE TO ermsadmin;
GRANT CREATE VIEW TO ermsadmin;
GRANT CREATE ANY TRIGGER TO ermsadmin;
GRANT CREATE ANY PROCEDURE TO ermsadmin;
GRANT CREATE SEQUENCE TO ermsadmin;
GRANT CREATE SYNONYM TO ermsadmin;
GRANT CONNECT TO ermsadmin;
GRANT RESOURCE TO ermsadmin;
GRANT UNLIMITED TABLESPACE TO ermsadmin;

-- Additional privileges that might be needed
GRANT SELECT ANY TABLE TO ermsadmin;
GRANT UPDATE ANY TABLE TO ermsadmin;
GRANT INSERT ANY TABLE TO ermsadmin;
GRANT DELETE ANY TABLE TO ermsadmin;

-- Ensure PDB is open and the state is saved
ALTER PLUGGABLE DATABASE FREEPDB1 OPEN;
ALTER PLUGGABLE DATABASE FREEPDB1 SAVE STATE;
EOF

echo "Starting Oracle container..."
docker-compose up -d oracle-db

echo "Waiting for Oracle to be ready (this will take about 2 minutes)..."
sleep 120

echo "Executing SQL initialization script..."
docker-compose exec -T oracle-db sqlplus sys/erms123@//localhost:1521/FREEPDB1 as sysdba < init-scripts/01-create-user.sql

echo "Starting Spring Boot application..."
docker-compose up -d erms-app

echo "Setup complete! Showing logs..."
docker-compose logs -f