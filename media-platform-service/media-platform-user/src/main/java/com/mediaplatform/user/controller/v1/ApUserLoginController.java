package com.mediaplatform.user.controller.v1;

import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.user.dtos.LoginDto;
import com.mediaplatform.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * App User Login Controller - REST API endpoints for user authentication
 * 
 * This controller provides authentication services for the mobile and web applications,
 * handling user login requests for both registered users and guest access scenarios.
 * 
 * Supported authentication flows:
 * - Registered user login: Phone number and password authentication
 * - Guest user access: Anonymous browsing with limited functionality
 * - JWT token generation for session management
 * - Integration with gateway authorization filters
 * 
 * Security considerations:
 * - All login requests go through the authentication service layer
 * - Passwords are validated using salted hash comparison
 * - JWT tokens are generated for successful authentications
 * - Guest users receive limited-privilege tokens
 * 
 * API Endpoints:
 * - POST /api/v1/login/login_auth: Main authentication endpoint
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/v1/login")
public class ApUserLoginController {

    @Autowired
    private ApUserService apUserService;

    /**
     * User authentication endpoint
     * 
     * Handles login requests for both registered users and guest access.
     * Supports two authentication modes:
     * 1. Registered user: Requires phone number and password
     * 2. Guest user: Empty or null credentials for anonymous access
     * 
     * Response includes:
     * - JWT authentication token for session management
     * - User profile information (for registered users)
     * - Authentication status and error details
     * 
     * @param dto LoginDto containing user credentials (phone/password) or empty for guest access
     * @return ResponseResult containing JWT token and user data on success, error details on failure
     */
    @PostMapping("/login_auth")
    public ResponseResult login(@RequestBody LoginDto dto) {
        return apUserService.login(dto);
    }
}
