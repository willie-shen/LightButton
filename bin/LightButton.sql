DROP DATABASE IF EXISTS LightButton;
CREATE DATABASE LightButton;
USE LightButton;

CREATE TABLE LightState
(
	lightID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	lightState VARCHAR(32) NOT NULL
);

INSERT into LightState(lightState) VALUES (0)