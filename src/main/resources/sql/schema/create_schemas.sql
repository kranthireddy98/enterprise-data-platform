IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'master')
    EXEC('CREATE SCHEMA master');

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'core')
    EXEC('CREATE SCHEMA core');

IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'ops')
    EXEC('CREATE SCHEMA ops');
