CREATE TABLE IF NOT EXISTS public.analytics (
    cartId VARCHAR(255) NOT NULL,
    version BIGINT NOT NULL,
    count BIGINT NOT NULL,
    isIn BOOLEAN NOT NULL,
    PRIMARY KEY (cartId));
