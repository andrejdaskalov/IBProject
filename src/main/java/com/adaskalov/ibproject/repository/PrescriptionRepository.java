package com.adaskalov.ibproject.repository;

import com.adaskalov.ibproject.model.Prescription;
import com.adaskalov.ibproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    List<Prescription> findAllByPatient(User patient);
}
