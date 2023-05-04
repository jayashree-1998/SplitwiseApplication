package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.exceptions.ResourceNotFound;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.repositories.UserRepository;
import com.splitwise.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public APIResponse registerUser(User user) {
        // modifying mobile number this is because it will be helpful in using twilio otp services
        try {
            user.setMobileNumber("+91 "+user.getMobileNumber());
            User user1 = this.userRepository.save(user);
            return new APIResponse("You have been successfully registered. Please login to continue!", true);
        }catch (Exception e){
            return new APIResponse("Email id already exists!Please try again",false);
        }
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return this.userRepository.findUserByEmailAndPassword(email,password);
    }

    @Override
    public Set<Group> getAllGroupsByUserID(String userID) {
        User user = this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFound("User", "Id"));
        return user.getGroupList();
    }
}
