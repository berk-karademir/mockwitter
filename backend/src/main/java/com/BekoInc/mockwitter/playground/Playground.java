package com.BekoInc.mockwitter.playground;

import com.BekoInc.mockwitter.dto.UserDTO;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.mapper.TweetMapper;
import com.BekoInc.mockwitter.repository.UserRepository;
import com.BekoInc.mockwitter.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class Playground {
    private static List<String> exampleList;


    @PostConstruct
    public void init() {
       if(exampleList == null) {
           exampleList = new ArrayList<>();
       }

        exampleList.add("A");
        exampleList.add("B");
        exampleList.add("C");
    }

    @GetMapping("/public/search/{something}")
    public String publicTest2(@PathVariable String something,
                              @RequestParam(name = "query", required = false) String query) throws NoSuchElementException {
        if (query == null || query.isEmpty()) {
            return "Public area v2 - Path Variable: " + something + " - Query param not provided.";
        }


        if (exampleList.contains(query)) {
            return "Value '" + query + "' found in exampleList!";

        } else {
            throw new NoSuchElementException("Examplelistte " + query + " yok");
        }
    }



    @GetMapping("/public/testarea")
    public String publicTest() {

        return "This is public-access test area! Should be accessible for any user, no restrictions!";
    }

    @GetMapping("/admin/testarea")
    public String adminTest() {
        return "This is admin-test area! Should be accessible by only admin!";
    }
}
