package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.ApiResponse;
import com.splitwise.userservice.repositories.UserRepository;
import com.splitwise.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public ApiResponse registerUser(User user) {
        // modifying mobile number this is because it will be helpful in using twilio otp services
        try {
            user.setMobileNumber("+91 "+user.getMobileNumber());
            User user1 = this.userRepository.save(user);
            return new ApiResponse("SuccessfullyRegistered", true);
        }catch (Exception e){
            return new ApiResponse("RegistrationFailed",false);
        }
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return this.userRepository.findUserByEmailAndPassword(email,password);
    }

    @Override
    public Set<Group> getAllGroupsByUserID(String userID) {
        User user = this.userRepository.findById(userID).orElseThrow();
        return user.getGroupList();
    }
}
