package com.aer.rest;

import com.aer.model.User;
import com.aer.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by fan.jin on 2016-10-15.
 */

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Role Based Access Control API", description = "RBAC API")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(
            tags = "User",
            value = "Get a User resource by its id",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/user/{id}")
    @PreAuthorize("hasAuthority('user_view')")
    public User loadById(@RequestHeader(value = "Api-Token") String apiToken,  @PathVariable("id") Long id) {
        return this.userService.findById(id);
    }

    @ApiOperation(
            tags = "User",
            value = "Get all User resource",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/user/all")
    @PreAuthorize("hasAuthority('user_view')")
    public List<User> loadAll(@RequestHeader(value = "Api-Token", required = false) String apiToken) {
        return this.userService.findAll();
    }

    @ApiOperation(
            tags = "User",
            value = "Get a User resource by its name",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value="/whoami")
    @PreAuthorize("hasAuthority('user_view')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }


    @ApiOperation(
            tags = "User",
            value = "Create a new user resource",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/user")
    @PreAuthorize("hasAuthority('user_edit')")
    public ResponseEntity<User> create(@RequestHeader(value = "Api-Token") String apiToken, @RequestBody User user) {
        User userCreated = userService.save(user);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }
}
