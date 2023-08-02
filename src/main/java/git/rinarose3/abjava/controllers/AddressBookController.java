package git.rinarose3.abjava.controllers;


import git.rinarose3.abjava.models.AddressBook;
import git.rinarose3.abjava.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ablist/")
@Tag(name = "Address Book", description = "API для управления списком адресной книги")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping
    //@Tag(name = "Address Book", description = "API для получения списка контактов в адресной книге")
    @Operation(summary = "Получить список контактов в адресной книге")
    public List<AddressBook> getPeople() {
        return addressBookService.getAllAddressBook();
    }

    @PostMapping
    //@Tag(name = "Address Book", description = "API для создания контакта в адресной книге")
    @Operation(summary = "Создать контакт в адресной книге")
    public ResponseEntity<AddressBook> postPeople(@RequestBody AddressBook addressBook) {
        return ResponseEntity.ok(addressBookService.saveAddressBook(addressBook));
    }

    @PutMapping
    //@Tag(name = "Address Book", description = "API для обновления контакта в адресной книге")
    @Operation(summary = "Обновить контакт в адресной книге")
    public ResponseEntity<AddressBook> putPeople(@RequestBody AddressBook addressBook) {
        try {
            return ResponseEntity.ok(addressBookService.updateAddressBook(addressBook));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    //@Tag(name = "Address Book", description = "API для удаления контакта в адресной книге")
    @Operation(summary = "Удалить контакт в адресной книге")
    public ResponseEntity<String> deletePeople(@RequestParam("id") Long id) {
        try {
            addressBookService.deleteAddressBookById(id);
            return ResponseEntity.ok("Удаление произведено успешно");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
