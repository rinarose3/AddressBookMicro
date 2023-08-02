package git.rinarose3.abjava.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
@Table(name = "addressbook")
@Schema(description = "Represents an address book entry")
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id")
    private Long id;
    @Schema(description = "Имя")
    private String name;
    @Schema(description = "Фамилия")
    private String fam;
    @Schema(description = "Телефон")
    private String tel;
    @Schema(description = "Почта")
    private String mail;
    @Schema(description = "Заметки")
    private String note;

    public List<HashMap<String,String>> getFieldNames() {
        List<HashMap<String, String>> fieldNames = new ArrayList<>();

        HashMap<String, String> itemName = new HashMap<>() {{
            put("field", "name");
            put("label", "Имя");
            put("value", getName());
        }};
        fieldNames.add(itemName);

        HashMap<String, String> itemFam = new HashMap<>() {{
            put("field", "fam");
            put("label", "Фамилия");
            put("value", getFam());
        }};
        fieldNames.add(itemFam);

        HashMap<String, String> itemTel = new HashMap<>() {{
            put("field", "tel");
            put("label", "Телефон");
            put("value", getTel());
        }};
        fieldNames.add(itemTel);

        HashMap<String, String> itemMail = new HashMap<>() {{
            put("field", "mail");
            put("label", "mail");
            put("value", getMail());
        }};
        fieldNames.add(itemMail);

        HashMap<String, String> itemNote = new HashMap<>() {{
            put("field", "note");
            put("label", "Заметки");
            put("value", getNote());
        }};
        fieldNames.add(itemNote);

        return fieldNames;
    }

    public void setData(AddressBook addressBook) {
        this.name = addressBook.getName();
        this.fam = addressBook.getFam();
        this.tel = addressBook.getTel();
        this.mail = addressBook.getMail();
        this.note = addressBook.getNote();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getId() {
        return id;
    }
}
