# IoTbuZZ
The project offers a **Smart Home** solution based on multiple technologies
offering a higher transparency for the user. Also, this is a very modular
solution.

## Applicability
The solution can be applied over multiple environments, not only for an automated house, like on a business office set-up.

## Components and set-up
The components are:
- *Raspberry Pi* used as a server point;
- *iBeacon* units used to indicate to identify the user and activate a context action;
- *Gemalto Concept Board* which is the control unit and execute tasks based on the server's instructions.

The set-up workflow is as follows:
- The user is detected using a iBeacon-user's mobile dialog; the user's mobile phone sends an API notification to the server;
- The server, based on iBeacon ID sent by the user's mobile phone will set a decision list prepared for controller.
  The decision list is sent to the controller unit;
- The controller unit receive the decision list and stars to run the tasks (open a door, power up a light, reports the status)

## Conclusion
Using this set-up will have a higher modularity level.
