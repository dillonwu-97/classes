-- Structure of one line
-- 2015,actor,4/15/15,Acting,Billy Crystal

DROP TABLE if EXISTS Daily_Show_Guests;

CREATE TABLE Daily_Show_Guests (
       year               integer,
       job_title          text,
       date_of_appearance date,
       profession         text,
       name               text
);

\copy Daily_Show_Guests FROM 'jon_stewart_daily_show_guests.csv'  delimiter ','  csv
