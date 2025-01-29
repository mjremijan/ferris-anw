/**
 * Author:  Michael Remijan <mjremijan@yahoo.com>
 * Created: Jan 28, 2025
 */
-- assign an identity column attribute to an INTEGER
-- column, and also define a primary key constraint
-- on the column
CREATE TABLE PEOPLE
	(PERSON_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY
	CONSTRAINT PEOPLE_PK PRIMARY KEY, PERSON VARCHAR(26));
