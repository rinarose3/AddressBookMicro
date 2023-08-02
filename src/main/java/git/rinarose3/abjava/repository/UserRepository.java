package git.rinarose3.abjava.repository;

import git.rinarose3.abjava.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users where name = ?1", nativeQuery = true)
    Optional<User> findUserByName(String name);
}
