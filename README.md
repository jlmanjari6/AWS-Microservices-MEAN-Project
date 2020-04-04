`

## Requirements**

This project requires to build a light-weight tourism application for Canada, and it must be accessible through mobile devices and desktops. It enables the user to search for popular tourist destinations across Canada and view various tourist attractions in any desired destination. If a user wants to book a ticket, the application should allow to login with their account or to sign-up for registration. Once the payment is processed; a ticket is generated for the user. Also, the dashboard of the application should be displayed with analytics or reports which must be generated from the server-side application using the data from the database. The project requires the client applications to be lightweight with primary requirements which are as follows [CITATION Ala20 \l 16393].

- Security – The application should be secure and must not be vulnerable to attacks. To keep the application secure, the project requires to implement 2-factor authentication, and store encrypted data in the database.
- Accuracy – The project requires that the search response data and dashboard analytics be displayed accurately.
- Efficiency – The application should be efficient enough by being available for the users providing instant responses. To achieve this, the project requires to implement the load balancer to handle the incoming traffic and implement the auto-scaling functionality to upscale/downscale the resources.
- Responsiveness – To achieve this, the project requires the client applications to be thin and lightweight and work on the devices to accommodate as many users as possible.

Apart from the above-mentioned requirements, there are few functional requirements for this project which are listed below.

- Entire server-side functionalities must be implemented on the cloud by developing loosely coupled modules that must be tested individually before integration.
- The application must be designed using containerization. A dummy card 1111-1111-1111-1111 must be used for transactions and the application must perform validation during the payment process.

## Functionalities**

Below listed are the four functional modules in our application along with the list of functionalities that are implemented in each module.

**Login/Signup module:

- User registration
- User log in and log out
- SMS-based two-factor authentication
- Forgot password
- Display user profile
- Session management

**Search module

- Search attractions in a location

**Booking module

- Display buses for a selected origin of travel
- Book ticket by payment
- Generate ticket to the user
- Provide ticket history for logged in user

**Analytics module

- Display top searched places
- Display number of bookings for each location in last week

## Architecture**

Below is the list of AWS cloud services that we used in our project; and we explained the purpose of each cloud service briefly.

1. **AWS Cognito:** We used Cognito to perform user authentication functionalities such as login, sign up, forgot password, and SMS based two-step verification
2. **S3 Bucket:** We used the S3 bucket for two purposes, one is to store the image details and the other is to host our web application
3. **CloudFront:** We used CloudFront on top of S3 to reduce the latency in API responses by caching and to provide secure connection between client and server.
4. **RDS:** We used RDS instance to configure and connect the MySQL database. Each service has a separately configured RDS instance with necessary MySQL tables required for that service
5. **CloudWatch:** CloudWatch helped us to maintain logs for several AWS services that we used in our project. We used the logs to troubleshoot our application
6. **ECR:** We used ECR to save the docker images of our services. Each service has its own ECR repository with the docker image of that service.
7. **ECS:** We used ECS to run and deploy our containers from the images in ECR. Each cluster of ECS has a service with one EC2 instance and two docker containers running on that instance.
8. **Load balancer:** We used load balancer with the help of dynamic port mapping for distributing the load between the two containers
9. **Autoscaling group:** As the starter account does not support autoscaling of containers, we performed the auto scaling of the EC2 instance of our service in the ECS cluster. We scaled the instances to a maximum of 3 against the average instance CPU utilization of 50%
10. **API Gateway:** We used API Gateway as a single-entry point for our services. It serves approximately 10,000 API requests per second
11. **AWS CodeBuild:** We used CodeBuild for CI/CD of our services that gets triggered by a GitHub repository push event.

