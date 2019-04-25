--SET ECHO ON
--
-- Author: Silas Agnew, Denver DeBoer, Nicholas English
-- Date: 15 April 2019
-- ------------------------------------------------------------------
-- Description:
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
    prequel varchar(50) REFERENCES Movie(movieName),
    sequel varchar(50) REFERENCES Movie(movieName),
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
