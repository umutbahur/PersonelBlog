-- Create schema for blogwebsite
CREATE SCHEMA IF NOT EXISTS blogwebsite;

-- Create Article Table
CREATE TABLE blogwebsite.article (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    publication_date TIMESTAMP NOT NULL
);

-- Create User Table
CREATE TABLE blogwebsite.user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'GUEST'
);
