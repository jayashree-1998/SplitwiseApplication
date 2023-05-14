package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.controller.UserController;
import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.exceptions.ResourceNotFound;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.repositories.UserRepository;
import com.splitwise.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public APIResponse registerUser(User user) {
        // modifying mobile number this is because it will be helpful in using twilio otp services

        try {
            user.setMobileNumber("+91 "+user.getMobileNumber());
            User user1 = this.userRepository.save(user);
            logger.info("'{}' ${}$ &{}& *{}* #{}# - New user registered",user1.getEmail(),user1.getMobileNumber(),"","","");
            return new APIResponse("You have been successfully registered. Please login to continue!", true);
        }catch (Exception e){
            logger.error("'{}' ${}$ &{}& *{}* #{}# - User entered an already existing email id while registering ",user.getEmail(),"","","","");
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
        logger.info("'{}' ${}$ &{}& *{}* #{}# - Got group list by userID", user.getEmail(),"","","","");
        return user.getGroupList();
    }
}
