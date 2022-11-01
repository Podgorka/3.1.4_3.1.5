package demo.service;


import demo.model.User;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class UserServiceImp implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> index() {
        return userRepository.findAll();
    }


    public User showUser(long id) {
        return userRepository.getReferenceById(id);
    }

    @Transactional
    public User showUserByUsername(String email){
        return userRepository.findUserByEmail(email);
    }

    public boolean saveUser(User user) {
        User userFromDb = userRepository.findUserByEmail(user.getUsername());
        if (userFromDb != null){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void updateUser(User user) {
        User userFromDb = userRepository.getReferenceById(user.getId());

        if (user.getPassword().equals(userFromDb.getPassword()) &&
                user.getPassword().startsWith("$2a$12$") && user.getPassword().length() == 60){
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }


    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

}
