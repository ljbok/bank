package shop.mtcoding.bank.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    // select * from user where username = ?
    Optional<User> findByUsername(String username); // Jpa QueryMethod 작동! (↔ jpql native query 와 다름)
    // save - 이미 만들어져 있음.

}
