package com.example.hospitalapp.web;

import com.example.hospitalapp.entities.Patient;
import com.example.hospitalapp.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.naming.Binding;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "4") int s,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(p, s));
        model.addAttribute("ListPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", p);
        model.addAttribute("keyword", kw);
        return "patients";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE ADMIN')")
    public String delete(Long id, String keyword, int page) {
        patientRepository.deleteById(id);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/patients")

   @ResponseBody
   public List<Patient> listPatients(){
        return patientRepository.findAll();
   }
    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ROLE ADMIN')")

   public String formPatients(Model model)
    {   model.addAttribute("patient",new Patient());
        return "formPatients";
   }
    @PostMapping(path = "/admin/save")
   public String save(Model model, @Valid Patient patient, BindingResult bindingResult,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "") String keyword ){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
   }
    @GetMapping("/admin/editPatients")
    @PreAuthorize("hasRole('ROLE ADMIN')")

    public String editPatients(Model model, Long id,String keyword,int page)
    {   Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null)throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",new Patient());
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatients";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

}
