
DROP TABLE IF EXISTS Baby_Names;

CREATE TABLE Baby_Names (
   state    text,
   gender   text,
   year     integer,
   fname    text,
   number   integer
);

\copy Baby_Names  from 'babynames2017_state_gender_year_name_number.csv'   CSV

