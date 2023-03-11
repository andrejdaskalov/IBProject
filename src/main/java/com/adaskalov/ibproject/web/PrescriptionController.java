package com.adaskalov.ibproject.web;

import com.adaskalov.ibproject.exception.PrescriptionNotFoundException;
import com.adaskalov.ibproject.model.Medicine;
import com.adaskalov.ibproject.model.Prescription;
import com.adaskalov.ibproject.model.User;
import com.adaskalov.ibproject.service.MedicineService;
import com.adaskalov.ibproject.service.PrescriptionService;
import com.adaskalov.ibproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A controller responsible for managing Prescriptions. Accessible only to role DOCTOR (See SecurityConfig)
 */
@Controller
@RequestMapping("/prescription")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    private final MedicineService medicineService;
    private final UserService userService;


    public PrescriptionController(PrescriptionService prescriptionService, UserService userService, MedicineService medicineService, UserService userService1) {
        this.prescriptionService = prescriptionService;
        this.medicineService = medicineService;
        this.userService = userService1;
    }

    @GetMapping
    public String mainPage(@RequestParam(required = false) Long patientId, Model model) throws Exception {
        List<Prescription> prescriptions;
        if (patientId == null) prescriptions = prescriptionService.findAll();
        else prescriptions = prescriptionService.findByPatient(patientId);

        model.addAttribute("title","All prescriptions:");
        model.addAttribute("prescriptions",prescriptions);
        return "prescriptions";
    }

    @GetMapping("/add")
    public String showAddPage(Model model){
        List<Medicine> medicineList = medicineService.findAll();
        List<User> users = userService.findAllPatients();
        model.addAttribute("medicineList",medicineList);
        model.addAttribute("patients",users);

        model.addAttribute("title","Add new Prescription:");
        return "add-prescription";
    }
    @PostMapping("/save/")
    public String addPrescription(
            @RequestParam Long patientId,
            @RequestParam String medicineId
    ) throws Exception {
        Prescription p = new Prescription();
        p.setMedicine(medicineService.findById(medicineId));
        p.setPatient(userService.getUserById(patientId));
        prescriptionService.savePrescription(p);
        return "redirect:/prescription";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable String id, Model model) throws PrescriptionNotFoundException {
        List<Medicine> medicineList = medicineService.findAll();
        List<User> users = userService.findAllPatients();
        model.addAttribute("medicineList",medicineList);
        model.addAttribute("patients",users);

        Prescription p = prescriptionService.findById(id);
        model.addAttribute("prescription",p);


        model.addAttribute("title","Edit Prescription:");
        return "add-prescription";
    }
    @PostMapping("/save/{id}")
    public String editPrescription( @PathVariable String id,
            @RequestParam Long patientId,
            @RequestParam String medicineId
    ) throws Exception {
        Prescription p = prescriptionService.findById(id);
        p.setMedicine(medicineService.findById(medicineId));
        p.setPatient(userService.getUserById(patientId));
        prescriptionService.savePrescription(p);
        return "redirect:/prescription";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws PrescriptionNotFoundException {
        prescriptionService.delete(id);
        return "redirect:/prescription";
    }

}
