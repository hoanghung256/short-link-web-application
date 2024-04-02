package com.example.shortlinkapplication.controller;

import com.example.shortlinkapplication.dto.profile.DeleteProfileRequest;
import com.example.shortlinkapplication.dto.profile.UpdateProfileRequest;
import com.example.shortlinkapplication.entity.User;
import com.example.shortlinkapplication.repository.UserRepository;
import com.example.shortlinkapplication.security.CurrentUser;
import com.example.shortlinkapplication.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.shortlinkapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/profile")
public class UserController {

  @Autowired
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserRepository userRepository;
  private final ProjectController projectController;
  private final UserService userService;

  @CrossOrigin
  @GetMapping("user/me")
  public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    logger.info("User principal: {}", userPrincipal);
    return userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> new RuntimeException(String.valueOf(userPrincipal.getId())));
  }

  @PutMapping("update-profile")
  public User updateUserProfile(@CurrentUser UserPrincipal userPrincipal,
      @RequestBody UpdateProfileRequest request) {
    User user = projectController.getUser(userPrincipal);
    Integer userID = user.getUserID();
    logger.info("UserId: {}", userID);
    return userService.updateUserProfile(request, userID);
  }

  @DeleteMapping("delete-profile")
  public String deleteUserProfile(@CurrentUser UserPrincipal userPrincipal,
      @RequestBody DeleteProfileRequest request) {
    User user = projectController.getUser(userPrincipal);
    Integer userID = user.getUserID();
    return userService.deleteUserProfile(request, userID);
  }

}
