package com.example.obejercicioSesion11y12.controller;

import com.example.obejercicioSesion11y12.entitites.Laptop;
import com.example.obejercicioSesion11y12.repository.LaptopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    private LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @GetMapping("/laptops")
    public ResponseEntity<List<Laptop>> findAll(){
        List<Laptop> laptops = laptopRepository.findAll();

        if(laptops.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(laptops);
    }

    @GetMapping("/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id){
        Optional<Laptop> optionalLaptop = laptopRepository.findById(id);

        if(optionalLaptop.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalLaptop.get());
    }

    @PostMapping("/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop){
        if(laptop.getId() != null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(laptopRepository.save(laptop));
    }

    @PutMapping("/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){
        if(laptop.getId() == null){
            return ResponseEntity.badRequest().build();
        }

        if(!laptopRepository.existsById(laptop.getId())){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(laptopRepository.save(laptop));
    }

    @DeleteMapping("/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){
        if(!laptopRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        try {
          laptopRepository.deleteById(id);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/laptops")
    public ResponseEntity<Laptop> deleteAll(){
        if(laptopRepository.findAll().isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
