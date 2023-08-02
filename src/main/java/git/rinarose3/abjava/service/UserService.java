package git.rinarose3.abjava.service;

import git.rinarose3.abjava.models.User;
import git.rinarose3.abjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //@Autowired
    //private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public String saveUser(User user) {
        user.setPassword(/*passwordEncoder.encode(*/user.getPassword()/*)*/);
        User user1 =  userRepository.save(user);
        if (user1 != null && user1.getName() != "") {
            return "USER SAVE";
        }
        return "PLEASE, TRY AGAIN. USER NOT SAVE";
    }
}
