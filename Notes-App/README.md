
# Notes APP
This is java based android notes app which lets user to create quick notes. This App is built by implementing MVVM architecture. <br>
Model — View — ViewModel (MVVM) is the industry-recognized software architecture pattern that overcomes all drawbacks of MVP and MVC design patterns. MVVM suggests separating the data presentation logic(Views or UI) from the core business logic part of the application. <br>

### User can -

  * Add note
  * Edit note
  * Delete note
  * Search note
 
 Add Note | Edit Note | Delete Note | Search Note
------------ | ------------- | ------------- | -------------

### MVVM Architecture



 * **Entity:** Annotated class that describes a database table when working with Room.

 * **SQLite database:** On device storage. The Room persistence library creates and maintains this database for you.

 * **DAO:** Data access object. A mapping of SQL queries to functions. When you use a DAO, you call the methods, and Room takes care of the rest.

 * **Room database:** Simplifies database work and serves as an access point to the underlying SQLite database (hides SQLiteOpenHelper). The Room database uses the DAO to issue queries to the SQLite database.

 * **Repository:** Used to manage multiple data sources.

 * **ViewModel:** Acts as a communication center between the Repository (data) and the UI. The UI no longer needs to worry about the origin of the data. ViewModel instances survive Activity/Fragment recreation.

 * **LiveData:** A data holder class that can be observed. Always holds/caches the latest version of data, and notifies its observers when data has changed. LiveData is lifecycle aware. UI components just observe relevant data and don't stop or resume observation. LiveData automatically manages all of this since it's aware of the relevant lifecycle status changes while observing.
 
