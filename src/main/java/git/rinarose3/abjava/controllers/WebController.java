package git.rinarose3.abjava.controllers;


import git.rinarose3.abjava.repository.AddressBookRepository;
import git.rinarose3.abjava.models.AddressBook;
import git.rinarose3.abjava.web.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/bibl/")
public class WebController {

    // Константы для URL-адресов API
    @Autowired
    private WebService webService;

    @Autowired
    private AddressBookRepository addressBookRepository;

    private final String rootUrl;
    private final String urlAPIablist;
    final String urlchange = "change/";
    private final List<HashMap<String, String>> menu;

    public WebController() {
        rootUrl = getClass().getAnnotation(RequestMapping.class).value()[0];
        urlAPIablist = AddressBookController.class.getAnnotation(RequestMapping.class).value()[0];
        menu = new ArrayList<>();
        HashMap<String, String> homeItem = new HashMap<>() {{
            put("title", "home");
            put("url_name", rootUrl);
            put("dis", "isEnabled");
            put("id", "main");
        }};
        menu.add(homeItem);

        HashMap<String, String> addItem = new HashMap<>() {{
            put("title", "add");
            put("url_name", rootUrl+"add/");
            put("dis", "isEnabled");
            put("id", "add");
        }};
        menu.add(addItem);

        // Элемент изменения
        HashMap<String, String> changeItem = new HashMap<>() {{
            put("title", "edit-icon");
            put("url_name", rootUrl+"change/");
            put("dis", "isDisabled");
            put("id", "change");
        }};
        menu.add(changeItem);

        // Элемент удаления
        HashMap<String, String> deleteItem = new HashMap<>() {{
            put("title", "delete-icon-1");
            put("url_name", "#");
            put("dis", "isDisabled");
            put("id", "delete");
        }};
        menu.add(deleteItem);
    }

    // Отображение главной страницы
    @GetMapping("/")
    public String index(Model model) {
        // Добавление атрибутов модели

        model.addAttribute("menu", menu);
        model.addAttribute("headerTemplate", "main");
        model.addAttribute("urlAPIablist", urlAPIablist);
        model.addAttribute("urlchange", rootUrl+urlchange);


        webService.getFieldNames();
        // Возвращение имени шаблона для отображения
        return "index";
    }

    @GetMapping("/add/")
    public String add(Model model) {
        model.addAttribute("menu", menu);
        model.addAttribute("Title", "Добавление записи");
        model.addAttribute("headerTemplate", "ab_ad_ch");
        model.addAttribute("urlAPIablist", urlAPIablist);
        model.addAttribute("rootUrl", rootUrl);
        model.addAttribute("requestType", "POST");
        // AddressBook ab = new AddressBook();
        // model.addAttribute("fields", ab.getFieldNames());
        model.addAttribute("fields", webService.getFieldNames());
        return "index";
    }

    @GetMapping("/change/")
    public String change(Model model, @RequestParam("pk") Long pid) {
        model.addAttribute("menu", menu);
        model.addAttribute("Title", "Изменение записи");
        model.addAttribute("headerTemplate", "ab_ad_ch");
        model.addAttribute("urlAPIablist", urlAPIablist);
        model.addAttribute("rootUrl", rootUrl);
        model.addAttribute("requestType", "PUT");
        // model.addAttribute("fields", webService.findById(pid));
        // model.addAttribute("id", pid);

        Optional<AddressBook> oab = addressBookRepository.findById(pid);

        if (oab.isPresent()) {
            AddressBook ab = oab.get();
            model.addAttribute("fields", ab.getFieldNames());
        }
        model.addAttribute("id", pid);
        return "index";
    }

}