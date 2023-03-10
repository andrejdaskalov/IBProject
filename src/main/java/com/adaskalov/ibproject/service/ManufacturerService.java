package com.adaskalov.ibproject.service;

import com.adaskalov.ibproject.exception.ManufacturerNotFoundException;
import com.adaskalov.ibproject.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    public Manufacturer findById(String id) throws ManufacturerNotFoundException;
    List<Manufacturer> findAll();

    Manufacturer save(Manufacturer manufacturer);

    Manufacturer delete(String id) throws ManufacturerNotFoundException;
}
