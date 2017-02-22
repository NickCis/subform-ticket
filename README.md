# subform-ticket
  jBpm get sub-form demo. Demonstrates the use of the REST API to get the field details of the different forms configured for a process definition, and the challenge to get the sub-form fields.

# Overview
There is 2 main cases where a developer might select to create sub-forms in a business process task:
  * When the input data from a form uses data from 2 different contexts, like the address for a user: The user has its metadata, and the address has its metadata. This example often will give us a 1-to-1 relationship between 2 different models. In the demo presented in this project, we have an IT support ticket that happens to include a _Software Issue_ later in the process.
  * When the input data from a form uses parent-detail data, like an invoice and its items. The invoice has a collection of items attached to it and they are presented in a sub-form. In the demo presented in this project, the _Software Issue_ has a list of _Components_ that will be filled up in a task of the process.

The objective is to find a way through the KIE SERVER APIs to gather information about the forms and its fields: KIE SERVER API already has a couple endpoints that allow these operatios:
  * server/containers/{id}/forms/processes/{pId} - GET : Responds with the form metadata and fields to start a process (the process form)
  * server/containers/{id}/forms/tasks/{tInstanceId} - GET : Responds with the form metadata and fields to continue an active task (task form)

The response contents are straight and fine when using basic types, but when using custom complex types and subforms we get information that obscures the content of the inner description, like when getting information about a field bound to a subform, we only get:

```JSON
{
"defaultSubform": "issue-subform.form",
"fieldClass": "java.lang.Object",
"inputBinding": "ticket/issue",
"name": "ticket_issue",
"outputBinding": "ticket/issue",
"position": 2,
"type": "Subform"
},
```
Along with other information that does not describe the contents of the sub-form fields.

# Executing the example
  In order to use this demo, we assume that you have:
  * Maven
  * Kie Server
  
I am using ~/gits, change this reference to your environment preferences.
  
  1. Using a terminal window, build the ticket-model components:
    1. Change working directory to `~/gits/subform-ticket/ticket-support`
    2. execute `mvn clean install` command
  2. Create a user with `support` and `development` roles access, the user that I have with those characteristics is the user `jboss`: change this value in the CURL commands ahead as preferred.
  3. Ensure your kie server instance is up and running.
  4. Deploy the kjar component to your kie server using the REST API:

    ```Shell
    curl -X PUT -H "Accept:application/json" -H "Content-Type:application/json" --user kieserver:kieserver1! \
    -d '{"release-id":{"group-id":"org.acme","artifact-id":"ticket-support","version":"1.0"}}' \
    "http://localhost:8080/kie-server/services/rest/server/containers/support"
    ```
**Note** I am using kieserver user, which has `kie-server` and `rest-all` roles and `kieserver1!` password. Also note that `support` is the container name.
  5. You should receive a reponse like the following:

    ```JSON
    {
      "type" : "SUCCESS",
      "msg" : "Container support successfully deployed with module org.acme:ticket-support:1.0.",
      "result" : {
        "kie-container" : {
          "status" : "STARTED",
          "scanner" : {
            "status" : "DISPOSED",
            "poll-interval" : null
          },
          "messages" : [ ],
          "container-id" : "support",
          "release-id" : {
            "version" : "1.0",
            "group-id" : "org.acme",
            "artifact-id" : "ticket-support"
          },
          "resolved-release-id" : {
            "version" : "1.0",
            "group-id" : "org.acme",
            "artifact-id" : "ticket-support"
          },
          "config-items" : [ ]
        }
      }
    }
    ```
  -
 
1. Change working directory to `~/gits/subform-ticket/extras`
2. Launch the business process execution by running:
   
  ```Shell
  curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" --user jboss:bpms -d @ticket.json \
  http://localhost:8080/kie-server/services/rest/server/containers/support/processes/ticket-support.support/instances
  ```
    
3. Check the EAP log to show lines like the following:

  ```
  20:42:03,769 INFO  [stdout] (default task-32) INBOUND TICKET:
  20:42:03,769 INFO  [stdout] (default task-32) {'title': 'Problem while loading sub-form', 'description': 'When loading a sub-form I only get partial data, How to get all the data that I need?', 'issue': 'null'}

  ```
  
4. Get the process instance form details:
    
  ```Shell
  curl -H "Accept: application/json" -H "Content-Type: application/json" --user jboss:bpms \
  http://localhost:8080/kie-server/services/rest/server/containers/support/forms/processes/ticket-support.support
  ```    
    
