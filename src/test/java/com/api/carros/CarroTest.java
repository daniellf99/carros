package com.api.carros;

import com.api.carros.models.Carro;
import com.api.carros.repository.CarroRepository;
import com.api.carros.resources.CarroResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(CarroResource.class)
public class CarroTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    CarroRepository carroRepository;

    Carro CARRO_1 = new Carro(1L, "Polo", "Volkswagen", "2019");
    Carro CARRO_2 = new Carro(2L, "Golf", "Volkswagen", "2017");

    @Test
    public void DeveListarCarros() throws Exception {
        List<Carro> carros = new ArrayList<>(Arrays.asList(CARRO_1, CARRO_2));

        Mockito.when(carroRepository.findAll()).thenReturn(carros);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/carros")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].modelo", Matchers.is("Golf")));
    }

    @Test
    public void DeveListarCarro() throws Exception {

        Mockito.when(carroRepository.findById(CARRO_1.getId())).thenReturn(CARRO_1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/carros/" + CARRO_1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo", Matchers.is("Polo")));
    }

    @Test
    public void DeveCriarCarro() throws Exception {
        Carro filme = Carro.builder()
                .modelo("Polo")
                .marca("Volkswagen")
                .ano("1992")
                .build();

        Mockito.when(carroRepository.save(filme)).thenReturn(filme);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/carros")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(filme));

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo", Matchers.is("Polo")));
    }

    @Test
    public void DeveAtualizarCarro() throws Exception {
        Carro corpoAtualizacao = Carro.builder()
                .modelo("Gol")
                .marca("Volkswagen")
                .ano("1992")
                .build();

        Carro carroAtualizado = Carro.builder()
                .id(1L)
                .modelo("Gol")
                .marca("Volkswagen")
                .ano("1992")
                .build();

        Mockito.when(carroRepository.findById(CARRO_1.getId())).thenReturn(CARRO_1);
        Mockito.when(carroRepository.save(carroAtualizado)).thenReturn(carroAtualizado);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/carros/" + CARRO_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(corpoAtualizacao));

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo", Matchers.is("Gol")));
    }

    @Test
    public void DeveRemoverCarro() throws Exception {
        Mockito.when(carroRepository.findById(CARRO_1.getId())).thenReturn(CARRO_1);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/carros/" + CARRO_1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
