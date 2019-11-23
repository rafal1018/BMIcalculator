package akademiakodu.BMIcalculator.Service;

import akademiakodu.BMIcalculator.Model.Result;
import akademiakodu.BMIcalculator.Model.Role;
import akademiakodu.BMIcalculator.Model.User;
import akademiakodu.BMIcalculator.Repository.ResultRepository;
import akademiakodu.BMIcalculator.Repository.RoleRepository;
import akademiakodu.BMIcalculator.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;


@Service("BMIService")
public class BMIService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ResultRepository resultRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public BMIService(UserRepository userRepository,
                      RoleRepository roleRepository,
                      ResultRepository resultRepository,
                      BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.resultRepository = resultRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id) != null ?
                userRepository.findById(id).get() : null;
    }

    public void addResult(Result result) {
        resultRepository.save(result);
    }
}