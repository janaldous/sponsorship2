-- Query: Computer Programming companies in London: 1298

SELECT COUNT(*) AS "Computer Programming Companies in London"
FROM PDFSPONSOR
WHERE INDUSTRY = 'Computer Programming'
				AND UPPER(TOWN) like '%LONDON%';

-- Goal 1: list all 1289 London tech companies
-- Goal 2: map all London tech companies with postcode -> TFL zone
 -- Current number of Recognized London Tech Companies: 1228/1289 (~92%)

SELECT COUNT(*) AS "London Tech Companies with Name Matching"
FROM COMPANY_SPONSOR CS
JOIN PDFSPONSOR PS ON CS.PDF_SPONSOR_ID = PS.ID
WHERE (CS.NAME_MATCHES IS TRUE
							OR CS.FUZZY_MATCHES IS TRUE)
				AND PS.INDUSTRY = 'Computer Programming'
				AND UPPER(PS.TOWN) like '%LONDON%';

/**
* To do:
* Clean data
* - Increase London tech companies with name matching to 100%, currently at (192/1298)
* Process data
* - Map London tech companies postcode to nearest TFL station -> TFL zone
* - Be able to filter companies by TFL zone and important areas (City of London, Canary Wharf, Scilicon Roundabout)
*/ -- Compare name from pdf sponsor and name from company house sponsor

SELECT CHE.COMPANY_NAME AS "Company House Name",
	PS.COMPANY_NAME AS "PDF Sponsor Name"
FROM COMPANY_SPONSOR CS
JOIN PDFSPONSOR PS ON CS.PDF_SPONSOR_ID = PS.ID
JOIN COMPANY_HOUSE_ENTRY CHE ON CS.COMPANY_HOUSE_ENTRY_ID = CHE.ID
WHERE CS.NAME_MATCHES IS NOT TRUE
				AND PS.INDUSTRY = 'Computer Programming'
				AND UPPER(PS.TOWN) like '%LONDON%'
LIMIT 5;

/**
* TODO to clean data:
*	- normalize: add new column, normalized company name, to company house name and pdf sponsor name
*   ltd -> limited
*/
SELECT COUNT(*)
FROM COMPANY_SPONSOR CS
JOIN PDFSPONSOR PS ON CS.PDF_SPONSOR_ID = PS.ID
JOIN COMPANY_HOUSE_ENTRY CHE ON CS.COMPANY_HOUSE_ENTRY_ID = CHE.ID
WHERE UPPER(CHE.NORMALIZED_NAME) = UPPER(PS.NORMALIZED_NAME)
				AND PS.INDUSTRY = 'Computer Programming'
				AND UPPER(PS.TOWN) like '%LONDON%';

-- Normalization improvement: 192 -> 260 (+68)
 -- manually remove trailing '.' such as  Ltd. to Ltd

UPDATE COMPANY_HOUSE_ENTRY
SET NORMALIZED_NAME = TRIM(TRAILING '.' FROM NORMALIZED_NAME)
WHERE UPPER(NORMALIZED_NAME) LIKE '%LTD.';


UPDATE PDFSPONSOR
SET NORMALIZED_NAME = TRIM(TRAILING '.' FROM NORMALIZED_NAME)
WHERE UPPER(NORMALIZED_NAME) LIKE '%LTD.';

-- manually set name_matches = true in company_sponsor table

UPDATE COMPANY_SPONSOR AS CS
SET NAME_MATCHES = TRUE
FROM PDFSPONSOR PS,
	COMPANY_HOUSE_ENTRY CHE
WHERE UPPER(CHE.NORMALIZED_NAME) = UPPER(PS.NORMALIZED_NAME)
				AND CS.PDF_SPONSOR_ID = PS.ID
				AND CS.COMPANY_HOUSE_ENTRY_ID = CHE.ID
				AND CS.NAME_MATCHES IS NOT TRUE;

-- After normalization: 721 -> 1006
-- After fuzzy compare: 1006 -> 1042 (+36)
 -- London Tech Companies: fuzzy compare: 260 -> 276

SELECT COUNT(*)
FROM COMPANY_SPONSOR;

-- 1042 > 2904/3056

SELECT COUNT(*)
FROM COMPANY_SPONSOR
WHERE FUZZY_MATCHES IS TRUE
				OR NAME_MATCHES IS TRUE;

-- Inspect differences between normalized company names

SELECT CHE.COMPANY_NAME,
	CHE.NORMALIZED_NAME,
	PS.COMPANY_NAME,
	PS.NORMALIZED_NAME
FROM COMPANY_SPONSOR CS
JOIN PDFSPONSOR PS ON CS.PDF_SPONSOR_ID = PS.ID
JOIN COMPANY_HOUSE_ENTRY CHE ON CS.COMPANY_HOUSE_ENTRY_ID = CHE.ID
WHERE (NAME_MATCHES IS NULL
							OR NAME_MATCHES IS FALSE)
				AND (FUZZY_MATCHES IS NULL
									OR FUZZY_MATCHES IS FALSE);