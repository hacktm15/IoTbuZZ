# IoTbuZZ
The project offers a **Smart Home** solution based on multiple technologies
providing a higher transparency for the user. Also, this is a very modular
solution.

## Applicability
The solution can be applied over multiple environments, not only for an automated house, but on a business office set-up as well.

## Components and set-up
The components are:
- *Raspberry Pi* used as a server point;
- *iBeacons* used to identify the user and activate a context action;
- *Gemalto Concept Board* which is the control unit that executes tasks based on the server's instructions.

The set-up workflow is as follows:
- The user is detected using an iBeacon-user's mobile dialog; the user's mobile phone sends an HTTP notification to the server's API;
- The server, based on iBeacon ID sent by the user's mobile phone will set a decision list prepared for controller.
  The decision list is sent to the controller unit;
- The controller unit receives the decision list and starts to run the tasks (e.g. open a door, power up a light, reports the status)

