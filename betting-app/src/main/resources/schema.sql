CREATE TABLE bet
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         VARCHAR(255)   NOT NULL,
    event_id        VARCHAR(255)   NOT NULL,
    event_market_id VARCHAR(255)   NOT NULL,
    event_winner_id VARCHAR(255)   NOT NULL,
    bet_amount      DECIMAL(19, 2) NOT NULL,
    created_at      TIMESTAMP      NOT NULL
);

CREATE TABLE bet_settlement
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    bet_id     BIGINT      NOT NULL UNIQUE,
    result     VARCHAR(10) NOT NULL,
    settled_at TIMESTAMP   NOT NULL
);
