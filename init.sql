DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS word;

CREATE TABLE IF NOT EXISTS word
(
    id      INT AUTO_INCREMENT,
    word    VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS game
(
    id      INT AUTO_INCREMENT,
    word_id INT,
    guessed VARCHAR(26),
    PRIMARY KEY(id),
    FOREIGN KEY (word_id) REFERENCES word(id)
);

INSERT INTO word
    (word)
VALUES
    ('aquto'),
    ('eat'),
    ('sleep'),
    ('football'),
    ('akka'),
    ('scala');
