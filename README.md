# Java oauth2
Development an authorization Server with OAuth2

## Docker
Install o docker, so execute the code line below:
 - Create network: 
```Docker Network
 docker network create severoauth2
 ```
 - Create the image of Postgre: 
```Docker Container
docker run -d --name authenticationserveroauth2 --network severoauth2 -p 5434:5432 -e POSTGRES_PASSWORD=oauthserver -e POSTGRES_USER=oauthserver -e POSTGRES_DB=clientoauth postgres:16.3
```
## Create client table
Save the credentials in client table
``` SQl
create table client(
id UUID not null primary key,
client_id varchar(150) not null,
client_secret varchar(400) not null,
redirect_uri varchar(200) not null,
scope varchar(50) 
)
```
