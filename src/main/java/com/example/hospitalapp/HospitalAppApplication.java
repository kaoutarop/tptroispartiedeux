package com.example.hospitalapp;

import com.example.hospitalapp.entities.Patient;
import com.example.hospitalapp.repository.PatientRepository;
import com.example.hospitalapp.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HospitalAppApplication {
    @Autowired
    PatientRepository patientRepository;

    //private PatientRepository patientRepository;
    public static void main(String[] args) {

        SpringApplication.run(HospitalAppApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
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
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder=passwordEncoder();
        return args -> {
            UserDetails u1= jdbcUserDetailsManager.loadUserByUsername("user11");
            if(u1==null)
                jdbcUserDetailsManager.createUser(
                    User.withUsername("user11").password(passwordEncoder().encode("1234")).roles("USER").build()
            );
            UserDetails u2= jdbcUserDetailsManager.loadUserByUsername("user22");
            if(u2==null)
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user22").password(passwordEncoder().encode("1234")).roles("USER").build()
            );
            UserDetails u3= jdbcUserDetailsManager.loadUserByUsername("admin2");
            if (u3==null)
            jdbcUserDetailsManager.createUser(
                    User.withUsername("admin2").password(passwordEncoder().encode("1234")).roles("USER","ADMIN").build()
            );

        };
    }
    @Bean
    CommandLineRunner commdLinneRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("user1","1234","user1@gmail.com","1234");
            accountService.addNewUser("user2","1234","user2@gmail.com","1234");
            accountService.addNewUser("admin","1234","admin@gmail.com","1234");


            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","ADMIN");
        };

    }
@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    }


