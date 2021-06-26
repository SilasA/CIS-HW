SPOOL project.out
SET ECHO ON
--
-- Author: Silas Agnew, Denver DeBoer, Nicholas English
-- Date: 16 April 2019
-- ------------------------------------------------------------------
-- Description:
--
-- DELETE TABLES
--
DROP TABLE Users CASCADE CONSTRAINTS;
DROP TABLE Movie CASCADE CONSTRAINTS;
DROP TABLE Artist CASCADE CONSTRAINTS;
DROP TABLE StudioCompany CASCADE CONSTRAINTS;
DROP TABLE UserReview CASCADE CONSTRAINTS;
DROP TABLE WorksOn CASCADE CONSTRAINTS;
DROP TABLE ContractWith CASCADE CONSTRAINTS;
DROP TABLE MovieType CASCADE CONSTRAINTS;
--
CREATE TABLE Users (
    userID int PRIMARY KEY,
    username varchar(20) NOT NULL,
    dateJoined varchar(20) NOT NULL
);
--
CREATE TABLE StudioCompany (
    sName varchar(30) PRIMARY KEY,
    sCEO varchar(30),
    sWebsite varchar(25)
);
--
CREATE TABLE Movie (
    movieName varchar(30) PRIMARY KEY,
    movieLength int,
    releaseDate varchar(20),
    budget int,
    prequel varchar(30) REFERENCES Movie(movieName),
    sequel varchar(30) REFERENCES Movie(movieName),
    studioName varchar(30) REFERENCES StudioCompany(sName),
    CONSTRAINT UNIQ CHECK (prequel != sequel)
);
--
CREATE TABLE Artist (
    aID int PRIMARY KEY,
    aName varchar(30),
    aBirthday varchar(20),
    aAddress varchar(30)
);
--
CREATE TABLE UserReview (
    userID int REFERENCES Users(userID),
    mName varchar(30) REFERENCES Movie(movieName),
    dateReviewed varchar(20),
    score int,
    CONSTRAINT UR PRIMARY KEY (userID, mName, dateReviewed),
    CONSTRAINT SC CHECK(score >= -50 AND score <= 50)
);
--
CREATE TABLE WorksOn (
    aID int REFERENCES Artist(aID),
    mName varchar(30) REFERENCES Movie(movieName),
    role varchar(10),
    CONSTRAINT AM PRIMARY KEY (aID, mName), 
    CONSTRAINT RL CHECK(role IN ('Director', 'Actor', 'Producer'))
);
--
CREATE TABLE ContractWith (
    aID int REFERENCES Artist(aID),
    sName varchar(30) REFERENCES StudioCompany(sName),
    CONSTRAINT WO PRIMARY KEY (aID, sName)
);
--
CREATE TABLE MovieType (
    movieName varchar(50) REFERENCES Movie(movieName),
    genre varchar(15),
    CONSTRAINT MG PRIMARY KEY (movieName, genre)
);
--
SET FEEDBACK OFF
--
-- INSERT DATA
--
--User(UserID, Username, DateJoined)
INSERT INTO Users VALUES (01,'CoJo','5 April 2019');
INSERT INTO Users VALUES (02,'Owen','5 April 2019');
INSERT INTO Users VALUES (03,'Halo Reach','5 April 2019');
INSERT INTO Users VALUES (04,'Bambi','5 April 2019');
INSERT INTO Users VALUES (05,'cinema.uncanned','5 April 2019');
INSERT INTO Users VALUES (06,'Pol Pot','5 April 2019');
--
--Studio_Company(StuName,StuCEO,Website)
INSERT INTO StudioCompany VALUES ('Marvel Studios','Avi Arad','www.marvel.com/movies');
INSERT INTO StudioCompany VALUES ('Universal Pictures','Ronald Meyer','www.universalpictures.com');
INSERT INTO StudioCompany VALUES ('Element Pictures','Annette Waldron',NULL);
INSERT INTO StudioCompany VALUES ('Wiseau Studios','Tommy Wiseau','wiseaustudios.com');
INSERT INTO StudioCompany VALUES ('Lion''s Gate Studios','Peter Strauss','www.lionsgate.com');
--
--Movie(MovieName, Movie_Length, Release_Date, Budget, Prequel, Sequel,SName)
INSERT INTO Movie VALUES ('Spider-Man Homecoming',133,'7 July 2017',175000000,NULL, NULL,'Marvel Studios');
INSERT INTO Movie VALUES ('Willard',100,'14 March 2003',22000000,NULL,NULL,'Lion''s Gate Studios');
INSERT INTO Movie VALUES ('The Room',99,'26 June 2003',6000000,NULL,NULL,'Wiseau Studios');
INSERT INTO Movie VALUES ('The Killing of a Sacred Deer',121,'3 November 2017',2000000,NULL,NULL,'Element Pictures');
INSERT INTO Movie VALUES ('Get Out',104,'24 February 2017',5000000,NULL,NULL,'Universal Pictures');
INSERT INTO Movie VALUES ('Ant-Man and the Wasp',118,'6 July 2018',162000000,NULL, NULL,'Marvel Studios');
INSERT INTO Movie VALUES ('Ant-Man',117,'17 July 2015',162000000,NULL, 'Ant-Man and the Wasp','Marvel Studios');
UPDATE Movie SET prequel = 'Ant-Man' WHERE movieName = 'Ant-Man and the Wasp';
--
--Artist(aID, aName, aBirthday, aAddress)
INSERT INTO Artist VALUES (11,'Tom Holland','1 June 1996','London, UK');
INSERT INTO Artist VALUES (12,'Crispin Glover','20 April 1964','New York City, New York, USA');
INSERT INTO Artist VALUES (13,'Barry Keoghan','17 October 1992','Dublin, Ireland');
INSERT INTO Artist VALUES (14,'Greg Sestero','15 July 1978','Walnut Creek, California, USA');
INSERT INTO Artist VALUES (15,'Daniel Kaluuya','24 February 1989','London, UK');
INSERT INTO Artist VALUES (16,'Paul Rudd','6 April 1969','New Jersy, USA');
INSERT INTO Artist VALUES (21,'Jon Watts','28 June 1981','Fountain, Colorado, USA');
INSERT INTO Artist VALUES (22,'Glen Morgan','16 January 1947','San Francisco, California, USA');
INSERT INTO Artist VALUES (23,'Tommy Wiseau','3 October 1955','Poznan, Poland');
INSERT INTO Artist VALUES (24,'Yorgos Lanthimos','1 January 1973','Athens, Greece');
INSERT INTO Artist VALUES (25,'Jordan Peele','21 February 1979','New York City, New York, USA');
INSERT INTO Artist VALUES (26,'Peyton Reed','3 July 1964','Raleigh, North Carolina, USA');
INSERT INTO Artist VALUES (31,'Victoria Alonso','22 December 1965','Buenos Aires, Argentina');
INSERT INTO Artist VALUES (32,'Toby Emmerich','8 February 1963','New York City, New York, USA');
INSERT INTO Artist VALUES (33,'Drew Caffrey','13 August 1931','Santa Monica, California, USA');
INSERT INTO Artist VALUES (34,'Atilla Salih Yucer','12 February 1978','Bloemfontein, South Africa');
INSERT INTO Artist VALUES (35,'Jason Blum','20 February 1969','Los Angeles, California, USA');
INSERT INTO Artist VALUES (36,'Stan Lee','28 December 1922','Los Angeles, California, USA');
INSERT INTO Artist VALUES (69,'Nicolas Cage','7 January 1964','Long Beach, California, USA');
--
--User_Review(UID,MName,DateReviewed,Score)
INSERT INTO UserReview VALUES (01,'Spider-Man Homecoming','5 April 2019',50);
INSERT INTO UserReview VALUES (01,'Willard','5 April 2019',-50);
INSERT INTO UserReview VALUES (01,'The Room','5 April 2019',-37);
INSERT INTO UserReview VALUES (01,'The Killing of a Sacred Deer','5 April 2019',26);
INSERT INTO UserReview VALUES (01,'Get Out','5 April 2019',50);
INSERT INTO UserReview VALUES (01,'Ant-Man and the Wasp','5 April 2019',50);
INSERT INTO UserReview VALUES (02,'Spider-Man Homecoming','5 April 2019',-50);
INSERT INTO UserReview VALUES (02,'Willard','5 April 2019',-50);
INSERT INTO UserReview VALUES (02,'The Room','5 April 2019',50);
INSERT INTO UserReview VALUES (02,'The Killing of a Sacred Deer','5 April 2019',50);
INSERT INTO UserReview VALUES (02,'Get Out','5 April 2019',-50);
INSERT INTO UserReview VALUES (02,'Ant-Man and the Wasp','5 April 2019',0);
INSERT INTO UserReview VALUES (03,'The Room','5 April 2019',-25);
INSERT INTO UserReview VALUES (03,'The Killing of a Sacred Deer','5 April 2019',-50);
INSERT INTO UserReview VALUES (03,'Get Out','5 April 2019',19);
INSERT INTO UserReview VALUES (03,'Ant-Man and the Wasp','5 April 2019',50);
INSERT INTO UserReview VALUES (04,'Spider-Man Homecoming','5 April 2019',39);
INSERT INTO UserReview VALUES (04,'Willard','5 April 2019',18);
INSERT INTO UserReview VALUES (04,'The Room','5 April 2019',-50);
INSERT INTO UserReview VALUES (04,'The Killing of a Sacred Deer','5 April 2019',0);
INSERT INTO UserReview VALUES (04,'Get Out','5 April 2019',20);
INSERT INTO UserReview VALUES (04,'Ant-Man and the Wasp','5 April 2019',0);
INSERT INTO UserReview VALUES (05,'Spider-Man Homecoming','5 April 2019',45);
INSERT INTO UserReview VALUES (05,'Willard','5 April 2019',25);
INSERT INTO UserReview VALUES (05,'The Room','5 April 2019',-30);
INSERT INTO UserReview VALUES (05,'The Killing of a Sacred Deer','5 April 2019',-15);
INSERT INTO UserReview VALUES (05,'Get Out','5 April 2019',35);
INSERT INTO UserReview VALUES (05,'Ant-Man and the Wasp','5 April 2019',50);
INSERT INTO UserReview VALUES (06,'Spider-Man Homecoming','5 April 2019',-19);
INSERT INTO UserReview VALUES (06,'Willard','5 April 2019',50);
INSERT INTO UserReview VALUES (06,'The Room','5 April 2019',50);
INSERT INTO UserReview VALUES (06,'The Killing of a Sacred Deer','5 April 2019',30);
INSERT INTO UserReview VALUES (06,'Get Out','5 April 2019',0);
INSERT INTO UserReview VALUES (06,'Ant-Man and the Wasp','5 April 2019',44);
--
--Works_On(aID,MName,Role)
INSERT INTO WorksOn VALUES (11,'Spider-Man Homecoming','Actor');
INSERT INTO WorksOn VALUES (21,'Spider-Man Homecoming','Director');
INSERT INTO WorksOn VALUES (31,'Spider-Man Homecoming','Producer');
INSERT INTO WorksOn VALUES (36,'Spider-Man Homecoming','Actor');
INSERT INTO WorksOn VALUES (12,'Willard','Actor');
INSERT INTO WorksOn VALUES (22,'Willard','Director');
INSERT INTO WorksOn VALUES (32,'Willard','Producer');
INSERT INTO WorksOn VALUES (13,'The Killing of a Sacred Deer','Actor');
INSERT INTO WorksOn VALUES (23,'The Killing of a Sacred Deer','Director');
INSERT INTO WorksOn VALUES (33,'The Killing of a Sacred Deer','Producer');
INSERT INTO WorksOn VALUES (14,'The Room','Actor');
INSERT INTO WorksOn VALUES (24,'The Room','Director');
INSERT INTO WorksOn VALUES (34,'The Room','Producer');
INSERT INTO WorksOn VALUES (15,'Get Out','Actor');
INSERT INTO WorksOn VALUES (25,'Get Out','Director');
INSERT INTO WorksOn VALUES (35,'Get Out','Producer');
INSERT INTO WorksOn VALUES (16,'Ant-Man and the Wasp','Actor');
INSERT INTO WorksOn VALUES (26,'Ant-Man and the Wasp','Director');
INSERT INTO WorksOn VALUES (36,'Ant-Man and the Wasp','Producer');
INSERT INTO WorksOn VALUES (31,'Ant-Man and the Wasp','Producer');
INSERT INTO WorksOn VALUES (16,'Ant-Man','Actor');
INSERT INTO WorksOn VALUES (26,'Ant-Man','Director');
INSERT INTO WorksOn VALUES (36,'Ant-Man','Producer');
INSERT INTO WorksOn VALUES (31,'Ant-Man','Producer');
--
--Contract_With(aID,SName)
INSERT INTO ContractWith VALUES (11,'Marvel Studios');
INSERT INTO ContractWith VALUES (12,'Lion''s Gate Studios');
INSERT INTO ContractWith VALUES (13,'Element Pictures');
INSERT INTO ContractWith VALUES (14,'Wiseau Studios');
INSERT INTO ContractWith VALUES (15,'Universal Pictures');
INSERT INTO ContractWith VALUES (16,'Marvel Studios');
INSERT INTO ContractWith VALUES (21,'Marvel Studios');
INSERT INTO ContractWith VALUES (22,'Lion''s Gate Studios');
INSERT INTO ContractWith VALUES (23,'Element Pictures');
INSERT INTO ContractWith VALUES (24,'Wiseau Studios');
INSERT INTO ContractWith VALUES (25,'Universal Pictures');
INSERT INTO ContractWith VALUES (26,'Marvel Studios');
INSERT INTO ContractWith VALUES (31,'Marvel Studios');
INSERT INTO ContractWith VALUES (32,'Lion''s Gate Studios');
INSERT INTO ContractWith VALUES (33,'Element Pictures');
INSERT INTO ContractWith VALUES (34,'Wiseau Studios');
INSERT INTO ContractWith VALUES (35,'Universal Pictures');
INSERT INTO ContractWith VALUES (36,'Marvel Studios');
--
--MovieType(MovieName,genre)
INSERT INTO MovieType VALUES ('Spider-Man Homecoming','Action');
INSERT INTO MovieType VALUES ('Spider-Man Homecoming','Adventure');
INSERT INTO MovieType VALUES ('Spider-Man Homecoming','Sci-Fi');
INSERT INTO MovieType VALUES ('Willard','Drama');
INSERT INTO MovieType VALUES ('Willard','Horror');
INSERT INTO MovieType VALUES ('Willard','Sci-Fi');
INSERT INTO MovieType VALUES ('The Room','Drama');
INSERT INTO MovieType VALUES ('The Killing of a Sacred Deer','Drama');
INSERT INTO MovieType VALUES ('The Killing of a Sacred Deer','Mystery');
INSERT INTO MovieType VALUES ('The Killing of a Sacred Deer','Thriller');
INSERT INTO MovieType VALUES ('Get Out','Horror');
INSERT INTO MovieType VALUES ('Get Out','Mystery');
INSERT INTO MovieType VALUES ('Get Out','Thriller');
INSERT INTO MovieType VALUES ('Ant-Man and the Wasp','Action');
INSERT INTO MovieType VALUES ('Ant-Man and the Wasp','Adventure');
INSERT INTO MovieType VALUES ('Ant-Man and the Wasp','Comedy');
INSERT INTO MovieType VALUES ('Ant-Man','Action');
INSERT INTO MovieType VALUES ('Ant-Man','Adventure');
INSERT INTO MovieType VALUES ('Ant-Man','Comedy');
INSERT INTO MovieType VALUES ('Ant-Man','Sci-Fi');
--
SET FEEDBACK ON
COMMIT;
--
-- Select statements to display table values
SELECT * FROM Users;
SELECT * FROM Movie;
SELECT * FROM Artist;
SELECT * FROM StudioCompany;
SELECT * FROM UserReview;
SELECT * FROM WorksOn;
SELECT * FROM ContractWith;
SELECT * FROM MovieType;
--
-- QUERIES
--
/* Query #1
   For every artist who was in a movie that is a Horror film with more than 5 reviews:
   We find the artists name, name of the movie, studio company's name, and the average
   score received for a movie. We will sort by average score.

   Completes #1, #4, #5 query types needed.
*/
SELECT	 A.aName, W.Mname, S.SName, AVG(R.Score)
FROM	 Artist A, WorksOn W, StudioCompany S, UserReview R, Movie M, MovieType T
WHERE	 T.genre = 'Horror' AND W.aID = A.aID AND
	 W.MName = M.MovieName AND M.StudioName = S.SName 
	 AND T.movieName = M.MovieName AND R.Mname = M.MovieName
