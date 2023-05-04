# KanDo
🗨️ A Kanban Board created with...

### Project Description

The JavaFX project management app is designed to help teams manage their projects and tasks efficiently. The app is built using JavaFX and utilizes Firebase Firestore as its database system. The app has three levels of users, admin, manager, and team member, each with their own specific access and functionality.

### Requirements

##### Functional Requirements

The app must allow the creation of new users and authentication for existing users.
The app must allow the creation of new projects by managers and admin users.
The app must allow the addition of new tasks to projects by managers and team members.
The app must allow the change of task status from To Do to Doing to Done by team members.
The app must allow the deletion of tasks by managers and admin users.

##### Non-functional Requirements

The app must be responsive and work seamlessly on different devices.
The app must have a user-friendly interface with easy navigation.
The app must have proper error handling and validation to prevent errors and data loss.
The app must have proper security measures to ensure user data privacy and prevent unauthorized access.

### Specifications

##### Backend Class

The app has one backend class called Ticket, which represents a single task.

###### Ticket Properties

Task Name
Task Description
Task Status (To Do, Doing, Done)
Task Type

###### Ticket Methods

Constructors
Getter and Setter methods for all properties
toString

##### Database Objects

The app has two database objects: Tickets and Projects. Both of these objects are stored in Firebase Firestore.

###### Tickets Collection

The Tickets collection stores all tasks created in the app. Each document in the collection represents a single task and contains the following properties:
Project Name
Task Name
Task Description
Task Status (To Do, Doing, Done)

###### Projects Collection

The Projects collection stores all projects created in the app. Each document in the collection represents a single project and contains the following properties:
Project Name
Project Description
Start Date
End Date

##### User Access Levels

The app has three levels of users: admin, manager, and team member. Each level has specific access and functionality.

###### Admin User

Can create new users
Can add new projects
Can add new tasks to projects
Can change task status from To Do to Doing to Done
Can delete tasks

###### Manager User

Can add new projects
Can add new tasks to projects
Can change task status from To Do to Doing to Done
Can delete tasks

###### Team Member User

Can add new tasks to projects
Can change task status from To Do to Doing to Done
Can delete tasks

### Conclusion

The JavaFX project management app is a comprehensive tool for teams to manage their projects and tasks efficiently. With its user-friendly interface, seamless functionality, and proper security measures, the app ensures that teams can collaborate effectively while maintaining the privacy and integrity of their data.
