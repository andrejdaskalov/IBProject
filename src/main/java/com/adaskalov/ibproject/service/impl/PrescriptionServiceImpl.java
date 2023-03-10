package com.adaskalov.ibproject.service.impl;

import com.adaskalov.ibproject.exception.PrescriptionNotFoundException;
import com.adaskalov.ibproject.exception.UserNotFoundException;
import com.adaskalov.ibproject.model.Prescription;
import com.adaskalov.ibproject.model.User;
import com.adaskalov.ibproject.repository.PrescriptionRepository;
import com.adaskalov.ibproject.repository.UserRepository;
import com.adaskalov.ibproject.service.PrescriptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, UserRepository userRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }

    @Override
    public List<Prescription> findByPatient(Long patientId) throws UserNotFoundException {
        User patient = userRepository.findById(patientId).orElseThrow(UserNotFoundException::new);
        return prescriptionRepository.findAllByPatient(patient);
    }


    @Override
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription delete(String prescriptionId) throws PrescriptionNotFoundException {
        Prescription p = prescriptionRepository.findById(prescriptionId).orElseThrow(PrescriptionNotFoundException::new);
        prescriptionRepository.delete(p);
        return p;
    }
    @Override
    public Prescription findById(String id) throws PrescriptionNotFoundException {
        return prescriptionRepository.findById(id).orElseThrow(PrescriptionNotFoundException::new);
    }

}
