CREATE DATABASE StudentDB;
GO

USE StudentDB;
GO

CREATE TABLE Student (
    StudentID NVARCHAR(20) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    Birthday DATE,
    Major NVARCHAR(100),
    GPA FLOAT,
    StudentGroup NVARCHAR(50)
);
