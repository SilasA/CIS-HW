SPOOL db.out
SET ECHO ON

/* Homework: create DB
 * Author: Silas Agnew
 */

SELECT * FROM Sailors;
SELECT * FROM Boats;
SELECT * FROM Reservations;
SELECT * FROM LazySailors;
SET AUTOCOMMIT ON;
INSERT INTO SAILERS VALUES (22, 'Dave', 6, 45.0, 95);
INSERT INTO SAILERS VALUES (91, 'Jay', 6, 45.0, 99);
INSERT INTO SAILERS VALUES (92, 'Popeye', 17, 45.0, 95);
SET ECHO OFF
SPOOL OFF