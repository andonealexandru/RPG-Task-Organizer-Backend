package com.backend.rpgtask.ui.controller;

import com.backend.rpgtask.service.EmailSenderService;
import com.backend.rpgtask.service.UserService;
import com.backend.rpgtask.shared.dto.UserDto;
import com.backend.rpgtask.ui.model.request.UserDetailsRequestModel;
import com.backend.rpgtask.ui.model.response.OperationStatusModel;
import com.backend.rpgtask.ui.model.response.RequestOperationName;
import com.backend.rpgtask.ui.model.response.RequestOperationStatus;
import com.backend.rpgtask.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") // localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @PreAuthorize("hasRole('USER')")
    public UserRest getUser(@PathVariable String id) {
        UserRest userRest = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, userRest);

        return userRest;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) throws Exception {

        if (userDetailsRequestModel.getFirstName().isEmpty()) throw new RuntimeException("Missing required field");
        else {
            //UserDto userDto = new UserDto();
            //BeanUtils.copyProperties(userDetailsRequestModel, userDto);
            ModelMapper modelMapper = new ModelMapper();
            UserDto userDto = modelMapper.map(userDetailsRequestModel, UserDto.class);

            UserDto createdUser = userService.createUser(userDto);
            UserRest userRest = modelMapper.map(createdUser, UserRest.class);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userDto.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("andonealexandru19@gmail.com");
            mailMessage.setText("To confirm your account please clik here: " +
                    "http://localhost:3000/users/confirm-account?token=" + createdUser.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            return userRest;
        }
    }

    @GetMapping(path = "/confirm-account", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public String confirmUserAccount(@RequestParam("token") String confirmationToken) {
        UserDto user = userService.getUserByConfirmationToken(confirmationToken);

        if (user != null) {
            UserDto userDto = userService.getUser(user.getEmail());
            userDto.setEnabled(true);
            userService.updateUser(userDto.getUserId(), userDto);
            return "Account verified";
        }
        else {
            return "Error";
        }
    }

    @PutMapping(path = "/{id}",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel, @PathVariable String id) {
        UserRest userRest = new UserRest();

        if (userDetailsRequestModel.getFirstName().isEmpty()) throw new RuntimeException("User already exists");

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);

        UserDto updatedUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updatedUser, userRest);

        return userRest;
    }

    @DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();

        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }
}
