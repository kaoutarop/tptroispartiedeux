package com.example.hospitalapp;

import com.example.hospitalapp.entities.Patient;
import com.example.hospitalapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class HospitalAppApplication implements CommandLineRunner {
@Autowired
private PatientRepository patientRepository;
    public static void main(String[] args) {

        SpringApplication.run(HospitalAppApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null,"mohammed",new Date(),false,34));
        patientRepository.save(new Patient(null,"kaoutar",new Date(),false,123));
        patientRepository.save(new Patient(null,"ahmed",new Date(),true,45));




    }
}
