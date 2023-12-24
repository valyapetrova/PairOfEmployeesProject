## Company system analyzer for pair of employees for Sirma Academy Final Exam

# Specific requirements
Create an application that identifies the pair of employees who
have worked together on common projects for the longest period
of time and the time for each of those projects. Use CSV file with data in the following format:
_EmpID, ProjectID, DateFrom, DateTo_ <br />

## Features 
1. CRUD for Employees
2. CRUD for Projects
3. DATABASE keeping all of the application's info, updating after every post/get method<br />
   -Employees table<br />
   -Projects table<br />
   -work_history<br />
4.FileService - reading CSV file from the requirement.<br />
However, the core of my application is stored in the DB and driven by queries to it, so i don't really use the file.
In my opinion DB is better and more secure for the purpose of the project. It is also simplifying the code.
5. Pair of employees filter - created with DB Query located in WorkHistoryRepository<br />
6.NULL == today - if the post method receives NULL date, it will transform it to today's date by default. <br />

## API 
-Employee "/employee"<br />
/create - creating employee<br />
/get/{id} - get employee by id<br />
/get/all - get list of all employees<br />
/delete/{id} - deleting employee by id<br />
/edit/{id} - edit employee by id<br />

-Project "/project" <br />
Similar to employee.<br />

-WorkHistory

... Тази страница е в ремонт. Предстои качване на бд информацията и редакции по кода..




