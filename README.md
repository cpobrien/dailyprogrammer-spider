# Daily Programmer Spider
The purpose of this project is to create a spider that goes through [/r/dailyprogrammer](https://www.reddit.com/r/dailyprogrammer/), collecting the minimal information in each crawl to fill DynamoDB tables with this information. This spider will become a Lambda application that will be the backbone for my Daily Programmer leaderboard project.

I can't imagine why some random would want to run this project, but if you do, you can look at the information below to see how to do it!

## Running

It's not yet a Lambda project, so don't need to do much. 
1. Have a `config.properties` file at the base of your project. Make sure the following is filled out:
  
    ```
     aws.accessKey=<...>
     aws.secretKey=<...>
     reddit.username=<...>
     reddit.password=<...>
     reddit.clientId=<...>
     reddit.clientSecret=<...>
     ```
1. On your AWS account, make sure you have a **Post** and **User** table
1. Type `gradle run` and it should run!