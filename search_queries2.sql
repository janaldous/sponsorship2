-- Query: Computer Programming companies in London: 1298

SELECT COUNT(*) AS "Computer Programming Companies in London"
FROM PDFSPONSOR
WHERE INDUSTRY = 'Computer Programming'
				AND UPPER(TOWN) like '%LONDON%';

-- Goal 1: list all 1289 London tech companies
-- Goal 2: map all London tech companies with postcode -> TFL zone
 -- Current number of Recognized London Tech Companies: 192

SELECT COUNT(*) AS "London Tech Companies with Name Matching"
FROM COMPANY_SPONSOR CS
JOIN PDFSPONSOR PS ON CS.PDF_SPONSOR_ID = PS.ID
WHERE CS.NAME_MATCHES IS TRUE
				AND PS.INDUSTRY = 'Computer Programming'
				AND UPPER(PS.TOWN) like '%LONDON%';

/**
* To do:
* Clean data
* - Increase London tech companies with name matching to 100%, currently at (192/1298)
* Process data
* - Map London tech companies postcode to nearest TFL station -> TFL zone
* - Be able to filter companies by TFL zone and important areas (City of London, Canary Wharf, Scilicon Roundabout)
*/