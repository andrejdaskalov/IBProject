package com.adaskalov.ibproject.service.impl;

import com.adaskalov.ibproject.exception.ManufacturerNotFoundException;
import com.adaskalov.ibproject.model.Manufacturer;
import com.adaskalov.ibproject.repository.ManufacturerRepository;
import com.adaskalov.ibproject.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer findById(String id) throws ManufacturerNotFoundException {
        return manufacturerRepository.findById(id).orElseThrow(ManufacturerNotFoundException::new);
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }
    @Override
    public Manufacturer save(Manufacturer manufacturer){
        return manufacturerRepository.save(manufacturer);
    }
    @Override
    public Manufacturer delete(String id) throws ManufacturerNotFoundException {
        Manufacturer m = manufacturerRepository.findById(id).orElseThrow(ManufacturerNotFoundException::new);
        manufacturerRepository.delete(m);
        return m;
    }
}
