--
-- Author: Silas Agnew, Denver DeBoer, Nicholas English
-- Date: 12 April 2019
-- ------------------------------------------------------------------
-- Description:
--
--
--
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
