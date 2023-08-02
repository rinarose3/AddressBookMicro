package git.rinarose3.abjava.repository;

import git.rinarose3.abjava.models.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
}