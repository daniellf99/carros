package com.api.carros.resources;

import java.util.List;

import javax.validation.Valid;

import com.api.carros.models.Carro;
import com.api.carros.repository.CarroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(value="/carros")
public class CarroResource {

    @Autowired
    CarroRepository carroRepository;

    @GetMapping
    public List<Carro> listarCarros(){
        return carroRepository.findAll();
    }

    @PostMapping
    public Carro salvarCarro(@RequestBody @Valid Carro carro){
        return carroRepository.save(carro);
    }

    @PutMapping("/{id}")
    public Carro atualizarCarro(@PathVariable(value="id") long id, @RequestBody @Valid Carro novoCarro){
        novoCarro.setId(id);
        return carroRepository.save(novoCarro);
    }

    @GetMapping("/{id}")
    public Carro listarCarro(@PathVariable(value="id") long id){
        return carroRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public void removerCarro(@PathVariable(value="id") long id){
        carroRepository.deleteById(id);
    }
}