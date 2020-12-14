# Android

The practical assignments (server-side and client-side) aim towards building a mobile and distributed, personalised diary application for keeping track of movies you watched and their details. The mobile app will be a movie memoir that allows people to create, delete, add or view a collection of memories that you had about the movies.
The mobile app will interact with the RESTful web service that you create in Assignment 1[MovieMemory], and retrieve some useful and related information from public web APIs.

LogIn Screen:

![image](https://user-images.githubusercontent.com/44027826/102030700-d68a1980-3e07-11eb-84d3-001cd4e7cec7.png)

Signup Screen with scroll view feature:

![image](https://user-images.githubusercontent.com/44027826/102030742-ffaaaa00-3e07-11eb-8359-0c3bc8f88793.png)

Home Screen: First name: Lyric, App title: MovieApp; Current Date: 2020-05-31 and top five movie names, their release dates and rating scores given by the user: Lyric

![image](https://user-images.githubusercontent.com/44027826/102030794-27017700-3e08-11eb-9871-f78fcd050563.png)


Navigation drawer:

![image](https://user-images.githubusercontent.com/44027826/102030809-37b1ed00-3e08-11eb-8bb4-93d23110d4b2.png)


Search Movie:

![image](https://user-images.githubusercontent.com/44027826/102030833-4b5d5380-3e08-11eb-9915-5d2eeb98d263.png)


Searched Movie: Harry Potter – Image, Movie Name and Release Year [imdb or google provide only year]

![image](https://user-images.githubusercontent.com/44027826/102030856-5d3ef680-3e08-11eb-82b5-101a1caddf02.png)


If any item is clicked, then it would go movie details page:

![image](https://user-images.githubusercontent.com/44027826/102030918-895a7780-3e08-11eb-809e-133819e2cf5e.png)


If the add to watchlist button is clicked, then using Android Room watchlist table will be saved, containing the name of the movie, release year, and date and time when added in the table.
When a user would try to add the movie again, it would show a toast message: “Movie has been added to the watchlist.”
When the add to memoir button is clicked: A scroll enabled screen

![image](https://user-images.githubusercontent.com/44027826/102031006-c6266e80-3e08-11eb-8ad9-586738b58e34.png)


If we scroll the screen:

![image](https://user-images.githubusercontent.com/44027826/102031054-e8b88780-3e08-11eb-9549-59b2d4d07c0f.png)

Watchlist screen using recycle view: Movie title, release year and last modified date time.

![image](https://user-images.githubusercontent.com/44027826/102031094-0128a200-3e09-11eb-85e6-2d51a65568a9.png)

View button of the movie the dark knight rises is clicked: Add to watchlist button is disabled. Movie rating is not converted in five stars because it is not written for this functionality, and it has been retrieved from google search API. But on another screen, five stars have been considered.

![image](https://user-images.githubusercontent.com/44027826/102031157-37662180-3e09-11eb-8703-3542e201ada6.png)


If user wants to add any cinema, he can also do that:

![image](https://user-images.githubusercontent.com/44027826/102031178-4f3da580-3e09-11eb-9a65-9aaa1c32ad04.png)



Following screen for movie memoir: User can sort the list based on three sorting options like date, user rating scores as star and public review score.  

![image](https://user-images.githubusercontent.com/44027826/102031223-6bd9dd80-3e09-11eb-85ef-f238fc25c55b.png)

![image](https://user-images.githubusercontent.com/44027826/102031239-785e3600-3e09-11eb-85c1-9b4e61222617.png)


Report:
Pie Chart:

![image](https://user-images.githubusercontent.com/44027826/102031265-87dd7f00-3e09-11eb-8579-8ab8b19e1432.png)

Bar Graph:

![image](https://user-images.githubusercontent.com/44027826/102031283-988df500-3e09-11eb-9ea6-f00885258e89.png)


![image](https://user-images.githubusercontent.com/44027826/102031296-a0e63000-3e09-11eb-82de-d4a8d343c1be.png)















