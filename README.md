# HTML Analyzer

Build a web application that allows user to conduct HTML Page analysis.

### Tech Stack

* The backend is developed using Apache CXF, JAX-RS, Spring Bean, Jsoup. 
* The frond end is developed using JQuery and Bootstrap(CSS).
* Gradle is used as build tool for application.


### Running Application

You can package and run the backend application by executing the following command:

* gradlew appStart

This will start an instance running on the default port 8080.

You can access running application in browser through http://localhost:8080/service/

You can stop the application by running below command:

* gradlew appStop

Note: You should have JDK 8 in classpath

### AVAILABLE REST API
The service is exposed via rest on following api.

 The html page can be analysed using below REST API. 
* http://localhost:8080/service/services/rest/api/analyze?url=<url> , where Where url is passed as query parameter.

 ```json
{
    "url": "https://www.pb.com",
    "htmlVersion": "HTML5",
    "title": "Pitney Bowes US | Digital Commerce, BI, Shipping & Mailing",
    "headers": {
        "h1": 1,
        "h2": 6,
        "h3": 6,
        "h4": 0,
        "h5": 0,
        "h6": 4
    },
    "linkCounts": {
        "internal": 3,
        "external": 252
    },
    "hasLogin": false
}
```
The external link in html page can be analysed using below REST API.

* http://localhost:8080/service/services/rest/api/external?url=<url> , Where url is passed as query parameter.

 ```json
[
    {
        "url": "https://www.google.co.in/imghp?hl=en&tab=wi",
        "reachable": true
    },
    {
        "url": "https://accounts.google.com/ServiceLogin?hl=en&passive=true&continue=https://www.google.com/",
        "reachable": true
    }, 
   
    {
        "url": "http://www.abc.com/services/",
        "reachable": false,
        "failureReason": "java.net.UnknownHostException: www.abc.com"
    }
]
```
 
 In case of error the json is returned as below:
 
 ```json
  {
     "value": "java.net.UnknownHostException: www.abc.com"
 }
```

The json results are used by the frontend to display results.


 
 ### Assumptions and Design Decisions
 
**Counting Links**: Only `<a>` elements with a href attribute are considered for counting links. 
 A link is considered a internal link if the domain is the same as the one in the provided url, 
 otherwise it is considered as external. Only http or https protocol is considered.
 
 
**Login Form**: A page is considered to have a login form if contains a `<form>` element that:
1. Contains exactly one input with type=password and 
2. it contains exactly one text field or exactly one email field


**External Link**: The link is considered accessible if it can be accessed directly or through redirection.
Performance considerations:
The accessibility of link is done in parallel using multiple threads for better performance.   
If the request to access link is not served within 5 sec, the request will time out.
The result is collected in non-blocking manner using Executor Completion Service. It uses non-blocking queue for taking out th result from Future.


**HTML Version**: The structure is prepared for the server to recognize and name all the html versions in [this page](https://www.w3.org/QA/2002/04/valid-dtd-list.html).
While this is sufficient for the exercise, Additional entries may be added in actual production quality code.

**The frontend**: Front end is quite intuitive, it first presents the page with text box for url. On clicking submit it returns result in tabular form through AJAX call.
I have used spinner to indicate for request in progress. 
When the result is displayed, a link is provided along side "External Link Counts", which can clicked to bring details of all external links for its reachability.

**Test Cases**: The test cases have been written at unit and integration level. Mockito is used for mocking jsoup calls.
Additional test cases could be written to deploy application in jetty and test rest endpoints. This is not done in this exercise.
UI Based end to end test cases will also be needed for production quality artifacts.