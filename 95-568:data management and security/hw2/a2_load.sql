
DROP TABLE IF EXISTS Medalists CASCADE;

CREATE TABLE Medalists (
    --
    -- TODO:

    -- Define the fields and their data types based on the content
    -- of the CSV file.
    -- Use the EXACT same names as given in the header of the CSV file
    edition integer,
    athlete text,
    noc text,
    gender text,
    event text,
    medal text,
    season text

);

\copy Medalists FROM 'medalists.csv' csv header;

