
-- File: companyDML-b-solution  
-- SQL/DML HOMEWORK (on the COMPANY database)
/*
Every query is worth 2 point. There is no partial credit for a
partially working query - think of this hwk as a large program and each query is a small part of the program.
--
IMPORTANT SPECIFICATIONS
--
(A)
-- Download the script file company.sql and use it to create your COMPANY database.
-- Dowlnoad the file companyDBinstance.pdf; it is provided for your convenience when checking the results of your queries.
(B)
Implement the queries below by ***editing this file*** to include
your name and your SQL code in the indicated places.   
--
(C)
IMPORTANT:
-- Don't use views
-- Don't use inline queries in the FROM clause - see our class notes.
--
(D)
After you have written the SQL code in the appropriate places:
** Run this file (from the command line in sqlplus).
** Print the resulting spooled file (companyDML-b.out) and submit the printout in class on the due date.
--
**** Note: you can use Apex to develop the individual queries. However, you ***MUST*** run this file from the command line as just explained above and then submit a printout of the spooled file. Submitting a printout of the webpage resulting from Apex will *NOT* be accepted.
--
*/
-- Please don't remove the SET ECHO command below.
SPOOL companyDML-b.out
SET ECHO ON
-- ------------------------------------------------------------
-- 
-- Name: Silas Agnew
--
-- -------------------------------------------------------------
--
-- NULL AND SUBSTRINGS -------------------------------
--
/*(10B)
Find the ssn and last name of every employee whose ssn contains two consecutive 8's, and has a supervisor. Sort the results by ssn.
*/
SELECT ssn, Lname
FROM employee
WHERE ssn LIKE '%88%' AND Super_ssn IS NOT NULL
ORDER BY ssn;
-- JOINING 3 TABLES ------------------------------
-- 
/*(11B)
For every employee who works for more than 20 hours on any project that is controlled by the research department: Find the ssn, project number,  and number of hours. Sort the results by ssn.
*/
SELECT DISTINCT W.Essn, W.Pno, W.Hours
FROM works_on W, project P, department D
WHERE W.Hours > 20 AND W.Pno = P.Pnumber AND P.Dnum = 5
ORDER BY Essn;
--
-- JOINING 3 TABLES ---------------------------
--
/*(12B)
Write a query that consists of one block only.
For every employee who works less than 10 hours on any project that is controlled by the department he works for: Find the employee's lname, his department number, project number, the number of the department controlling it, and the number of hours he works on that project. Sort the results by lname.
*/
SELECT E.Lname, E.Dno, P.Pnumber, P.Dnum, W.Hours
FROM employee E, project P, works_on W
WHERE W.Hours < 10 AND E.Dno = P.Dnum
ORDER BY E.Lname;
--
-- JOINING 4 TABLES -------------------------
--
/*(13B)
For every employee who works on any project that is located in Houston: Find the employees ssn and lname, and the names of his/her dependent(s) and their relationship(s) to the employee. Notice that there will be one row per qualyfing dependent. Sort the results by employee lname.
*/
SELECT E.Ssn, E.Lname, D.Dependent_name, D.Relationship
FROM employee E, dependent D, works_on W, project P
WHERE E.Ssn = W.Essn AND E.Ssn = D.Essn AND W.Pno = P.Pnumber AND P.Plocation = 'Houston'
ORDER BY E.Lname;
--
-- SELF JOIN -------------------------------------------
-- 
/*(14B)
Write a query that consists of one block only.
For every employee who works for a department that is different from his supervisor's department: Find his ssn, lname, department number; and his supervisor's ssn, lname, and department number. Sort the results by ssn.  
*/
SELECT E1.Ssn, E1.Lname, E1.Dno, E2.Ssn, E2.Lname, E2.Dno
FROM employee E1, employee E2
WHERE E1.Dno <> E2.Dno AND E1.Super_ssn = E2.Ssn
ORDER BY E1.Ssn;
--
-- USING MORE THAN ONE RANGE VARIABLE ON ONE TABLE -------------------
--
/*(15B)
Find pairs of employee lname's such that the two employees in the pair work on the same project for the same number of hours. List every pair once only. Sort the result by the lname in the left column in the result. 
*/
SELECT E1.lname, E2.lname
FROM employee E1, employee E2, works_on W1, works_on W2
WHERE E1.Ssn = W1.Essn AND E2.Ssn = W2.Essn AND W1.Pno = W2.Pno AND W1.Hours = W2.Hours AND E1.Ssn < E2.Ssn
ORDER BY E1.lname;
--
/*(16B)
For every employee who has more than one dependent: Find the ssn, lname, and number of dependents. Sort the result by lname
*/
SELECT E.Ssn, E.lname, Count(D.Dependent_name)
FROM employee E, dependent D
WHERE E.Ssn = D.Essn AND (SELECT Count(D1.Dependent_name) FROM dependent D1 WHERE E.Ssn = D1.Essn) > 1
GROUP BY E.lname, E.Ssn
ORDER BY E.lname;
-- 
/*(17B)
For every project that has more than 2 employees working on and the total hours worked on it is less than 40: Find the project number, project name, number of employees working on it, and the total number of hours worked by all employees on that project. Sort the results by project number.
*/
SELECT P.Pnumber, P.Pname, Count(W.Essn), Sum(W.Hours)
FROM works_on W, project P
WHERE P.Pnumber = W.Pno AND (SELECT Count(Essn)
                             FROM works_on 
                             WHERE Pno = P.Pnumber) > 2
      AND (SELECT SUM(Hours)
           FROM works_on
           WHERE Pno = Pnumber) < 40
GROUP BY P.Pnumber, P.Pname
ORDER BY P.Pnumber;
--
-- CORRELATED SUBQUERY --------------------------------
--
/*(18B)
For every employee whose salary is above the average salary in his department: Find the dno, ssn, lname, and salary. Sort the results by department number.
*/
SELECT E.Dno, E.ssn, E.lname, E.salary
FROM employee E
WHERE (SELECT AVG(E1.salary)
       FROM employee E1
       WHERE E.Dno = E1.Dno) <= E.salary
ORDER BY E.Dno;
--
-- CORRELATED SUBQUERY -------------------------------
--
/*(19B)
For every employee who works for the research department but does not work on any one project for more than 20 hours: Find the ssn and lname. Sort the results by lname
*/
SELECT E.ssn, E.lname
FROM employee E, department D
WHERE D.Dnumber = E.Dno AND E.ssn NOT IN (SELECT E1.Ssn 
                                      FROM employee E1, works_on W
                                      WHERE E1.Ssn = W.Essn AND W.Hours > 20)
ORDER BY E.lname;
--
-- DIVISION ---------------------------------------------
--
/*(20B) Hint: This is a DIVISION query
For every employee who works on every project that is controlled by department 4: Find the ssn and lname. Sort the results by lname
*/
SELECT E.Ssn, E.lname
FROM employee E
WHERE NOT EXISTS ((SELECT P.Pnumber
                   FROM project P
                   WHERE P.Dnum = 4)
                  MINUS
                  (SELECT P.Pnumber
                   FROM project P, works_on W
                   WHERE W.Essn = E.Ssn AND
                         W.Pno = P.Pnumber AND
                         P.Dnum = 4))                         
ORDER BY E.lname;
--
SET ECHO OFF
SPOOL OFF


