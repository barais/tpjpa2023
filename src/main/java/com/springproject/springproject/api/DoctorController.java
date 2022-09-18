package com.springproject.springproject.api;

import com.springproject.springproject.domain.Doctor;
import com.springproject.springproject.service.DoctorDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class DoctorController {

    private final DoctorDAO dao;

    DoctorController(DoctorDAO dao) {
        this.dao = dao;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/doctors")
    List<Doctor> all() {
        return dao.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/doctors")
    Doctor newDoctor(@RequestBody Doctor newDoctor) {
        return dao.save(newDoctor);
    }

    // Single item

    @GetMapping("/doctors/{id}")
    Doctor one(@PathVariable Long id) {

        return dao.findById(id).orElseThrow();
    }

    @PutMapping("/doctors/{id}")
    Doctor replaceDoctor(@RequestBody Doctor newDoctor, @PathVariable Long id) {

        return dao.findById(id)
                .map(doctor -> {
                    doctor.setFirstName(newDoctor.getFirstName());
                    doctor.setLastName(newDoctor.getLastName());
                    doctor.setSpecialisation(newDoctor.getSpecialisation());
                    return dao.save(doctor);
                })
                .orElseGet(() -> {
                    newDoctor.setId(id);
                    return dao.save(newDoctor);
                });
    }

    @DeleteMapping("/doctors/{id}")
    void deleteDoctor(@PathVariable Long id) {
        dao.deleteById(id);
    }
}