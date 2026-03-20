package com.dunnwr.taskmanagerapi.springdata.user;

import com.dunnwr.taskmanagerapi.models.user.*;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryAdapter implements UserRepository, UserDetailsService {

    private final SpringDataUserRepository springDataUserRepository;

    public UserRepositoryAdapter(SpringDataUserRepository springDataUserRepository){
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public User save(User user) {

        UserEntity entity = new UserEntity(
                user.getId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getPassword().getValue(),
                user.getEmail().getValue(),
                user.getGender().name(),
                user.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet())
        );

        UserEntity savedEntity = springDataUserRepository.save(entity);

        return User.restore(
                savedEntity.getId(),
                savedEntity.getFirstName(),
                savedEntity.getMiddleName(),
                savedEntity.getLastName(),
                Password.fromEncoded(savedEntity.getPassword()),
                Email.fromStored(savedEntity.getEmail()),
                Gender.from(savedEntity.getGender()),
                savedEntity.getRoles().stream()
                        .map(Role::valueOf)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return springDataUserRepository.findByEmail(email.getValue())
                .map(savedUser -> User.restore(
                        savedUser.getId(),
                        savedUser.getFirstName(),
                        savedUser.getMiddleName(),
                        savedUser.getLastName(),
                        Password.fromEncoded(savedUser.getPassword()),
                        Email.fromStored(savedUser.getEmail()),
                        Gender.from(savedUser.getGender()),
                        savedUser.getRoles().stream()
                                .map(Role::valueOf)
                                .collect(Collectors.toSet())
                ));
    }

    @Override
    public void deleteById(Long id) {
        springDataUserRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return springDataUserRepository.existsByEmail(email.getValue());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return springDataUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