5. Get the task form details:
    
  ```Shell
  curl -H "Accept: application/json" -H "Content-Type: application/json" --user jboss:bpms \
  http://localhost:8080/kie-server/services/rest/server/containers/support/forms/tasks/3
  ```
# Resolving to get the Sub-form details
  The kie-server endpoints for retrieving FORMS: `server/containers/{id}/forms/processes/{pId} - GET`and `server/containers/{id}/forms/tasks/{tInstanceId} - GET`, they have a **`filter`** parameter.
  The filter parameter for the forms endpoint: summarizes the JSON information about the fields, and includes detail about the sub-form fields.
  Thus, in our example, the result for the process form by executing:
  ```Shell
  curl -H "Accept: application/json" -H "Content-Type: application/json" --user jboss:bpms \
  http://localhost:8080/e-server/services/rest/server/containers/support/forms/processes/ticket-support.support?filter=true
  ```
  results in:
  ```JSON
  {"form": {
      "dataHolder": {
          "id": "ticket",
          "inputId": "",
          "name": "#E9E371",
          "outId": "ticket",
          "type": "dataModelerEntry",
          "value": "org.acme.ticket_support.Ticket"
      },
      "displayMode": "default",
      "field": [
          {
              "errorMessage": "",
              "fieldClass": "java.lang.String",
              "fieldRequired": false,
              "hideContent": false,
              "id": 1910476460,
            "isHTML": false,
            "label": "description",
            "name": "ticket_description",
            "outputBinding": "ticket/description",
            "position": 1,
            "readonly": false,
            "title": "",
            "type": "InputTextArea"
        },
        {
            "errorMessage": "",
            "fieldClass": "java.lang.String",
            "fieldRequired": true,
            "groupWithPrevious": false,
            "hideContent": false,
            "id": 1257741753,
            "isHTML": false,
            "label": "title",
            "name": "ticket_title",
            "outputBinding": "ticket/title",
            "position": 0,
            "readonly": false,
            "title": "",
            "type": "InputText"
        }
    ],
    "id": 1417469681,
    "name": "ticket-support.support-taskform.form",
    "status": 0
  }}  
  ```
  and the form for the task that has a sub-form is retrieved by:
  ```Shell
  curl -H "Accept: application/json" -H "Content-Type: application/json" --user jboss:bpms \
  http://localhost:8080/e-server/services/rest/server/containers/support/forms/tasks/1?filter=true
  ```
  producing the following response:
  ```JSON
  {"form": {
      "dataHolder": {
          "id": "ticket",
          "inputId": "ticket",
          "name": "#E9E371",
          "outId": "ticket",
          "type": "dataModelerEntry",
          "value": "org.acme.ticket_support.Ticket"
      },
      "displayMode": "default",
      "field": [
          {
              "errorMessage": "",
              "fieldClass": "java.lang.String",
              "fieldRequired": false,
              "hideContent": false,
              "id": 87528946,
              "inputBinding": "When loading a sub-form I only get partial data, How to get all the data that I need?",
              "isHTML": false,
              "label": "Ticket description",
              "name": "ticket_description",
              "outputBinding": "ticket/description",
              "position": 1,
              "readonly": true,
              "title": "",
              "type": "InputTextArea"
          },
          {
              "errorMessage": "",
              "fieldClass": "java.lang.String",
              "fieldRequired": false,
              "groupWithPrevious": false,
              "hideContent": false,
              "id": 1146164454,
              "inputBinding": "Problem while loading sub-form",
              "isHTML": false,
              "label": "Ticket title",
              "name": "ticket_title",
            "outputBinding": "ticket/title",
            "position": 0,
            "readonly": true,
            "title": "",
            "type": "InputText"
        }
    ],
    "form": {
        "dataHolder": {
            "id": "issue",
            "inputId": "issue",
            "name": "#9BCAFA",
            "outId": "issue",
            "type": "dataModelerEntry",
            "value": "org.acme.ticket_support.Issue"
        },
        "field": [
            {
                "id": 442250124,
                "name": "issue_title",
                "position": 0,
                "property": [
                    {
                        "name": "fieldRequired",
                        "value": true
                    },
                    {
                        "name": "groupWithPrevious",
                        "value": false
                    },
                    {
                        "name": "label",
                        "value": "title"
                    },
                    {
                        "name": "errorMessage",
                        "value": ""
                    },
                    {
                        "name": "title",
                        "value": ""
                    },
                    {
                        "name": "readonly",
                        "value": false
                    },
                    {
                        "name": "isHTML",
                        "value": false
                    },
                    {
                        "name": "hideContent",
                        "value": false
                    },
                    {
                        "name": "inputBinding",
                        "value": "issue/title"
                    },
                    {
                        "name": "outputBinding",
                        "value": "issue/title"
                    },
                    {
                        "name": "fieldClass",
                        "value": "java.lang.String"
                    }
                ],
                "type": "InputText"
            },
            {
                "id": 330417435,
                "name": "issue_applicationName",
                "position": 2,
                "property": [
                    {
                        "name": "fieldRequired",
                        "value": true
                    },
                    {
                        "name": "label",
                        "value": "application"
                    },
                    {
                        "name": "errorMessage",
                        "value": ""
                    },
                    {
                        "name": "title",
                        "value": ""
                    },
                    {
                        "name": "readonly",
                        "value": false
                    },
                    {
                        "name": "isHTML",
                        "value": false
                    },
                    {
                        "name": "hideContent",
                        "value": false
                    },
                    {
                        "name": "inputBinding",
                        "value": "issue/applicationName"
                    },
                    {
                        "name": "outputBinding",
                        "value": "issue/applicationName"
                    },
                    {
                        "name": "fieldClass",
                        "value": "java.lang.String"
                    }
                ],
                "type": "InputText"
            },
            {
                "id": 1647907110,
                "name": "issue_applicationVersion",
                "position": 3,
                "property": [
                    {
                        "name": "fieldRequired",
                        "value": false
                    },
                    {
                        "name": "groupWithPrevious",
                        "value": true
                    },
                    {
                        "name": "label",
                        "value": "version"
                    },
                    {
                        "name": "errorMessage",
                        "value": ""
                    },
                    {
                        "name": "title",
                        "value": ""
                    },
                    {
                        "name": "readonly",
                        "value": false
                    },
                    {
                        "name": "maxlength",
                        "value": 5
                    },
                    {
                        "name": "isHTML",
                        "value": false
                    },
                    {
                        "name": "hideContent",
                        "value": false
                    },
                    {
                        "name": "inputBinding",
                        "value": "issue/applicationVersion"
                    },
                    {
                        "name": "outputBinding",
                        "value": "issue/applicationVersion"
                    },
                    {
                        "name": "fieldClass",
                        "value": "java.lang.String"
                    }
                ],
                "type": "InputText"
            },
            {
                "id": 315967384,
                "name": "issue_severity",
                "position": 1,
                "property": [
                    {
                        "name": "fieldRequired",
                        "value": false
                    },
                    {
                        "name": "groupWithPrevious",
                        "value": false
                    },
                    {
                        "name": "label",
                        "value": "severity"
                    },
                    {
                        "name": "errorMessage",
                        "value": ""
                    },
                    {
                        "name": "title",
                        "value": ""
                    },
                    {
                        "name": "readonly",
                        "value": false
                    },
                    {
                        "name": "isHTML",
                        "value": false
                    },
                    {
                        "name": "hideContent",
                        "value": false
                    },
                    {
                        "name": "inputBinding",
                        "value": "issue/severity"
                    },
                    {
                        "name": "outputBinding",
                        "value": "issue/severity"
                    },
                    {
                        "name": "fieldClass",
                        "value": "java.lang.String"
                    }
                ],
                "type": "InputText"
            },
            {
                "id": 11671165,
                "name": "issue_description",
                "position": 4,
                "property": [
                    {
                        "name": "fieldRequired",
                        "value": false
                    },
                    {
                        "name": "label",
                        "value": "description"
                    },
                    {
                        "name": "errorMessage",
                        "value": ""
                    },
                    {
                        "name": "title",
                        "value": ""
                    },
                    {
                        "name": "readonly",
                        "value": false
                    },
                    {
                        "name": "isHTML",
                        "value": false
                    },
                    {
                        "name": "hideContent",
                        "value": false
                    },
                    {
                        "name": "inputBinding",
                        "value": "issue/description"
                    },
                    {
                        "name": "outputBinding",
                        "value": "issue/description"
                    },
                    {
                        "name": "fieldClass",
                        "value": "java.lang.String"
                    }
                ],
                "type": "InputTextArea"
            }
        ],
        "id": 97254019,
        "property": [
            {
                "name": "name",
                "value": "issue-subform.form"
            },
            {
                "name": "displayMode",
                "value": "default"
            },
            {
                "name": "status",
                "value": 0
            }
        ]
    },
    "id": 1805793668,
    "name": "issue_description-taskform.form",
    "status": 0
  }}
  ```
  Note the **dataModelerEntry** type for the sub-form field in contrast with the **InputText** for the other fields.
