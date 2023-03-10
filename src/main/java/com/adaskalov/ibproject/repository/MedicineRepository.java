package com.adaskalov.ibproject.repository;

import com.adaskalov.ibproject.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,String> {
    public List<Medicine> findAllByNameOrManufacturerName(String name, String manName);
}
