System Architecture Diagram
![image](https://github.com/user-attachments/assets/b69f6a4b-2515-4f25-9c78-a20a5245bb5a)


Trip Management System
Overview

The Trip Management System is a microservices-based application designed to manage trips efficiently. It allows users to create trips, assign transporters, track their real-time locations, and transition through various trip states. The system leverages modern architectural patterns such as API Gateway, Eureka service discovery, and event-driven architecture to ensure scalability, maintainability, and real-time updates.
Architecture
Microservices Architecture

The application is built on a microservices architecture, with each service having a distinct responsibility:

    Trip Service: Manages trip lifecycle, including state transitions (Created → Booked → Running → Completed).
    Transporter Service: Handles transporter details and their assignment to trips.
    Identity Service: Manages user authentication, authorization, and registration.
    Location Service: Tracks and updates real-time transporter locations.
    Notification Service: Sends notifications, such as user registration acknowledgments and trip updates.

API Gateway

The API Gateway acts as the single entry point to the system and handles:

    Routing requests to appropriate microservices.
    Authentication and authorization using OAuth2/JWT.
    Rate limiting to control the number of requests per client.
    Centralized logging and monitoring.

Event-Driven Architecture

The system uses an event-driven architecture for asynchronous communication:

    Kafka is used for message brokering.
        Real-Time Location Updates: Location Service consumes Kafka events to update transporter locations.
        User Registration Notifications: Notification Service listens to registration events to send welcome messages.

Trip States and Workflow
State Transition:

    Created: Every trip starts in an unassigned state.
    Booked: A transporter is assigned to the trip.
    Running: The trip starts when initiated by the user or transporter.
    Completed: The trip ends when the journey is marked as complete.
