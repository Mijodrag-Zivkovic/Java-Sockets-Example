# Java-Sockets-Example

This project was a part of introduction to Web Development.
Purpose of this project was to write simple app that saves quotes and quote authors' names, using java sockets.
App consists of two services:
- The Main one, which parses HTTP requests from the user and returns all quotes (port 8080)
- The helper one, which parses HTTP requests sent by the Main service, and returns quote of the day from it's own premade list of quotes (port 8081)
Always run the Helper service first!
Originally written on 28.3.2021.
