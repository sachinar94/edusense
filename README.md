## Steps to run

1. first of all clone repository from Github then open terminal in project directory

    `~/edusense` //project main directory name

2. Have to run our backend first

    `docker-compose -f src/main/docker/mysql.up`

3. It takes few time to run is in docker container after that open another tab for run our front end

    `npm install` - to install dependencies

    `sudo ./mvnw` - run user end

4. After some time user view is on port 8080. For run browserSync to get instant changes open another tab and command

    `yarn start`

5. Now Application is now is on port 9000 with browserSync.

    login with if by default accounts are:

    for user login - UserName: 'user', Password: 'user'

    for admin login - Username: 'admin' Password: 'admin'
