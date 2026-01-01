package hello.upload.controller;

import hello.upload.domain.ItemRepository;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/itemUpload")
public class ItemController {
    private final ItemRepository itemRepository;
    private final FileStore fileStore;
    @GetMapping("/new")
    public String newItem(@ModelAttribute ItemForm form){
        return "/items/form";
    }
}