GROUP BY A.aName, W.MName, S.SName
HAVING	 COUNT(*) > 4
ORDER BY AVG(R.Score);
--
--
--
/* Query #2
   For every artist, find an artist who lives in the same location.

   Completes #2 query type needed.
*/
SELECT	 A1.aName, A2.aName, A1.aAddress
FROM	 Artist A1, Artist A2
WHERE	 A1.aName != A2.aName AND A1.aAddress = A2.aAddress AND A1.aID > A2.aID
ORDER BY A1.aAddress;
--
--
--
/* Query #3
   For every artist, find the studio name via outer join query.

   Completes #9 query type needed.
*/
SELECT	 A.aName, C.sName
FROM	 Artist A LEFT JOIN ContractWith C
ON	 A.aID = C.aID
ORDER BY C.sName;
--
--
--
/* Query #4
   For every artist who works on every Marvel movie, display
   the artist's ID, Name, and the studio's name

   Complete #3, #6, #7, #8 query type needed.
*/
SELECT A.aID, A.aName, C.SName
FROM Artist A, ContractWith C
Where A.aID = C.aID AND
      NOT EXISTS((SELECT M.MovieName
		  FROM Movie M
		  WHERE M.studioName LIKE 'Marvel Studios')
		MINUS
		(SELECT M.MovieName
		 FROM Movie M, WorksOn W
		 WHERE A.aID = W.aID AND
		 W.MName = M.MovieName AND
		 M.studioName LIKE 'Marvel Studios'));
--
-- TESTS
--
-- Primary Key Test
INSERT INTO Users VALUES (01,'VladiP00','5 April 2019');
--
-- Foreign Key Test
INSERT INTO UserReview VALUES (007, 'Spider-Man Homecoming','6 April 2019', 0);
--
-- Testing Constraint SC
INSERT INTO UserReview VALUES (01,'Willard','5 April 2019', 100);
--
-- Testing Constraint UNIQ
INSERT INTO Movie VALUES ('Terminator',108,'26 October 1984',6400000,'Spider-Man Homecoming','Spider-Man Homecoming','Marvel Studios');
COMMIT;
--
SPOOL OFF
