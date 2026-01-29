IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'enterprise_platform')
BEGIN
    CREATE DATABASE enterprise_platform;
END
