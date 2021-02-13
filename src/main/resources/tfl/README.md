Sources:
- Tube stations: <https://tfl.gov.uk/info-for/open-data-users/api-documentation>
	- <http://tfl.gov.uk/tfl/syndication/feeds/stations.kml>
	- converted raw .kml to .geojson (see ``tfl/stations.geojson``) (used <https://mygeodata.cloud/result> to convert)
- <https://content.tfl.gov.uk/example-api-requests.pdf>
- <https://en.wikipedia.org/wiki/List_of_stations_in_London_fare_zone_1> - to get stations in Zone 1. See below for jQuery used to get stations as csv

```js
JSON.stringify($(".wikitable > tbody > tr")
	.filter(function( index ) {
    	return $( "td:nth-child(3)", this ).text() === "London Underground";
  	})
  	.get()
  	.map(x => ({
  		stationName: $("td:nth-child(1)", x).text().trim(), 
  		localAuthority: $("td:nth-child(2)", x).text()
  	}))
  	.map(x => x.stationName + "," + x.localAuthority)
  	.join("\n");
);
```