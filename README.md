# The-Whoa-Weight-App
Application created for ePortfolio

Created by Allan Gibbons for SNHU Bachelor's in Computer Science.

A simple application that allows the user to create a profile and log in. After login, the application accepts input from the user to store their information, 
including the date, current weight and the goal weight that they would like to achieve. Although it is not an in depth application, it is used to 
demonstate the use of a databse, security, and functionality that I created for this project.

I ran into some difficultis with the project. I started by using SQLite for Android, but found it clumsy and not very secure. So I implimented the Firebase database into the build.
Additionally, while syncing the gradle, for the inclusion of Firebase, I noticed that the project can be broken entirely by not placing the correct line of code where it belongs.
Trial and error, and a lot of research, helped to discover where the bugs were, and subsequently eliminated.  

The application features activities that range from login credentials to information gathering for a database. 
The user has the ability to create a profile that is saved on the web-based database, Firebase. This is a Google owned application and was chosen for security purposes. 

Some notes about the creation of the Whoa Weight Application.

1. The application is created using Java.
2. The application cannot be copied and pasted without updating the environments Gradle files. 
3. Firebase secures the applications user's by way of allowing the "Administrator" to hold the key to their profiles instead of a dedicated
   file on the device. This makes it harder for would-be nefarious individuals to access the data. 
4. Installing this project onto a device is not easily achievable in it's current state. I do not intend to publish it as a functional application, but more of a
   detailed example of my ability and talent in application development.
5. However, it CAN be opened using Android Studio in order to understand how it was coded and how it can be recreated, if necessary.
