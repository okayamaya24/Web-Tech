# Postman Testing Documentation

This document provides instructions on how to test the implemented features using Postman.

## Use Case 7: Crew Member Submits Availability

### 1. Get Upcoming Games for Crew Member

**Request:**
- Method: GET
- URL: `http://localhost:8080/api/availability/games/upcoming`
- Query Params: `crewMemberEmail=john.doe@example.com`

This endpoint retrieves a list of upcoming games for a crew member to submit availability for. It includes information about whether availability has already been submitted.

**Example Response:**
```json
[
    {
        "gameId": 1,
        "date": "2023-10-15",
        "time": "19:00",
        "venue": "Main Stadium",
        "opponent": "Rivals",
        "sport": "Football",
        "hasSubmittedAvailability": true,
        "isAvailable": true,
        "comment": "I can work this game"
    },
    {
        "gameId": 2,
        "date": "2023-10-22",
        "time": "14:00",
        "venue": "Away Field",
        "opponent": "Tigers",
        "sport": "Football",
        "hasSubmittedAvailability": false
    }
]
```

### 2. Submit Availability for a Game

**Request:**
- Method: POST
- URL: `http://localhost:8080/api/availability/{gameId}`
- Query Params: `crewMemberEmail=john.doe@example.com`
- Body (raw JSON):

```json
{
    "available": true,
    "comment": "I am available for this game"
}
```

**Example:**
```
POST http://localhost:8080/api/availability/2?crewMemberEmail=john.doe@example.com
```

**Expected Response:**
```json
{
    "success": true,
    "gameId": 2,
    "crewMemberId": 3,
    "available": true,
    "comment": "I am available for this game"
}
```

### 3. Check Specific Availability

**Request:**
- Method: GET
- URL: `http://localhost:8080/api/availability/game/{gameId}/crew-member`
- Query Params: `crewMemberEmail=john.doe@example.com`

**Example:**
```
GET http://localhost:8080/api/availability/game/2/crew-member?crewMemberEmail=john.doe@example.com
```

**Expected Response:**
```json
{
    "id": 5,
    "crewMemberId": 3,
    "gameId": 2,
    "available": true,
    "comment": "I am available for this game"
}
```

### 4. View All Availabilities for a Crew Member

**Request:**
- Method: GET
- URL: `http://localhost:8080/api/availability/crew-member`
- Query Params: `crewMemberEmail=john.doe@example.com`

**Example:**
```
GET http://localhost:8080/api/availability/crew-member?crewMemberEmail=john.doe@example.com
```

**Expected Response:**
```json
[
    {
        "id": 4,
        "crewMemberId": 3,
        "gameId": 1,
        "available": true,
        "comment": "I can work this game"
    },
    {
        "id": 5,
        "crewMemberId": 3,
        "gameId": 2,
        "available": true,
        "comment": "I am available for this game"
    }
]
```

## Use Case 23: Admin Schedules Crew

### 1. Get Upcoming Games with Assignment Status (Admin)

**Request:**
- Method: GET
- URL: `http://localhost:8080/api/crew-assignments/upcoming`
- Query Params: `adminEmail=admin@example.com`

This endpoint provides an overview of upcoming games and their assignment status.

**Expected Response:**
```json
[
    {
        "gameId": 1,
        "opponent": "Rivals",
        "date": "2023-10-15",
        "time": "19:00",
        "location": "Main Stadium",
        "totalPositionsRequired": 3,
        "positionsFilled": 2
    },
    {
        "gameId": 2,
        "opponent": "Tigers",
        "date": "2023-10-22",
        "time": "14:00",
        "location": "Away Field",
        "totalPositionsRequired": 3,
        "positionsFilled": 0
    }
]
```

### 2. Get Available Positions for a Game

**Request:**
- Method: GET
- URL: `http://localhost:8080/api/crew-assignments/game/{gameId}/positions`

**Example:**
```
GET http://localhost:8080/api/crew-assignments/game/1/positions
```

**Expected Response:**
```json
[
    {
        "position": "camera",
        "assigned": false,
        "crewMemberName": null,
        "assignmentId": null
    },
    {
        "position": "director",
        "assigned": true,
        "crewMemberName": "jane.smith@example.com",
        "assignmentId": 5
    },
    {
        "position": "commentator",
        "assigned": true,
        "crewMemberName": "mike.jones@example.com",
        "assignmentId": 6
    }
]
```

