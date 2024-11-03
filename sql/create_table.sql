
-- Create tbl_pharmacist table
CREATE TABLE "tbl_pharmacist" (
    "pharmacist_id" SERIAL PRIMARY KEY,
    "password" VARCHAR(255) NOT NULL,
    "email" VARCHAR(100),
    "first_name" VARCHAR(50) NOT NULL,
    "last_name" VARCHAR(50) NOT NULL,
    "mobile" VARCHAR(20) NOT NULL,
    "address_1" VARCHAR(50) ,
    "address_2" VARCHAR(50) ,
    "longitude" DECIMAL(10, 8),
    "latitude" DECIMAL(10, 8),
    "postal_code" VARCHAR(10) ,
    "status" VARCHAR(20),
    "role" VARCHAR(20),
    "updated_user_id" INTEGER,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

-- Create tbl_pharmacy_group table
CREATE TABLE "tbl_pharmacy_group" (
    "pharmacy_group_id" SERIAL PRIMARY KEY,
    "group_name" VARCHAR(255) NOT NULL,
    "group_code" VARCHAR(3) NOT NULL,
    "status" VARCHAR(20),
    "updated_user_id" INTEGER,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

-- Create tbl_pharmacy_branch table
CREATE TABLE "tbl_pharmacy_branch" (
    "pharmacy_branch_id" SERIAL PRIMARY KEY,
    "branch_name" VARCHAR(255) NOT NULL,
    "address_1" VARCHAR(50) NOT NULL,
    "address_2" VARCHAR(50) ,
    "postal_code" VARCHAR(10) NOT NULL,
    "longitude" DECIMAL(10, 8),
    "latitude" DECIMAL(10, 8),
    "status" VARCHAR(20),
    "updated_user_id" INTEGER,  -- Foreign key to tbl_admin_user
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraint linking updated_user_id to tbl_admin_user
    CONSTRAINT fk_user
        FOREIGN KEY ("updated_user_id")
        REFERENCES "tbl_admin_user" ("admin_user_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Create tbl_jobs table with foreign keys and additional fields
CREATE TABLE "tbl_jobs" (
    "job_id" SERIAL PRIMARY KEY,
    "job_ref" VARCHAR(50) UNIQUE,
    "description" TEXT,
    "pharmacist_id" INTEGER,  -- Foreign key to tbl_pharmacist
    "pharmacy_group_id" INTEGER,  -- Foreign key to tbl_pharmacy_group
    "pharmacy_branch_id" INTEGER,  -- Foreign key to tbl_pharmacy_branch
    "job_date" DATE NOT NULL,
    "job_start_time" TIME NOT NULL,
    "job_end_time" TIME NOT NULL,
    "hourly_rate" DECIMAL(10, 2),
    "total_work_hour" DECIMAL(5, 2),
    "total_paid" DECIMAL(10, 2),
    "lunch_arrangement" VARCHAR(255),
    "parking_option" VARCHAR(255),
    "rate_per_mile" DECIMAL(5, 2),
    "status_code" VARCHAR(1),
    "status" VARCHAR(20),
    "deleted" BOOLEAN DEFAULT FALSE,
    "updated_user_id" INTEGER,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key constraint linking pharmacist_id to tbl_pharmacist
    CONSTRAINT fk_pharmacist
        FOREIGN KEY ("pharmacist_id")
        REFERENCES "tbl_pharmacist" ("pharmacist_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE,
        
    -- Foreign key constraint linking pharmacy_group_id to tbl_pharmacy_group
    CONSTRAINT fk_pharmacy_group
        FOREIGN KEY ("pharmacy_group_id")
        REFERENCES "tbl_pharmacy_group" ("pharmacy_group_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE,
        
    -- Foreign key constraint linking pharmacy_branch_id to tbl_pharmacy_branch
    CONSTRAINT fk_pharmacy_branch
        FOREIGN KEY ("pharmacy_branch_id")
        REFERENCES "tbl_pharmacy_branch" ("pharmacy_branch_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE
);


-- Create tbl_negotiation table with foreign keys and additional fields
CREATE TABLE "tbl_negotiation" (
    "negotiation_id" SERIAL PRIMARY KEY,
    "reason" TEXT,
    "job_id" INTEGER,  -- Foreign key to tbl_jobs
    "pharmacist_id" INTEGER,  -- Foreign key to tbl_pharmacist
    "original_hourly_rate" DECIMAL(10, 2),
    "original_total_paid" DECIMAL(10, 2),
    "purposed_hourly_rate" DECIMAL(10, 2),
    "purposed_total_paid" DECIMAL(10, 2),
    "counter_hourly_rate" DECIMAL(10, 2),
    "counter_total_paid" DECIMAL(10, 2),
    "status_code" VARCHAR(1),
    "deleted" BOOLEAN DEFAULT FALSE,
    "updated_user_id" INTEGER,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key constraint linking pharmacist_id to tbl_jobs
    CONSTRAINT fk_jobs
        FOREIGN KEY ("job_id")
        REFERENCES "tbl_jobs" ("job_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE,
        
    -- Foreign key constraint linking pharmacist_id to tbl_pharmacist
    CONSTRAINT fk_pharmacist
        FOREIGN KEY ("pharmacist_id")
        REFERENCES "tbl_pharmacist" ("pharmacist_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE,
);




CREATE TABLE "tbl_sessions" (
    "session_id" SERIAL PRIMARY KEY,
    "user_id" INTEGER NOT NULL,
    "user_role" VARCHAR(20) NOT NULL,
    "jwt_token" TEXT NOT NULL,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "expires_at" TIMESTAMP,
    FOREIGN KEY ("user_id") REFERENCES "tbl_admin_user" ("admin_user_id") ON DELETE CASCADE
        ON UPDATE CASCADE
);


CREATE TABLE "password_reset_token" (
    "token_id" SERIAL PRIMARY KEY,
    "token" VARCHAR(255) NOT NULL,
    "user_id" INTEGER NOT NULL,
    "user_type" VARCHAR(10) NOT NULL CHECK (user_type IN ('ADMIN', 'PHARMACIST')),
    "expiry_date" TIMESTAMP NOT NULL,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_admin FOREIGN KEY ("user_id") REFERENCES "tbl_admin_user" ("admin_user_id") ON DELETE CASCADE,
    CONSTRAINT fk_user_pharmacist FOREIGN KEY ("user_id") REFERENCES "tbl_pharmacist" ("pharmacist_id") ON DELETE CASCADE
);

CREATE TABLE device_tokens (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL UNIQUE,
    token VARCHAR(255) NOT NULL,
    platform VARCHAR(50) NOT NULL, -- e.g., 'android', 'ios'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
