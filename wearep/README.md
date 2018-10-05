# uppgBMS
Gruppuppgift Datorkommunikation och Nätverk
Deltagare: Billy, Malin, Sten

GetCoords class:

  Methods:
    setURL()
        Combines the url to get the geocoding json data with the users chosen city
        
    collectJSON()
    	Establishes a connection to the URL and collects the JSON data in a string
    	
    parseJSON()
    	parses the JSON data into a JSON object, and then creates various JSON objects
    	and an array to extract the desired data (coordinates)
    	
    getCity()
    	returns a String containing the chosen city
    	
    getLongitude()
    	IF longitude isn't null it returns a Double containing the longitude coodrinate 
    	for the chosen city. (latitude is null if run() has not been executed)
    	
    getLatitude()
    	IF latitude isn't null it returns a Double containing the latitude coodrinate 
    	for the chosen city. (latitude is null if run() has not been executed)
    	
    run(String tmpCity)
    	Sets the City, runs the methods: setURL(), collectJSON(), parseJSON()
    	
        