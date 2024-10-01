# CS 460 Project 1: Coffee Maker

## Group Members
- Jyrus Cadman
- David Dominguez
- Alex Hartel
- Matthew Lloyd Macias
- William Lopez

## Project Description
This project simulates a coffee maker system using Java. It includes various components such as sensors, buttons, and a heating system to replicate the functionality of a real coffee maker.

## Key Components

### Main Control System
The `Main` class serves as the central control system for the coffee maker. It manages the overall operation, including:
- Initializing all components
- Handling user input
- Coordinating between different parts of the system

### Sensors
- **TemperatureSensor**: Monitors and controls the water temperature
- **VoltageSensor**: Checks if the voltage is within the correct range
- **ReservoirSensor**: Detects if there's water in the reservoir
- **LidSensor**: Checks if the lid is open or closed
- **CarafeSensor**: Detects if the carafe is in place

### Buttons
- **BrewButton**: Initiates the brewing process
- **HeatingButton**: Controls the heating element
- **PowerButton**: Turns the coffee maker on and off

### Other Components
- **Heater**: Manages the heating element of the coffee maker
- **LEDBank**: Controls the various indicator lights
- **Timer**: Handles timing operations for brewing and heating

## How to Run
1. Compile all Java files in the `src` directory.
2. Run the `Main` class to start the coffee maker simulation.

## Future Improvements
- Implement socket communication between the coffee maker and a terminal interface
- Enhance error handling and edge case scenarios
- Improve user interface and feedback mechanisms

## Notes for Developers
- The project uses a modular approach with separate classes for each component
- The `Main` class coordinates all operations and should be the focus for system-wide changes
- Sensor and button classes are designed to be easily extendable for future enhancements
