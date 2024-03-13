CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.products(
    id                      UUID PRIMARY KEY,
    title                   VARCHAR(50),
    vendor_code             VARCHAR(6),
    description             VARCHAR(255),
    category                VARCHAR(50),
    price                   DOUBLE PRECISION,
    quantity                INTEGER,
    create_at               TIMESTAMPTZ,
    quantity_last_updated   TIMESTAMPTZ
);

CREATE UNIQUE INDEX vendor ON app.products (vendor_code);