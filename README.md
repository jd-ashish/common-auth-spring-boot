****

Welcome to the Spring Boot common authentication system!
==========================================

**INTRODUCTIONS**

These projects will help you start a project without writing anything like auth controller, user controller swager implementation, JWT is implemented, and also maintained a well-structured package or folder.This project did not use any deprecated class or any methods.
Here, you will find three property files: one open property file that is accessible to everyone, two production and development property files, and a second or third file where you can configure your database or another setting depending on the project level.

If you would like to edit this page you'll find it located at:
`resource/index.html`

The frontend controller locations is:
`main.java.com.projects.app.controllers.frontend`

The API package location is
`main.java.com.projects.app.controllers.api`



*   Auth Controller
    1.  Login get user token with user details
    2.  Register user
    3.  Forgot password
    4.  Send **OTP**  to email for password reset or other purposes.
*   User Controller
    1. Get All Users list
    2. Create user
    3. Get Single user by **{user_id}**
    4. Update user by **{user_id}**
    5. Delete user by **{user_id}**
    6. Upload profile image
        1. Base64
        2. URL
* Role controller
  1. Your can create role via API
  2. You can update role via API.
  3. You can get all role list via API.
*   swagger-ui implemented
*   Jwt authentication token setup **{"Bearer "+token}**
"# common-auth-spring-boot" 
