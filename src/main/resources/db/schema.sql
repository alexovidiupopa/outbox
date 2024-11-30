drop table if exists outbox cascade;
drop table if exists "order" cascade;

-- Orders table
CREATE TABLE "order" (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_id BIGINT NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Outbox table
CREATE TABLE outbox (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        aggregate_id BIGINT NOT NULL,
                        aggregate_type VARCHAR(50) NOT NULL,
                        event_type VARCHAR(50) NOT NULL,
                        payload TEXT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        processed_at TIMESTAMP NULL
);