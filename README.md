# cookie-mock

the platform to mock every thing you want

## road map

- [] service management
    - a 'service' equals one mock server
    - random base url
- [] endpoints management
    - can export as document
- [] cases management
    - can build multiple cases for each endpoint with different conditions
- [] modify authorization for editors
    - mark project identifier as tag
    - setup ACL for each user to define which project allowed accessing 
> 1 service : n * endpoints : n * n cases

## next...

- [x] write some test to verify service/endpoint/cases works well
- [x] find a way to parse condition easily
- build basic ui for this
- add authorization and acl logic
