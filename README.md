# LetsS3

## A simple File upload service implemented using Spring and HTML

A pun project done on a weekend when feeling bored, this projects can be used to upload files to S3 and store the meta data in DB

## Changes related to DB :

Check out the source code and you can access the project by changing the MySQL DB name, MySQL user name and password in application.properties

### Changes related to S3 :
1. Change the access key, secret key and region in application.properties 
2. Change the bucket name in FileService.java. Look for the variable `bucketName` and change it with your actual bucket name

## Access the project using localhost:8080/index.html

## Links

I faced multiple issues when connecting this service to S3. Attached [link](https://stackoverflow.com/questions/71080354/getting-the-bucket-does-not-allow-acls-error) helpmed a lot

## Also refer the below attached photos and videos for demo


![DB_Image](https://github.com/user-attachments/assets/5c2153fa-4c33-4792-95f7-908ac6553845)




https://github.com/user-attachments/assets/027b789b-2e26-4269-8fe6-9a1846dbf896
