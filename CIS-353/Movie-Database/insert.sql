--Insert Data into Tables
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
--Select statements to display table values
SELECT * FROM Users;
SELECT * FROM Movie;
SELECT * FROM Artist;
SELECT * FROM StudioCompany;
SELECT * FROM UserReview;
SELECT * FROM WorksOn;
SELECT * FROM ContractWith;
SELECT * FROM MovieType;
