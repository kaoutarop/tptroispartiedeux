package com.example.hospitalapp;

import com.example.hospitalapp.entities.Patient;
import com.example.hospitalapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class HospitalAppApplication  {
@Autowired
PatientRepository patientRepository;
//private PatientRepository patientRepository;
    public static void main(String[] args) {

        SpringApplication.run(HospitalAppApplication.class, args);
    }

//@Bean
CommandLineRunner commandLineRunner(PatientRepository patientRepository){
    return args ->

    {
        patientRepository.save(new Patient(null, "mohammed", new Date(), false, 134));
        patientRepository.save(new Patient(null, "kaoutar", new Date(), false, 123));
        patientRepository.save(new Patient(null, "ahmed", new Date(), true, 145));
        patientRepository.save(new Patient(null, "hassan", new Date(), false, 134));
        patientRepository.save(new Patient(null, "amin", new Date(), false, 123));

        patientRepository.save(new Patient(null, "salma", new Date(), true, 145));

        patientRepository.findAll().forEach(p -> {
            System.out.println(p.getNom());
        });
    };
    }
    }


