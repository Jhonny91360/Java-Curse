package com.devtalles.tu_cv_spring_boot.cv.rest;

import com.devtalles.tu_cv_spring_boot.cv.model.Person;
import com.devtalles.tu_cv_spring_boot.cv.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CvApiController {

    private final List<User> users=  new ArrayList<User>();

    CvApiController(){
        this.users.add(new User("Jhonny", "jhonnyzamsa@hotmail.com"));
        this.users.add(new User("Andrea", "andrea@gmail.com"));
        this.users.add(new User("Martina", "martina@hotmail.com"));
        this.users.add(new User("Neo", "neo@yahoo.com"));
        this.users.add(new User("Lili", "lili@outlook.com"));
    }


    @GetMapping("/cv")
    public Person getPerson(){
        return new Person("Jhonny","Zambrano", "backend dev");
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(defaultValue = "",required = false) String email,@RequestParam(defaultValue = "",required = false) String name ){
        System.out.println("email----->"+email);
        System.out.println("name----->"+name);

        List<User> filteredUsers = users.stream()
                                        .filter( user ->  user.getEmail().toLowerCase().contains(email.toLowerCase()) )
                                        .filter( user ->  user.getName().toLowerCase().contains(name.toLowerCase()) )
                                        // .filter( user -> user.getName().equalsIgnoreCase(name) && user.getEmail().equalsIgnoreCase(email) )
                                        .toList();

        System.out.println("users----->"+filteredUsers);
        return filteredUsers;
    }

    @GetMapping("/user/{index}")
    public User getUser(@PathVariable int index){

        if(index>=0 && index < users.size() ){
            return users.get(index);
        }
        return new User("Not found", "notFound");
    }
}
