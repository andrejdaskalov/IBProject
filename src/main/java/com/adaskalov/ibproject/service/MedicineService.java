package com.adaskalov.ibproject.service;

import com.adaskalov.ibproject.exception.MedicineNotFoundException;
import com.adaskalov.ibproject.model.Medicine;

import java.util.List;

public interface MedicineService {
    public Medicine saveMedicine(Medicine medicine);
    Medicine findById(String id) throws MedicineNotFoundException;
    Medicine delete(String id) throws MedicineNotFoundException;
    List<Medicine> findAll();
    List<Medicine> search(String query);
}
