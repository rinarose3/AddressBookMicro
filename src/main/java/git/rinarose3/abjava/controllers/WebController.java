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


    /*@Autowired
    private WebService webService;
    @Autowired
    private AddressBookRepository addressBookRepository;*/

    // Константы для URL-адресов API
    private final WebService webService;
    private final AddressBookRepository addressBookRepository;
    private final String rootUrl;
    private final String urlAPIablist;
    final String urlchange = "change/";

    private final List<HashMap<String, String>> menu;

    @Autowired
    public WebController(WebService webService, AddressBookRepository addressBookRepository) {
        this.webService = webService;
        this.addressBookRepository = addressBookRepository;

        rootUrl = getClass().getAnnotation(RequestMapping.class).value()[0];
        urlAPIablist = AddressBookController.class.getAnnotation(RequestMapping.class).value()[0];
        menu = new ArrayList<>();

        menu.add(itemMenu("home", rootUrl, "isEnabled", "main"));
        menu.add(itemMenu("add", rootUrl+"add/", "isEnabled", "add"));
        menu.add(itemMenu("edit-icon", rootUrl+"change/", "isDisabled", "change"));
        menu.add(itemMenu("delete-icon-1", "#", "isDisabled", "delete"));
    }

    // Отображение главной страницы
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("menu", menu);
        model.addAttribute("headerTemplate", "main");
        model.addAttribute("urlAPIablist", urlAPIablist);
        model.addAttribute("urlchange", rootUrl+urlchange);
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
        model.addAttribute("fields", webService.getFieldNames(pid));
        model.addAttribute("id", pid);
        return "index";
    }
    private  HashMap<String, String> itemMenu(String title, String url, String dis, String id){
        HashMap<String, String> item = new HashMap<>();
            item.put("title", title);
            item.put("url_name", url);
            item.put("dis", dis);
            item.put("id", id);
        return item;
    }
}