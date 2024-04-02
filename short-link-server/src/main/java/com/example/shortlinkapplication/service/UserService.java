package com.example.shortlinkapplication.service;

import com.example.shortlinkapplication.dto.profile.DeleteProfileRequest;
import com.example.shortlinkapplication.dto.profile.UpdateProfileRequest;
import com.example.shortlinkapplication.entity.User;
import com.example.shortlinkapplication.repository.UserRepository;
import com.example.shortlinkapplication.security.UserPrincipal;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor
@Data
public class UserService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return UserPrincipal.create(user);
  }

  @Transactional
  public UserDetails loadUserById(Integer id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceAccessException("User"));
    return UserPrincipal.create(user);
  }

  public User updateUserProfile(UpdateProfileRequest request, Integer userID) {
    Optional<User> optionalUser = userRepository.findById(userID);

    logger.info("User name: {}", request.getName());
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      user.setName(request.getName());
      user.setEmail(request.getEmail());
      userRepository.save(user);
      logger.info("User: {}", user);
      return user;
    }
    return null;
  }

  public String deleteUserProfile(DeleteProfileRequest request, Integer userID) {
    Optional<User> optionalUser = userRepository.findById(userID);
    String verify = "confirm delete account";
    if (optionalUser.isPresent() && request.getVerify().equals(verify)) {
      userRepository.deleteById(userID);
      return "Delete account success!";
    }
    return null;
  }
}
