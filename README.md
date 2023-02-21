# SQLite_Viewer
A simple GUI program that connects to a SQL database and displays data in a table.

https://hyperskill.org/projects/171

This is a Java Swing application built using Java, Swing API, and SQLite3 as my choice of SQL. It is a simple program that displays all the tables in a drop down combo box and has a text area to execute SQL statements. The program auto generates a SELECT * statement for the current table as a template. The open file button verifies before attempting an SQL connection if a file exists in the project directory. A pop up for errors appears depending on the error as well as sections of the application are disabled to prevent misuse/errors. When the program successfully opens a file, the execute button is enabled allowing for SQL execution.
