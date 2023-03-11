package com.adaskalov.ibproject.web;

import com.adaskalov.ibproject.exception.ManufacturerNotFoundException;
import com.adaskalov.ibproject.model.Manufacturer;
import com.adaskalov.ibproject.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * A controller responsible for managing the Manufacturer model. Accessible only to ADMIN role. (See SecurityConfig)
 */
@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String Index(Model model){
        model.addAttribute("manufacturers",manufacturerService.findAll());
        return "manufacturers";
    }
    @GetMapping("/add")
    public String showAddPage(Model model){
        model.addAttribute("title","Add new Manufacturer");
        return "add-manufacturer";
    }
    @PostMapping("/save/")
    public String addManufacturer(@RequestParam String name) {
        Manufacturer m = new Manufacturer();
        m.setName(name);
        manufacturerService.save(m);
        return "redirect:/manufacturer";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable String id, Model model) throws ManufacturerNotFoundException {
        Manufacturer m = manufacturerService.findById(id);
        model.addAttribute("manufacturer",m);

        model.addAttribute("title","Edit Manufacturer");
        return "add-manufacturer";
    }
    @PostMapping("/save/{id}")
    public String editPrescription( @PathVariable String id,
                                    @RequestParam String name
    ) throws Exception {
        Manufacturer m = manufacturerService.findById(id);
        m.setName(name);
        manufacturerService.save(m);
        return "redirect:/manufacturer";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws  ManufacturerNotFoundException {
        manufacturerService.delete(id);
        return "redirect:/manufacturer";
    }
}
