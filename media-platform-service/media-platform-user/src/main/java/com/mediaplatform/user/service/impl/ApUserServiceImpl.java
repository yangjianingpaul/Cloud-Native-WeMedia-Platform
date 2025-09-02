package com.mediaplatform.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.common.enums.AppHttpCodeEnum;
import com.mediaplatform.model.user.dtos.LoginDto;
import com.mediaplatform.model.user.pojos.ApUser;
import com.mediaplatform.user.mapper.ApUserMapper;
import com.mediaplatform.user.service.ApUserService;
import com.mediaplatform.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * App User Service Implementation - Core service for user authentication and management
 * 
 * This service handles fundamental user operations for the media platform application,
 * providing secure authentication mechanisms and user data management capabilities.
 * 
 * Key responsibilities:
 * - User authentication with phone number and password
 * - Guest user access management (anonymous users)
 * - JWT token generation and management
 * - Password security using salted MD5 hashing
 * - User profile data retrieval and management
 * - Integration with MyBatis Plus for database operations
 * 
 * Security features:
 * - Password encryption using MD5 with salt
 * - JWT token-based authentication
 * - Support for both registered and guest users
 * - Secure credential validation and error handling
 * 
 * The service supports two authentication modes:
 * 1. Registered user login: Phone number + password authentication
 * 2. Guest user access: Anonymous access with limited privileges
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Transactional  // Enable transaction management for data consistency
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {
    /**
     * User login authentication service
     * 
     * This method provides dual authentication modes for the media platform:
     * 1. Registered user login with phone number and password validation
     * 2. Guest user access for anonymous browsing with limited functionality
     * 
     * Authentication process:
     * - Validates user credentials against encrypted password with salt
     * - Generates JWT tokens for session management
     * - Returns user profile information for client applications
     * - Handles both successful authentication and error scenarios
     * 
     * @param dto LoginDto containing user credentials (phone and password) or empty for guest access
     * @return ResponseResult containing JWT token and user information on success, error details on failure
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        // Handle registered user authentication with phone and password
        if (StringUtils.isNotBlank(dto.getPhone()) && StringUtils.isNotBlank(dto.getPassword())) {
            log.info("Processing registered user login for phone: {}", dto.getPhone());
            
            // Query user by phone number from database
            ApUser dbUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (dbUser == null) {
                log.warn("Login failed: User not found for phone: {}", dto.getPhone());
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "The user information does not exist!");
            }

            // Validate password using salted MD5 hash comparison
            String salt = dbUser.getSalt();
            String password = dto.getPassword();
            String hashedPassword = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            
            if (!hashedPassword.equals(dbUser.getPassword())) {
                log.warn("Login failed: Invalid password for phone: {}", dto.getPhone());
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            // Generate JWT token and prepare response data
            String token = AppJwtUtil.getToken(dbUser.getId().longValue());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            
            // Remove sensitive data before returning user information
            dbUser.setSalt("");
            dbUser.setPassword("");
            response.put("user", dbUser);
            
            log.info("Login successful for user ID: {}", dbUser.getId());
            return ResponseResult.okResult(response);
            
        } else {
            // Handle guest user access (anonymous browsing)
            log.info("Processing guest user login");
            Map<String, Object> response = new HashMap<>();
            response.put("token", AppJwtUtil.getToken(0L)); // Guest user ID = 0
            return ResponseResult.okResult(response);
        }
    }

    /**
     * Retrieve user display name by user ID
     * 
     * This method provides user name lookup functionality for the platform,
     * commonly used for displaying author names in articles, comments, and other
     * user-generated content throughout the application.
     * 
     * @param userId the unique identifier of the user whose name is requested
     * @return ResponseResult containing the user's display name on success, error details on failure
     * @throws IllegalArgumentException if userId is null
     */
    @Override
    public ResponseResult getUserName(Integer userId) {
        // Validate input parameter
        if (userId == null) {
            log.warn("getUserName failed: User ID parameter is null");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // Retrieve user name from database
        ApUser user = getById(userId);
        if (user == null || user.getName() == null) {
            log.warn("getUserName failed: No user found or name is null for user ID: {}", userId);
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        log.debug("Successfully retrieved name for user ID: {}", userId);
        return ResponseResult.okResult(user.getName());
    }
}
