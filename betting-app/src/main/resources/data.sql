-- Event "event-100": 2 correct predictions (WON), 1 wrong (LOST)
-- Event "event-200": separate event for isolation demo
INSERT INTO bet (user_id, event_id, event_market_id, event_winner_id, bet_amount)
VALUES ('user-1', 'event-100', 'market-1', 'winner-A', 50.00),
       ('user-2', 'event-100', 'market-1', 'winner-A', 100.00),
       ('user-3', 'event-100', 'market-1', 'winner-B', 75.00),
       ('user-1', 'event-200', 'market-2', 'winner-X', 200.00);
