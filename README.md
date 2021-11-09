# E-Commerce App
-----


Spring Security, Spring Boot, java 8, H2, Angular 11

#Requirements
-----

  JDK 1.8
  Docker
  Maven

# Build
-----

1. Clone this repo

2. Open the directory oauth2-google

3. Open Powershell

4. Build the image

   docker image build . -t demo/e-commerce-backend
   
5. Run the image 

   docker run -p 8080:8080 demo/e-commerce-backend
