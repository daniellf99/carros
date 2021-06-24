package com.api.carros.repository;

import com.api.carros.models.Carro;

import org.springframework.data.jpa.repository.JpaRepository;

    public interface CarroRepository extends JpaRepository<Carro, Long>{

        Carro findById(long id);

    }
