package com.backend.rpgtask.ui.controller;

import com.backend.rpgtask.io.entity.DailyEntity;
import com.backend.rpgtask.service.*;
import com.backend.rpgtask.shared.dto.DailyDto;
import com.backend.rpgtask.shared.dto.HabitsDto;
import com.backend.rpgtask.shared.dto.ToDoDto;
import com.backend.rpgtask.shared.dto.UserDto;
import com.backend.rpgtask.ui.model.request.HabitsRequestModel;
import com.backend.rpgtask.ui.model.request.ToDoRequestModel;
import com.backend.rpgtask.ui.model.request.UserDetailsRequestModel;
import com.backend.rpgtask.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users") // localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ToDoService toDoService;

    @Autowired
    HabitsService habitsService;

    @Autowired
    DailyService dailyService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    ModelMapper modelMapper;

    /** DAILY ENDPOINTS **/

    @PostMapping(path = "/daily", produces = { MediaType.APPLICATION_JSON_VALUE })
    public DailyRest createDaily(@RequestBody HabitsRequestModel habitsRequestModel) {

        DailyDto dailyDto = modelMapper.map(habitsRequestModel, DailyDto.class);
        dailyDto.setOrderNumber(habitsRequestModel.getOrder());

        DailyDto createdDto = dailyService.createTask(dailyDto);

        DailyRest response = modelMapper.map(createdDto, DailyRest.class);
        response.setOrder(createdDto.getOrderNumber());

        return response;
    }

    @GetMapping(path = "/daily/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<DailyRest> getDaily(@PathVariable String userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        List<DailyDto> dailyDtos = dailyService.getTasks(userDto);
        List<DailyRest> response = new ArrayList<>();

        for (int i = 0; i < dailyDtos.size(); ++i) {
            DailyRest dailyRest = modelMapper.map(dailyDtos.get(i), DailyRest.class);
            dailyRest.setOrder(dailyDtos.get(i).getOrderNumber());
            response.add(dailyRest);
        }

        return response;
    }

    @PutMapping(path = "/daily/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public OperationStatusModel updateDaily(@RequestBody List<DailyRest> dailyRestList, @PathVariable String userId) {

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.UPDATE.name());

        UserDto userDto = userService.getUserByUserId(userId);

        for (int i = 0; i < dailyRestList.size(); ++i) {
            DailyDto dailyDto = modelMapper.map(dailyRestList.get(i), DailyDto.class);
            dailyDto.setOrderNumber(dailyRestList.get(i).getOrder());
            dailyService.updateTask(dailyDto, userDto);
        }

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return operationStatusModel;
    }

    @DeleteMapping(path = "/daily/{userId}/{id}")
    public OperationStatusModel deleteDaily(@PathVariable String userId, @PathVariable Long id) {

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());

        UserDto userDto = userService.getUserByUserId(userId);
        dailyService.deleteTask(id, userDto);

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return operationStatusModel;
    }

    /** HABITS ENDPOINTS **/

    @PostMapping(path = "/habits", produces = { MediaType.APPLICATION_JSON_VALUE })
    public HabitsRest createHabits(@RequestBody HabitsRequestModel habitsRequestModel) {

        HabitsDto habitsDto = modelMapper.map(habitsRequestModel, HabitsDto.class);
        habitsDto.setOrderNumber(habitsRequestModel.getOrder());

        HabitsDto createdDto = habitsService.createTask(habitsDto);

        HabitsRest response = modelMapper.map(createdDto, HabitsRest.class);
        response.setOrder(createdDto.getOrderNumber());

        return response;
    }

    @GetMapping(path = "/habits/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<HabitsRest> getHabits(@PathVariable String userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        List<HabitsDto> habitsDtos = habitsService.getTasks(userDto);
        List<HabitsRest> response = new ArrayList<>();

        for (int i = 0; i < habitsDtos.size(); ++i) {
            HabitsRest habitsRest = modelMapper.map(habitsDtos.get(i), HabitsRest.class);
            habitsRest.setOrder(habitsDtos.get(i).getOrderNumber());
            response.add(habitsRest);
        }

        return response;
    }


    @PutMapping(path = "/habits/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public OperationStatusModel updateHabits(@RequestBody List<HabitsRest> habitsRestList, @PathVariable String userId) {

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.UPDATE.name());

        UserDto userDto = userService.getUserByUserId(userId);

        for (int i = 0; i < habitsRestList.size(); ++i) {
            HabitsDto habitsDto = modelMapper.map(habitsRestList.get(i), HabitsDto.class);
            habitsDto.setOrderNumber(habitsRestList.get(i).getOrder());
            habitsService.updateTask(habitsDto, userDto);
        }

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return operationStatusModel;
    }

    @DeleteMapping(path = "/habits/{userId}/{id}")
    public OperationStatusModel deleteHabits(@PathVariable String userId, @PathVariable Long id) {

        OperationStatusModel operationStatusModel = new OperationStatusModel();

        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());

        UserDto userDto = userService.getUserByUserId(userId);
        habitsService.deleteTask(id, userDto);

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return operationStatusModel;
    }

    /** TO DO ENDPOINTS **/

    @PostMapping(path = "/todo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ToDoRest createTodo(@RequestBody ToDoRequestModel toDoRequestModel) {

        ToDoDto toDoDto = modelMapper.map(toDoRequestModel, ToDoDto.class);
        toDoDto.setOrderNumber(toDoRequestModel.getOrder());

        ToDoDto createdDto = toDoService.createTask(toDoDto);

        ToDoRest response = modelMapper.map(createdDto, ToDoRest.class);
        response.setOrder(createdDto.getOrderNumber());

        return response;
    }

    @GetMapping(path = "/todo/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<ToDoRest> getTodo(@PathVariable String userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        List<ToDoDto> toDoDtos = toDoService.getTasks(userDto);
        List<ToDoRest> response = new ArrayList<>();

        for (int i = 0; i < toDoDtos.size(); ++i) {
            ToDoRest toDoRest = modelMapper.map(toDoDtos.get(i), ToDoRest.class);
            toDoRest.setOrder(toDoDtos.get(i).getOrderNumber());
            response.add(toDoRest);
        }

        return response;
    }

    @PutMapping(path = "/todo/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public OperationStatusModel updateTodo(@RequestBody List<ToDoRest> toDoRestList, @PathVariable String userId) {

        OperationStatusModel operationStatusModel = new OperationStatusModel();

        operationStatusModel.setOperationName(RequestOperationName.UPDATE.name());

        UserDto userDto = userService.getUserByUserId(userId);

        for (int i = 0; i < toDoRestList.size(); ++i) {
            ToDoDto toDoDto = modelMapper.map(toDoRestList.get(i), ToDoDto.class);
            toDoDto.setOrderNumber(toDoRestList.get(i).getOrder());
            toDoService.updateTask(toDoDto, userDto);
        }

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return operationStatusModel;
    }

    @DeleteMapping(path = "/todo/{userId}/{id}")
    public OperationStatusModel deleteTodo(@PathVariable String userId, @PathVariable Long id) {

        OperationStatusModel operationStatusModel = new OperationStatusModel();

        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());

        UserDto userDto = userService.getUserByUserId(userId);
        toDoService.deleteTask(id, userDto);

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return operationStatusModel;
    }


    /** USER ENDPOINTS **/

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
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
                    "https://rpg-task-organizer.herokuapp.com/#/confirm/" + createdUser.getConfirmationToken());

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
