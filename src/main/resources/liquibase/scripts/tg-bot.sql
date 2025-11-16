-- liquibase formatted sql

-- changeset euzhastina:1
CREATE TABLE notification_task (
id SERIAL PRIMARY KEY,
chatId INTEGER,
messageText TEXT,
messageTime TIMESTAMP
)

-- changeset euzhastina:2
ALTER TABLE notification_task DROP COLUMN chatId;
ALTER TABLE notification_task DROP COLUMN messageText;
ALTER TABLE notification_task DROP COLUMN messageTime;

-- changeset euzhastina:3
CREATE TABLE notification_task (
id SERIAL PRIMARY KEY,
chat_id BIGINT,
message_text TEXT,
message_time TIMESTAMP
)

-- changeset euzhastina:4
DROP TABLE notification_task;

-- changeset euzhastina:5
CREATE TABLE notification_task (
id SERIAL PRIMARY KEY,
chat_id BIGINT,
message_text TEXT,
message_time TIMESTAMP
)

