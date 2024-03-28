package com.backend.clinica.service.impl;


import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologoDeNombrePedro_yRetornarSuId() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("anbft57891","Pedro","Pintos");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Pedro",odontologoSalidaDto.getNombre());
    }
  /*  @Test
    void deberiaBuscarUnOdontologoPorId_yRetornarSuId1() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("anbft57891","Felipe","Pintos");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);
        assertEquals(1,odontologoSalidaDto.getId());



    }*/

    @Test
    @Order(2)
    void deberiaEliminarseElOdontologo_yLanzarseLaExcepciónAlIntentarElimiarloNuevamente() {
        try {
            odontologoService.eliminarOdontologo(1L);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        assertThrows(ResourceNotFoundException.class,()->odontologoService.eliminarOdontologo(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDeOdontologos(){
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();
        assertTrue(odontologos.isEmpty());
    }


}
