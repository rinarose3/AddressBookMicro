package git.rinarose3.abjava.config;

import git.rinarose3.abjava.models.User;
import git.rinarose3.abjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfoUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<User> user = userService.findUserByName(username);
        System.out.println(user.get().getPassword());
        System.out.println(user.get().getRole());
        return user.map(UserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("USERNAME NOT FOUND"));
    }
}
