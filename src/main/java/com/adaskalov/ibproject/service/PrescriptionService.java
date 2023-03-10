package com.adaskalov.ibproject.service;

import com.adaskalov.ibproject.exception.PrescriptionNotFoundException;
import com.adaskalov.ibproject.exception.UserNotFoundException;
import com.adaskalov.ibproject.model.Prescription;
import com.adaskalov.ibproject.model.User;

import java.util.List;

public interface PrescriptionService {
    List<Prescription> findAll();
    List<Prescription> findByPatient(Long patientId) throws UserNotFoundException;
//    Prescription addPrescriptionToPatient(Prescription prescription, User patient);
//    Prescription
    Prescription savePrescription(Prescription prescription);
    Prescription delete(String prescriptionId) throws PrescriptionNotFoundException;

    Prescription findById(String id) throws PrescriptionNotFoundException;
}
