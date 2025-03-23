# Uber Clone

## Overview
Uber-Clone is a native Android application developed in Java that replicates core functionality of the popular ride-sharing service Uber. This project serves as a demonstration of Android development skills and understanding of ride-sharing application architecture.

## Features
- User registration and authentication
- Driver and rider modes
- Real-time location tracking
- Ride requests and acceptance
- Route visualization on maps
- Fare calculation
- Ride history
- User ratings and reviews
- Payment simulation

## Technical Implementation
- **Platform**: Native Android
- **Language**: Java (100%)
- **Maps Integration**: Google Maps API for route visualization and location services
- **Real-time Updates**: Firebase Realtime Database (optional)
- **UI Design**: Material Design principles

## Screenshots
(Screenshots would be placed here)

## Getting Started

### Prerequisites
- Android Studio
- Google Maps API key
- Android device or emulator running Android 5.0 (Lollipop) or higher

### Installation
1. Clone this repository
2. Open the project in Android Studio
3. Add your Google Maps API key in the appropriate configuration file
4. Build and run the application

## Configuration
The application requires a valid Google Maps API key to function properly. You will need to:
1. Obtain an API key from the Google Cloud Console
2. Enable the Maps SDK for Android
3. Add the key to your `local.properties` file (do not commit this file to version control)

## Project Structure
- `UberPrototype/`: Main application module
  - `activities/`: Activity classes for different screens
  - `adapters/`: RecyclerView and other adapters
  - `models/`: Data models
  - `services/`: Background services
  - `utils/`: Helper classes and utilities

## Known Issues
- This is a prototype and not intended for production use
- Payment processing is simulated only
- Limited error handling in some edge cases

## Future Enhancements
- Add support for different vehicle types
- Implement promo codes functionality
- Enhance ride sharing capabilities
- Add multiple payment methods
- Improve driver-rider matching algorithm

## Contributors
- José Carlos Peixoto Leão (kzeca)
