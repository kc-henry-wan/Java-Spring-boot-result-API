-- Create an audit table for tracking changes in TBL_Job
CREATE TABLE "TBL_Job_Audit" (
    "audit_id" SERIAL PRIMARY KEY,
    "job_id" INTEGER,
    "job_ref" VARCHAR(50) UNIQUE,
    "description" TEXT,
    "pharmacist_id" INTEGER,  -- Foreign key to TBL_Pharmacist
    "pharmacy_group_id" INTEGER,  -- Foreign key to TBL_Pharmacy_Group
    "pharmacy_branch_id" INTEGER,  -- Foreign key to TBL_Pharmacy_Branch
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
    "updated_user_id" INTEGER,  -- Foreign key to TBL_Admin_User
    "action" VARCHAR(50),  -- 'INSERT', 'UPDATE', or 'DELETE'
    "action_timestamp" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    CONSTRAINT fk_job
        FOREIGN KEY ("job_id")
        REFERENCES "TBL_Job" ("job_id")
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_audit_user
        FOREIGN KEY ("user_id")
        REFERENCES "TBL_User" ("user_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_audit_pharmacist
        FOREIGN KEY ("pharmacist_id")
        REFERENCES "TBL_Pharmacist" ("pharmacist_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_audit_pharmacy_group
        FOREIGN KEY ("pharmacy_group_id")
        REFERENCES "TBL_Pharmacy_Group" ("pharmacy_group_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_audit_pharmacy_branch
        FOREIGN KEY ("pharmacy_branch_id")
        REFERENCES "TBL_Pharmacy_Branch" ("pharmacy_branch_id")
        ON DELETE SET NULL
        ON UPDATE CASCADE
);


//create triggers. These triggers will fire on `UPDATE` or `DELETE` events and insert corresponding records into the audit table