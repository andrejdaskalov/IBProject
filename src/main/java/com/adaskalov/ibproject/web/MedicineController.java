package com.adaskalov.ibproject.web;

import com.adaskalov.ibproject.exception.ManufacturerNotFoundException;
import com.adaskalov.ibproject.exception.MedicineNotFoundException;
import com.adaskalov.ibproject.model.Manufacturer;
import com.adaskalov.ibproject.model.Medicine;
import com.adaskalov.ibproject.service.ManufacturerService;
import com.adaskalov.ibproject.service.MedicineService;
import com.adaskalov.ibproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicine")
public class MedicineController {
    private final MedicineService medicineService;

    private final ManufacturerService manufacturerService;
    private final UserService userService;



    public MedicineController(MedicineService medicineService, ManufacturerService manufacturerService, UserService userService) {
        this.medicineService = medicineService;
        this.manufacturerService = manufacturerService;
        this.userService = userService;
    }

    @GetMapping
    public String Index(@RequestParam(required = false) String query,
                        Model model){
        List<Medicine> results;
        if (query != null && !query.isEmpty()){
            results = medicineService.search(query);
        } else results = medicineService.findAll();
        model.addAttribute("title","All Medicine");
        model.addAttribute("medicineList",results);
        return "medicine";
    }
    @GetMapping("/add")
    public String showAddPage(Model model){
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        model.addAttribute("manufacturers",manufacturers);
        return "add-medicine";
    }
    @PostMapping("/save-meds/")
    public String addMedicine(
                              @RequestParam String name,
                              @RequestParam String recommendedUsage,
                              @RequestParam String manufacturerId
                              ) throws Exception {
        Medicine m = new Medicine();
        m.setName(name);
        Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
        m.setManufacturer(manufacturer);
        m.setRecommendedUsage(recommendedUsage);
//        User u = userService.getUserById(Long.parseLong(principal.getName()));
//        u.getMedicine().add(m);
        medicineService.saveMedicine(m);
//        userService.saveUser(u);
        return "redirect:/medicine";
    }
    @PostMapping("/save-meds/{id}")
    public String updateMedicine(@PathVariable String id,
                              @RequestParam String name,
                              @RequestParam String recommendedUsage,
                              @RequestParam String manufacturerId
    ) throws ManufacturerNotFoundException, MedicineNotFoundException {
        Medicine m = medicineService.findById(id);
        m.setName(name);
        Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
        m.setManufacturer(manufacturer);
        m.setRecommendedUsage(recommendedUsage);
        medicineService.saveMedicine(m);
        return "redirect:/medicine";
    }
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable String id, Model model) throws MedicineNotFoundException {
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        model.addAttribute("manufacturers",manufacturers);
        Medicine medicine = medicineService.findById(id);
        model.addAttribute("medicine",medicine);
        return "add-medicine";
    }

/*
    @PostMapping("/save-medicine")
    public String editMedicine(){
        //edit logic
        return "redirect://";
    }
*/

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable String id) throws MedicineNotFoundException {
        medicineService.delete(id);
        return "redirect:/medicine";
    }

/*
    @GetMapping("filter")
    public String searchMedicine(){
        //search retrieve logic
        // add result to model
        return "main";
    }
*/

}
