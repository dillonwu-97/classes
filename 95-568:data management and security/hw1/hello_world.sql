DROP TABLE IF EXISTS Greetings;
CREATE TABLE Greetings (a text, b text);
INSERT INTO Greetings
	VALUES ('Hello,', 'world!');
SELECT * FROM Greetings