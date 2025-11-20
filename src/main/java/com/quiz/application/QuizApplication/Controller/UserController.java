package com.quiz.application.QuizApplication.Controller;

import com.quiz.application.QuizApplication.Model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    List<User> userList = new ArrayList<>();

    @GetMapping("/greet")
    public String getHi() {
        return "hi";
    }
    @GetMapping("/listUsers")
    public List<User> getAllUsers(){
        return this.userList;
    }
    @PostMapping("/createUser")
    public void createNewUser(@RequestBody User newUser){
        this.userList.add(newUser);
    }
}
