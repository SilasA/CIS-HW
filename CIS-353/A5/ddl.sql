SPOOL ddl.out
SET ECHO ON
--
-- Author: Silas Agnew
--
-- IMPORTANT: use the names IC-1, IC-2, etc. as given below.
-- -------------------------------------------------------------------
--

DROP TABLE Orders CASCADE CONSTRAINTS;
DROP TABLE OrderLine CASCADE CONSTRAINTS;

CREATE TABLE Orders
(
orderNum    INTEGER     PRIMARY KEY,
priority    CHAR(10)    NOT NULL,
cost        INTEGER     NOT NULL,

/* IC-1: The priority is one of: high, medium, or low */
CONSTRAINT IC1 CHECK(priority='high' OR priority='medium' OR priority='low'),

/* IC-2: The cost of a high priority order is above 2000. */
CONSTRAINT IC2 CHECK(NOT (priority='high' AND cost < 2000)),

/* IC-3: The cost of a medium prority order is between 800 and 2200 (inclusive) */
CONSTRAINT IC3 CHECK(NOT (priority='medium' AND cost < 800 OR cost > 2200)),

/* IC-4: The cost of a low priority order is less than 1000 */
CONSTRAINT IC4 CHECK(NOT (priority='low' AND cost >= 1000))
);


CREATE TABLE OrderLine
(
orderNum    INTEGER,
lineNum     INTEGER,
item        CHAR(10)    NOT NULL,
quantity    INTEGER,
PRIMARY KEY (orderNum, lineNum),

/* IC-5: Every order line must belong to an order in the Order table.
   Also if an order is deleted then all its order lines must be deleted. */
CONSTRAINT IC5 FOREIGN KEY (orderNum)
REFERENCES Orders(orderNum)
ON DELETE CASCADE
);

-- -------------------------------------------------------------------
-- TESTING THE SCHEMA
-- -------------------------------------------------------------------
INSERT INTO Orders VALUES (10, 'high', 2400);
INSERT INTO Orders VALUES (20, 'high', 1900);
INSERT INTO Orders VALUES (30, 'high', 2100);
INSERT INTO Orders VALUES (40, 'medium',700);
INSERT INTO Orders VALUES (50, 'low',  1100);
INSERT INTO Orders VALUES (60, 'low',   900);
SELECT * FROM OrderLine;

INSERT INTO OrderLine VALUES (10, 1, 'AAA', 200);
INSERT INTO OrderLine VALUES (10, 2, 'BBB', 300);
INSERT INTO OrderLine VALUES (60, 1, 'CCC', 5);
INSERT INTO OrderLine VALUES (15, 1, 'AAA', 7);
SELECT * FROM OrderLine;

DELETE FROM Orders WHERE orderNum = 10;
SELECT * FROM Orders;
SELECT * FROM OrderLine;

SET ECHO OFF
SPOOL OFF







