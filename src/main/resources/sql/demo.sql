CREATE TABLE kafka_source
(
    created_at STRING,
    t_action   STRING,
    t_object   STRING
) WITH (
    'connector.type' = 'kafka',
    'connector.version' = 'universal',
    'connector.topic' = 'xxx',
    'format.type' = 'json',
    'connector.properties.0.key' = 'bootstrap.servers',
    'connector.properties.0.value' = '127.0.0.1:9092',
    'connector.properties.1.key' = 'group.id',
    'connector.properties.1.value' = 'xxxx',
    'connector.startup-mode' = 'group-offsets'
);

CREATE TABLE clickhouse_sink
(
    created_at STRING,
    t_action   STRING,
    t_object   STRING
) WITH (
    'connector' = 'clickhouse',
    'url' = 'clickhouse://127.0.0.1:8123',
    'database-name' = 'default',
    'table-name' = 'demo',
    'sink.batch-size' = '100',
    'sink.flush-interval' = '1000',
    'sink.max-retries' = '3'
);

INSERT INTO clickhouse_sink
SELECT
    created_at,
    t_action,
    t_object
FROM kafka_source
WHERE t_action = 'sql-submit';