### 3. Get Crew Recommendations for a Game

**Request:**
- Method: GET
- URL: `http://localhost:8080/api/crew-assignments/recommendations/{gameId}`
- Query Params: `adminEmail=admin@example.com`

This endpoint provides recommendations for each position, showing who is qualified and available.

**Example:**
```
GET http://localhost:8080/api/crew-assignments/recommendations/1?adminEmail=admin@example.com
```

**Expected Response:**
```json
[
    {
        "position": "camera",
        "assigned": false,
        "assignedCrew": null,
        "recommendedCrew": [
            {
                "crewMemberId": 3,
                "name": "John Doe",
                "email": "john.doe@example.com",
                "phoneNumber": "555-123-4567",
                "currentlyAssigned": false
            },
            {
                "crewMemberId": 4,
                "name": "Alice Johnson",
                "email": "alice.j@example.com",
                "phoneNumber": "555-987-6543",
                "currentlyAssigned": false
            }
        ]
    },
    {
        "position": "director",
        "assigned": true,
        "assignedCrew": {
            "crewMemberId": 7,
            "name": "Jane Smith",
            "email": "jane.smith@example.com",
            "phoneNumber": "555-111-2222",
            "currentlyAssigned": true
        },
        "recommendedCrew": [
            {
                "crewMemberId": 8,
                "name": "Bob Williams",
                "email": "bob.w@example.com",
                "phoneNumber": "555-333-4444",
                "currentlyAssigned": false
            }
        ]
    }
]
```

### 4. Assign a Single Crew Member to a Position

**Request:**
- Method: POST
- URL: `http://localhost:8080/api/crew-assignments`
- Query Params: `adminEmail=admin@example.com`
- Body (raw JSON):

```json
{
    "gameId": 1,
    "position": "camera",
    "name": "john.doe@example.com"
}
```

**Expected Response:**
```json
{
    "id": 7,
    "gameId": 1,
    "position": "camera",
    "name": "john.doe@example.com"
}
```

### 5. Assign Multiple Crew Members at Once (Recommended)

**Request:**
- Method: POST
- URL: `http://localhost:8080/api/crew-assignments/bulk`
- Query Params: `adminEmail=admin@example.com`
- Body (raw JSON):

```json
{
    "gameId": 2,
    "assignments": [
        {
            "position": "camera",
            "name": "john.doe@example.com"
        },
        {
            "position": "director",
            "name": "jane.smith@example.com"
        },
        {
            "position": "commentator",
            "name": "mike.jones@example.com"
        }
    ]
}
```

**Expected Response:**
```json
{
    "success": true,
    "message": "Created 3 crew assignments for game 2",
    "assignments": [
        {
            "id": 8,
            "gameId": 2,
            "position": "camera",
            "name": "john.doe@example.com"
        },
        {
            "id": 9,
            "gameId": 2,
            "position": "director",
            "name": "jane.smith@example.com"
        },
        {
            "id": 10,
            "gameId": 2,
            "position": "commentator",
            "name": "mike.jones@example.com"
        }
    ]
}
```

### 6. Remove a Crew Assignment

**Request:**
- Method: DELETE
- URL: `http://localhost:8080/api/crew-assignments/{id}`
- Query Params: `adminEmail=admin@example.com`

**Example:**
```
DELETE http://localhost:8080/api/crew-assignments/7?adminEmail=admin@example.com
```

**Expected Response:**
```json
"Crew assignment deleted successfully"
```

### 7. Remove All Assignments for a Game

**Request:**
- Method: DELETE
- URL: `http://localhost:8080/api/crew-assignments/game/{gameId}`
- Query Params: `adminEmail=admin@example.com`

**Example:**
```
DELETE http://localhost:8080/api/crew-assignments/game/2?adminEmail=admin@example.com
```

**Expected Response:**
```json
"All crew assignments for game deleted successfully"
```

## Important Notes

1. For all admin operations, make sure to include the `adminEmail=admin@example.com` query parameter to authenticate as an admin. If this parameter is missing or has a different value, you'll receive a 403 Forbidden response.

2. When assigning a crew member to a position, ensure:
   - The crew member has the required qualification for the position
   - The crew member has marked themselves as available for the game
   - The position is valid for the game
   - The position is not already assigned to another crew member

3. You may need to create test data first:
   - Create games with required positions
   - Create crew members with qualifications
   - Submit availability for crew members 