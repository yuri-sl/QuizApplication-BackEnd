package com.quiz.application.QuizApplication.Controller;

import com.quiz.application.QuizApplication.Model.User;
import com.quiz.application.QuizApplication.dtos.PartialUserDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final List<User> userList = new ArrayList<>();

    @GetMapping("/greet")
    public String getHi() {
        return "hi";
    }
    @GetMapping("/listUsers")
    public List<User> getAllUsers(){
        return this.userList;
    }
    @PostMapping("/createUser")
    public ResponseEntity<User> createNewUser(@RequestBody User newUser){
        this.userList.add(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newUser);
    }

    //Post básico => Criar novo usuário básico
    @PostMapping()
    public ResponseEntity<User> createNewUserDefinitive(@RequestBody User newUser){
        userList.add(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //2 Buscar usuário por id - GET users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        for(User u: this.userList){
            if(u.getId() == id){
                return ResponseEntity.status(HttpStatus.FOUND).body(u);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //3) Deletar o usuário - DELTE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserByIdFromList(@PathVariable int id){
        for(User u: this.userList){
            if(u.getId() == id){
                this.userList.remove(u);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //4 - Criar o usuário com validação de email
    @PostMapping("/check-username")
    public ResponseEntity<?> createUserCheckingEmail(@RequestBody User newUser){
        if(newUser.getUsername().isEmpty() || newUser.getUsername().isBlank()){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O email é obrigatório!");
        }
        for(User u: this.userList){
            if(u.getUsername().equals(newUser.getUsername())){
                return  ResponseEntity.status(HttpStatus.CONFLICT).body("O usuário já está cadastrado no sitema!");
            }
        }
        this.userList.add(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    //5 - PATCH Parcial - Patch/users/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUserById(@PathVariable int id , @RequestBody PartialUserDTO ptUserDTO ){
        for(User u: this.userList){
            if(u.getId() == id){
                if(ptUserDTO.getUsername() != null && !ptUserDTO.getUsername().isEmpty()){
                    u.setUsername(ptUserDTO.getUsername());
                }
                if(!ptUserDTO.getPassword().isBlank() && !ptUserDTO.getPassword().isEmpty()){
                    u.setPassword(ptUserDTO.getPassword());
                }
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuário não encontrado!");
    }

    //6 - Endpoint de health - GET/users/health
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> getHealthStatusReDone(){
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

/*
    @PostMapping("/users")
    @ResponseStatus
    public ResponseEntity createUser(@RequestBody User newUserCreated){
        this.userList.add(newUserCreated);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newUserCreated);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus
    public ResponseEntity deleteUser(@PathVariable long id ){
        boolean removed = userList.removeIf(user -> user.getId() == id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/usersEvolved")
    @ResponseStatus
    public ResponseEntity<User> createUserEvolved(@RequestBody User newUser){
        this.userList.add(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserFromList(@PathVariable Long id){
        boolean found = false;
        for(User u : this.userList){
            if(u.getId() == id){
                found = true;
                return  ResponseEntity.ok(u);
            }
        }
        return  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus
    public ResponseEntity<Void> deleteUserFromList(@PathVariable Long id){
        boolean found = false;
        for(User u: this.userList){
            if(u.getId() == id){
                this.userList.remove(u);
                found = true;
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping()
    @ResponseStatus
    public ResponseEntity<?> createUserCheckEmail(@RequestBody User newUser){
        if(newUser.getUsername().isBlank() || newUser.getUsername().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email é obrigatório");
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
    }
    @PostMapping
    @ResponseStatus
    public ResponseEntity<?> createUserNoDuplicateEmail(@RequestBody User newUser){
        boolean valid;
        for(User u : this.userList){
            if(u.getUsername() == newUser.getUsername()){
                valid = false;
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já foi cadastrado!");
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    @PatchMapping("/{id}")
    @ResponseStatus
    public ResponseEntity<?> atualizarParcialmente(@RequestBody String username, String password,@PathVariable int id){
        for (User u: this.userList){
            if(u.getId() == id){
                this.userList.remove(u);
                User newUserCreated = new User(id,username,password,"user");
                this.userList.add(newUserCreated);
                return  ResponseEntity.status(HttpStatus.OK).build();
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return null;
    }
    @GetMapping("/health")
    @ResponseStatus
    public ResponseEntity<?> getHealthStatus(){
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("{'status': 'UP'}");
    }
    */
}
