package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;
    @Test
    @Order(1)
    void deberiaRegistrarseUnPacienteDeNombreJuan_yRetornarSuId() {

            PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan","Perez",123456,
                   LocalDate.of(2024,03,28),new DomicilioEntradaDto("Calle",123,"Localidad","Provincia"));

           PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

          assertNotNull(pacienteSalidaDto);
          assertNotNull(pacienteSalidaDto.getId());
          assertEquals("Juan",pacienteSalidaDto.getNombre());
    }

   /* @Test
    void deberiaBuscarUnPacientePorId_yRetornarSuId1() {
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Ana","Perez",123456,
                LocalDate.of(2024,03,28),new DomicilioEntradaDto("Calle",123,"Localidad","Provincia"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        assertEquals(1,pacienteSalidaDto.getId());


    }*/
    @Test
    @Order(2)
    void deberiaEliminarseElPaciente_yLanzarseLaExcepciónAlIntentarElimiarloNuevamente(){
        try {
            pacienteService.eliminarPacientePorId(1L);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        assertThrows(ResourceNotFoundException.class,()-> pacienteService.eliminarPacientePorId(1L));

    }

   @Test
   @Order(3)
    void deberiaDevolverUnaListaVaciaDePacientes(){
       List<PacienteSalidaDto> pacientes = pacienteService.listarPacientes();
       assertTrue(pacientes.isEmpty());
   }
}