
INSERT INTO "tbl_pharmacist" ("password", "email", "first_name", "last_name", "status", "mobile", "role")
VALUES
('password', 'user1@example.com', 'Paul', 'Hunter', 'A', '987654321', 'ROLE_ADMIN'),
('password', 'user2@example.com', 'Peter', 'Pen', 'A', '987654321', 'ROLE_ADMIN'),
('password', 'user3@example.com', 'Mary', 'Harmsot', 'A', '987654321', 'ROLE_ADMIN'),
('password', 'user4@example.com', 'Sue', 'Elli', 'A', '987654321', 'ROLE_ADMIN'),
('password', 'user5@example.com', 'Michael', 'Jordon', 'A', '987654321', 'ROLE_ADMIN')
;

INSERT INTO "tbl_pharmacist" ("first_name", "email", "password", "last_name", "status", "updated_user_id", "mobile", "address_1","postal_code")
VALUES
('Pharmacist A', 'pharmaA@example.com', 'password', 'Smith', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist B', 'pharmaB@example.com', 'password', 'Thomas', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist C', 'pharmaC@example.com', 'password', 'Williams', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist D', 'pharmaD@example.com', 'password', 'Murphy', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist E', 'pharmaE@example.com', 'password', 'Walsh', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist F', 'pharmaF@example.com', 'password', 'Taylor', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist G', 'pharmaG@example.com', 'password', 'Walsh', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist H', 'pharmaH@example.com', 'password', 'Garcia', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist I', 'pharmaI@example.com', 'password', 'Byrne', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT'),
('Pharmacist J', 'pharmaJ@example.com', 'password', 'Davies', 'A', 1, '987654321', '8 Prior Deram Walk','CV4 8FT');

INSERT INTO "tbl_pharmacy_group" ("group_name", "group_code", "status", "updated_user_id" )
VALUES
('Pharmacy Group A', 'AA', 'A', 2),
('Pharmacy Group X', 'XX', 'A', 2),
('Pharmacy Group H', 'HH', 'A', 2);


INSERT INTO "tbl_pharmacy_branch" ("branch_name", "address_1", "postal_code", "status", "updated_user_id")
VALUES
('Branch A', '123 Main St, City A', 'CV4 7AL', 'A', 2),
('Branch B', '456 Oak St, City B', 'CV4 7AL', 'A', 2),
('Branch C', '789 Pine St, City C', 'CV4 7AL', 'A', 2),
('Branch D', '101 Maple St, City D', 'CV4 7AL', 'A', 2),
('Branch E', '202 Elm St, City E', 'CV4 7AL', 'A', 2);




INSERT INTO "tbl_jobs" ("job_ref", "description", "status", "pharmacy_group_id", "pharmacy_branch_id", "job_date", "job_start_time", "job_end_time", "hourly_rate", "total_work_hour", "total_paid", "lunch_arrangement", "parking_option", "rate_per_mile", "status_code", "deleted", "updated_user_id")
VALUES
('REF001', 'Pharmacist Shift A Morning shift', 'Open', 1, 1, '2024-10-25', '09:00', '13:00', 25.00, 4.00, 100.00, 'Lunch provided', 'Parking available', 0.50, 'O', FALSE, 2),
('REF002', 'Pharmacist Shift B Evening shift', 'Open', 2, 2, '2024-10-26', '14:00', '18:00', 28.00, 4.00, 112.00, 'No lunch', 'No parking', 0.60, 'O', FALSE, 2),
('REF003', 'Pharmacist Shift C Night shift', 'Open',  3, 3, '2024-10-27', '20:00', '00:00', 30.00, 4.00, 120.00, 'Lunch provided', 'Parking available', 0.70, 'O', FALSE, 2),
('REF004', 'Pharmacist Shift D Morning shift', 'Open', 1, 4, '2024-10-28', '09:00', '13:00', 22.00, 4.00, 88.00, 'No lunch', 'Parking available', 0.40, 'O', FALSE, 2),
('REF005', 'Pharmacist Shift E Evening shift', 'Open', 1, 3, '2024-10-29', '14:00', '18:00', 26.00, 4.00, 104.00, 'Lunch provided', 'No parking', 0.50, 'O', FALSE, 2),
('REF006', 'Pharmacist Shift F Night shift', 'Open', 1, 4, '2024-10-30', '20:00', '00:00', 32.00, 4.00, 128.00, 'No lunch', 'Parking available', 0.60, 'O', FALSE, 2),
('REF007', 'Pharmacist Shift G Morning shift', 'Open',  1, 3, '2024-11-01', '09:00', '13:00', 24.00, 4.00, 96.00, 'Lunch provided', 'No parking', 0.55, 'O', FALSE, 2),
('REF008', 'Pharmacist Shift H Evening shift', 'Open',  1, 2, '2024-11-02', '14:00', '18:00', 29.00, 4.00, 116.00, 'No lunch', 'Parking available', 0.65, 'O', FALSE, 2);


update "tbl_pharmacist" set password = '$2a$12$jU3s8q.ecbEsglv5Bvs5OeuEXvfqi64Qx.P4pIRyRCwihynjbpvdK';
