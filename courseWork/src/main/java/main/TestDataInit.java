package main;

import main.entity.Clients;
import main.entity.User;
import main.repository.ClientsRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientsRepository clientsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        userRepository.save(new User("sveta", passwordEncoder.encode("0304"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("user", passwordEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));

        //clientsRepository.save(new Clients("Ivanov", "Ivan", "Ivanovich", "1111", "444444"));
        //clientsRepository.save(new Clients("Sidorov", "Ilya", "Olegovich", "1111", "444444"));

    }
}
