package com.adaskalov.ibproject.service.impl;

import com.adaskalov.ibproject.exception.MedicineNotFoundException;
import com.adaskalov.ibproject.model.Medicine;
import com.adaskalov.ibproject.repository.MedicineRepository;
import com.adaskalov.ibproject.service.MedicineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;

    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Medicine saveMedicine(Medicine medicine){
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine findById(String id) throws MedicineNotFoundException {
        return medicineRepository.findById(id).orElseThrow(MedicineNotFoundException::new);
    }

    @Override
    public Medicine delete(String id) throws MedicineNotFoundException {
        Medicine m = medicineRepository.findById(id).orElseThrow(MedicineNotFoundException::new);
        medicineRepository.delete(m);
        return m;
    }

    @Override
    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    @Override
    public List<Medicine> search(String query) {
        return medicineRepository.findAllByNameOrManufacturerName(query,query);
    }
}
