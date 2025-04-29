package edu.tcu.cs.backend.crewmember;

import edu.tcu.cs.backend.crewmember.dto.CrewMemberRequestDTO;
import edu.tcu.cs.backend.crewmember.dto.InvitationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
@RequestMapping("/api/crew-members")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Crew Members", description = "API endpoints for managing crew members")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;
    private final CrewMemberRepository crewMemberRepository;
    private final InvitationService invitationService;

    @Autowired
    public CrewMemberController(CrewMemberService crewMemberService, 
                               CrewMemberRepository crewMemberRepository,
                               InvitationService invitationService) {
        this.crewMemberService = crewMemberService;
        this.crewMemberRepository = crewMemberRepository;
        this.invitationService = invitationService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new crew member", 
               description = "Creates a new crew member account - publicly accessible without authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Crew member registered successfully",
                     content = @Content(schema = @Schema(implementation = CrewMemberDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or email already in use")
    })
    public ResponseEntity<?> registerCrewMember(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Crew member registration details", 
                required = true, content = @Content(schema = @Schema(implementation = CrewMemberDto.class)))
            @RequestBody CrewMemberDto crewMemberDto) {
        if ("admin@example.com".equalsIgnoreCase(crewMemberDto.getEmail())) {
            return ResponseEntity.badRequest().body("Cannot register admin through registration page.");
        }
        CrewMemberDto savedCrewMember = crewMemberService.registerCrewMember(crewMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCrewMember);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get crew member by email", description = "Retrieves a specific crew member by their email")
    @ApiResponse(responseCode = "200", description = "Crew member found")
    @ApiResponse(responseCode = "404", description = "Crew member not found")
    public ResponseEntity<CrewMemberDto> getCrewMemberByEmail(
            @Parameter(description = "Email of the crew member", required = true) @PathVariable String email) {
        CrewMemberDto crewMemberDto = crewMemberService.findByEmail(email);
        return ResponseEntity.ok(crewMemberDto);
    }

    @GetMapping
    @Operation(summary = "Get all crew members", 
               description = "Retrieves a list of all crew members. When accessed by admin, provides full details of crew members. Admin can filter by position or search by name.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of crew members retrieved successfully")
    })
    public ResponseEntity<?> getAllCrewMembers(
            @Parameter(description = "Admin email for authorization (optional)") 
            @RequestParam(required = false) String adminEmail,
            @Parameter(description = "Filter by qualified position (optional)")
            @RequestParam(required = false) String position,
            @Parameter(description = "Search by name (optional, searches in first and last name)")
            @RequestParam(required = false) String nameSearch) {
        
        // Verify admin permissions for detailed view
        boolean isAdmin = "admin@example.com".equalsIgnoreCase(adminEmail);
        
        // Get all crew members
        List<CrewMember> crewMembers = crewMemberService.getAllCrewMembers();
        
        // Apply filters if specified
        if (position != null && !position.isEmpty()) {
            crewMembers = crewMembers.stream()
                .filter(cm -> cm.getQualifiedPosition() != null && 
                        cm.getQualifiedPosition().equalsIgnoreCase(position))
                .collect(Collectors.toList());
        }
        
        if (nameSearch != null && !nameSearch.isEmpty()) {
            String searchLower = nameSearch.toLowerCase();
            crewMembers = crewMembers.stream()
                .filter(cm -> (cm.getFirstName() != null && cm.getFirstName().toLowerCase().contains(searchLower)) ||
                              (cm.getLastName() != null && cm.getLastName().toLowerCase().contains(searchLower)))
                .collect(Collectors.toList());
        }
        
        // Filter out sensitive information if not admin
        if (!isAdmin) {
            return ResponseEntity.ok(crewMembers.stream()
                .map(cm -> {
                    CrewMember filtered = new CrewMember();
                    filtered.setId(cm.getId());
                    filtered.setFirstName(cm.getFirstName());
                    filtered.setLastName(cm.getLastName());
                    filtered.setEmail(cm.getEmail());
                    filtered.setRole(cm.getRole());
                    filtered.setQualifiedPosition(cm.getQualifiedPosition());
                    // Don't include password and phone number
                    return filtered;
                })
                .collect(Collectors.toList()));
        }
        
        return ResponseEntity.ok(crewMembers);
    }
    
    @PostMapping("/invite")
    @Operation(summary = "Invite a crew member", 
               description = "Allows an admin to invite a new crew member by sending an email invitation with a registration link")
    @ApiResponse(responseCode = "201", description = "Crew member invited successfully and invitation email sent")
    @ApiResponse(responseCode = "400", description = "Invalid input or email already in use")
    @ApiResponse(responseCode = "403", description = "Forbidden - only admin can invite members")
    public ResponseEntity<?> inviteCrewMember(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Invitation details", required = true) 
            @RequestBody InvitationDto invitationDto,
            @Parameter(description = "Admin email for authorization", required = true) 
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can invite crew members");
        }
        
        try {
            if (invitationDto == null) {
                return ResponseEntity.badRequest().body("Request body cannot be null");
            }
            
            // Debug log
            System.out.println("Invitation request received:");
            System.out.println("Email: " + invitationDto.getEmail());
            System.out.println("First Name: " + invitationDto.getFirstName());
            System.out.println("Last Name: " + invitationDto.getLastName());
            System.out.println("Position: " + invitationDto.getQualifiedPosition());
            
            Invitation invitation = invitationService.createInvitation(invitationDto);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "message", "Invitation sent successfully",
                    "email", invitation.getEmail(),
                    "expiresAt", invitation.getExpiresAt()
                )
            );
        } catch (RuntimeException e) {
            System.err.println("Error inviting crew member: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Unexpected error: " + e.getMessage());
        }
    }
    
    @GetMapping("/validate-invitation")
    @Operation(summary = "Validate an invitation token", 
               description = "Validates if an invitation token is valid before registration")
    @ApiResponse(responseCode = "200", description = "Invitation token is valid")
    @ApiResponse(responseCode = "400", description = "Invalid, expired, or already used token")
    public ResponseEntity<?> validateInvitationToken(
            @Parameter(description = "Invitation token to validate", required = true)
            @RequestParam String token) {
        try {
            Invitation invitation = invitationService.validateInvitationToken(token);
            return ResponseEntity.ok(
                Map.of(
                    "valid", true,
                    "email", invitation.getEmail(),
                    "firstName", invitation.getFirstName(),
                    "lastName", invitation.getLastName(),
                    "qualifiedPosition", invitation.getQualifiedPosition()
                )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "valid", false,
                    "message", e.getMessage()
                )
            );
        }
    }
    
    @PostMapping("/register-with-invitation")
    @Operation(summary = "Register a crew member with an invitation token", 
               description = "Registers a new crew member using a valid invitation token")
    @ApiResponse(responseCode = "201", description = "Crew member registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input, token expired, or already used")
    public ResponseEntity<?> registerWithInvitation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Registration details with invitation token", required = true)
            @RequestBody Map<String, String> registrationData) {
        
        String token = registrationData.get("token");
        String password = registrationData.get("password");
        String phoneNumber = registrationData.get("phoneNumber");
        
        if (token == null || password == null) {
            return ResponseEntity.badRequest().body("Invitation token and password are required");
        }
        
        try {
            // Validate the token
            Invitation invitation = invitationService.validateInvitationToken(token);
            
            // Create crew member from invitation data
            CrewMemberDto crewMemberDto = new CrewMemberDto();
            crewMemberDto.setEmail(invitation.getEmail());
            crewMemberDto.setFirstName(invitation.getFirstName());
            crewMemberDto.setLastName(invitation.getLastName());
            crewMemberDto.setQualifiedPosition(invitation.getQualifiedPosition());
            crewMemberDto.setPassword(password);
            crewMemberDto.setRole("crew");
            crewMemberDto.setPhoneNumber(phoneNumber != null ? phoneNumber : "");
            
            // Register the crew member
            CrewMemberDto savedMember = crewMemberService.registerCrewMember(crewMemberDto);
            
            // Mark the invitation as accepted
            invitationService.markInvitationAsAccepted(token);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/pending-invitations")
    @Operation(summary = "Get all pending invitations", 
               description = "Retrieves all non-expired and non-accepted invitations")
    @ApiResponse(responseCode = "200", description = "List of pending invitations retrieved successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden - only admin can view pending invitations")
    public ResponseEntity<?> getPendingInvitations(
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can view pending invitations");
        }
        
        List<Invitation> pendingInvitations = invitationService.getPendingInvitations();
        return ResponseEntity.ok(pendingInvitations);
    }
    
    @DeleteMapping("/invitations/{id}")
    @Operation(summary = "Delete an invitation", 
               description = "Allows an admin to delete an invitation")
    @ApiResponse(responseCode = "200", description = "Invitation deleted successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden - only admin can delete invitations")
    @ApiResponse(responseCode = "404", description = "Invitation not found")
    public ResponseEntity<?> deleteInvitation(
            @Parameter(description = "ID of the invitation to delete", required = true)
            @PathVariable Long id,
            @Parameter(description = "Admin email for authorization", required = true)
            @RequestParam String adminEmail) {
        
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can delete invitations");
        }
        
        try {
            invitationService.deleteInvitation(id);
            return ResponseEntity.ok(Map.of("message", "Invitation deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("Invitation not found or could not be deleted");
        }
    }
    
    @DeleteMapping("/{email}")
    @Operation(summary = "Delete a crew member", description = "Allows an admin to delete a crew member")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Crew member deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or crew member not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden - only admin can delete members")
    })
    public ResponseEntity<?> deleteCrewMember(
            @Parameter(description = "Email of the crew member to delete", required = true) 
            @PathVariable String email,
            @Parameter(description = "Admin email for authorization", required = true) 
            @RequestParam String adminEmail) {
        // Verify that the request is coming from an admin user
        if (!"admin@example.com".equalsIgnoreCase(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can delete crew members");
        }
        
        try {
            crewMemberService.deleteCrewMember(email);
            return ResponseEntity.ok(Map.of(
                "message", "Crew member deleted successfully",
                "email", email
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
