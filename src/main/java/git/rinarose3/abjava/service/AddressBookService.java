package git.rinarose3.abjava.service;

import git.rinarose3.abjava.models.AddressBook;
import git.rinarose3.abjava.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.List;
import java.util.Optional;
import git.rinarose3.abjava.kafka.MyBean;
@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private MyBean myBean;

    public List<AddressBook> getAllAddressBook() {
        return addressBookRepository.findAll();
    }

    public AddressBook saveAddressBook(AddressBook addressBook) {
        myBean.someMethod();
        System.out.println("Приветик");
        return addressBookRepository.save(addressBook);
    }

    public AddressBook updateAddressBook(AddressBook addressBook) {
        AddressBook existingAddressBook = this.getAddressBookById(addressBook.getId());
        existingAddressBook.setData(addressBook);

        return addressBookRepository.save(existingAddressBook);
    }

    public void deleteAddressBookById(Long id) {
        AddressBook existingAddressBook = this.getAddressBookById(id);
        addressBookRepository.deleteById(id);
    }

    private AddressBook getAddressBookById(Long id) {
        Optional<AddressBook> addressBookOptional = addressBookRepository.findById(id);
        return addressBookOptional.orElseThrow(() -> new RuntimeException("Ошибка поиска элемента по id = "+ id));
    }
}
