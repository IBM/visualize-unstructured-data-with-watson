[![Build Status](https://travis-ci.org/IBM/visualize-unstructured-text-with-watson.svg?branch=master)](https://travis-ci.org/IBM/visualize-unstructured-text-with-watson)

!!! WORK IN PROGRESS !!!

# Visualize Unstructured Text Using Watson Natural Language Understanding

Spring Boot app which allows user to upload a file and see Watson NLU results for that document in a bubble cloud.

Configure your Watson NLU username and password in NLUCredentials.java.

App uses Apache Tika to extract text from documents. https://tika.apache.org/1.16/formats.html

Excellent resource on bubble cloud by Jim Vallandingham here: http://vallandingham.me/building_a_bubble_cloud.html

In this Code Pattern, we will [create something] using [technologies] and [components]. [Explain briefly how things work]. [Give acknowledgements to others if necessary]

When the reader has completed this Code Pattern, they will understand how to:

* [goal 1]
* [goal 2]
* [goal 3]
* [goal 4]

![](doc/source/images/architecture.png)

## Flow

1. User configures credentials for the Watson NLU service and starts the app.
2. User selects document file to proecess and load.
3. Document text is extracted using Apache Tika.
4. Extracted data is passed to Watson NLU for enrichment.
5. Enriched data is visualized in the UI using the D3.js library.

# Watch the Video

!!! COMING 

# Steps

1. [Clone the repo](#1-clone-the-repo)
2. [Create Watson services with IBM Cloud](#2-create-watson-services-with-ibm-cloud)
3. [Configure credentials](#3-configure-credentials)
4. [Run the application](#4-run-the-application)

### 1. Clone the repo

Clone the `visualize-unstructured-text-with-watson` repo locally. In a terminal, run:

```
$ git clone https://github.com/IBM/visualize-unstructured-text-with-watson
```

### 2. Create Watson services with IBM Cloud

Create the following services:

* [**Watson Natural Language Understanding**](https://console.ng.bluemix.net/catalog/services/natural-language-understanding)

### 3. Configure credentials

The credentials for IBM Cloud services, can be found in the ``Services`` menu in IBM Cloud, by selecting the ``Service Credentials`` option for each service.

Use those values to update the `config.properties` file located in the `src/main/resources` directory. Replace the default values with the appropriate credentials (either API key, or username/password). Note that quotes are not required.

```
# Watson Natural Language Understanding
NATURAL_LANGUAGE_UNDERSTANDING_URL=https://gateway.watsonplatform.net/natural-language-understanding/api
## Un-comment and use either username+password or IAM apikey.
NATURAL_LANGUAGE_UNDERSTANDING_IAM_APIKEY=<add_nlu_iam_apikey>
#NATURAL_LANGUAGE_UNDERSTANDING_USERNAME=<add_nlu_username>
#NATURAL_LANGUAGE_UNDERSTANDING_PASSWORD=<add_nlu_password>
```

### 4. Run the application

1. Install and package the Java app by running the following Maven command:

```
mvn clean install
```

> Note: if you do not already have Maven installed and configured locally, you can substitute the `mvn` portion of the command with either `./mvnw` (on Linux or Mac), or `mvnw.cmd` (on Windows). This will run a version of Maven that has been setup for only this repo.

2. Start the app by running:

```
java -jar target/nlu-visual-1.0.jar
``` 

3. Browse to `http://localhost:8080` to see the app.

4. To start the visualization process, select and upload a document from your local file system. The file can be in `.doc`, `.docx`, `.txt`, or `.pdf` format. For your convenience, we have included 2 sample poems located in the [data](/data) subdirectory of this repo. 

# Sample output

From the home page, you will be prompted to choose a file from your local system:

![](doc/source/images/home-page.png)

Select a file and press the `Upload` button. In this example, the file "The Raven.pdf" was selected from the `data/examples` folder:

![](doc/source/images/raven-concepts.png)

If you click on the `Sentiments` tab, you will see:

![](doc/source/images/raven-sentiments.png)

## License

This code pattern is licensed under the Apache License, Version 2. Separate third-party code objects invoked within this code pattern are licensed by their respective providers pursuant to their own separate licenses. Contributions are subject to the [Developer Certificate of Origin, Version 1.1](https://developercertificate.org/) and the [Apache License, Version 2](http://www.apache.org/licenses/LICENSE-2.0.txt).

[Apache License FAQ](http://www.apache.org/foundation/license-faq.html#WhatDoesItMEAN)