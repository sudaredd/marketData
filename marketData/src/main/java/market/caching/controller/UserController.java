package market.caching.controller;

import java.util.List;

import market.caching.model.UserPayload;
import market.caching.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
    private UserRepository userRepository;
	
	 @RequestMapping(path = "/users/all",method = RequestMethod.GET)
	    public List<UserPayload> fetchUsers() {

	        return userRepository.fetchAllUsers();
	    }

	    @RequestMapping(path = "/users/first",method = RequestMethod.GET)
	    public UserPayload fetchFirst() {
	        return userRepository.firstUser();
	    }

	    @RequestMapping(path = "/users/",method = RequestMethod.GET)
	    public UserPayload findByFirstNameLastName(String firstName,String lastName ) {

	        return userRepository.userByFirstNameAndLastName(firstName,lastName);
	    }

}
