# Classes
This document is a light overview of the java classes this project contains. The project is a small static html site and REST api that's containerized and deployed on kubernetes. the class structure is as follows: 

```
src/main
├── java
│   ├── exceptions
│   │   └── ...
│   ├── search
│   │   ├── Main.java
│   │   ├── api
│   │   │   └── ...
│   │   └── site
│   │       └── ...
│   └── txt
│       └── ...
└── resources
    ├── static
    │   └── *.css
    └── templates
        └── *.html
```

The `search` package contains the main method and two packages: `api` and `site`, for the REST API and static site, respectively. The `txt` package is a package for business logic, e.g. building results returned by the controller. As would be expected, `exceptions` contains custom exceptions for the project Test for both are mirrored in the `test` package tree (not show). 

## txt package 
this package contains the backend logic for finding the occurances of a word in a string or file.

**FindWord**
_Integer countOccurances(InputStream inStrFile, String term)_
 - reads through file stream for each line
 - transforms the line and search term into lower case, no-punctuation versions to 'normalize' input for search
 - loops through the line and counts the number of times the word appears
 - returns the number of hits of the search term

_Integer countOccurances(String str, String term)_
 - transforms the input string and search term into lower case, no-punctuation versions to 'normalize' input for search
 - loops through the line and counts the number of times the word appears
 - returns the number of hits of the search term

_String normalizeLine(String line)_
 - cleans the input of punctuation and non-ASCII characters
 - returns the input in lower case

_String cleanLine(String line)_
 - returns the input, cleaned punctuation and non-ASCII characters

_int hits(String str, String term)_
 - breaks input string into array
 - searches through array, counting each time the string found matches the search term
 - returns the number of hits

## search package 
this packages contains the api and site packages, which handle the REST API and the static site front end. It also contains the main method.

### api subpackage
This subpackage contains the logic for serving the REST api endpoints.

**Results** - data class for collecting results from the counting/searching methods; used to pass contents that will become the response body back to the REST controller
_String/Integer getters and setters_
 - String term - search term
 - Integer count - number of occurances found
 - String input - input string or filename

**SearchAPI** - provides the REST endpoints, calls backend methods, and configures response
_ResponseEntity<Results> api_ - method for the GET `/api/` endpoint
 - returns a greeting as a json response body

_ResponseEntity<Results> file_ - method for POST`/api/file/` endpoint
 - calls the countOccurances method on the searcher ('FindWord') class, which returns the number of hits it found for the search term
 - formats input and results as an response object

_ResponseEntity<Results> string_ - method for POST `/api/string/` endpoint
 - calls the countOccurances method on the searcher ('FindWord') class, which returns the number of hits it found for the search term
 - formats input and results as an response object

### site subpackage
This subpacakge contains the logic for serving the static pages.

**Pages** - provides controllers for serving static pages
_String docs_
 - returns the template name for the docs page so Spring Boot can serve it

_String resources_
 - returns the template name for the resources page so Spring Boot can serve it

_String index_
 - returns the template name for the index page so Spring Boot can serve it

## exceptions package
**FileContentException** - used when there is an issue with content in an already-opened file stream
_String readWriteError_
    - sets the exception message for this custom exception

**FileStreamException** - used when there is an issue opening a file into a filestream
_String readWriteError_ 
    - sets the exception message for this custom exception

