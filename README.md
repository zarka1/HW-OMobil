Customer.csv and payment.csv files must be in the folder, where the .jar file is.

Build Project:
*mvn clean package in the root folder,
*copy Customer.csv and payment.csv files in the target folder
*cd target
*java -cp .\otpMobilTest-1.0-SNAPSHOT.jar org.example.Main

The result files (application.log, report01.csv, report02.csv, top.csv) will be in the target folder.
