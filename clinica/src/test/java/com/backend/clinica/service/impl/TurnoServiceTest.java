package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {
@Autowired
private TurnoService turnoService;
    @Test
    void debriaRegistrarUnTurnoConOdontlogoNombrePedro_yPacienteNombreMarcela() {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto();
    }

    @Test
    void listarTurnos() {
    }

    @Test
    void eliminarTurno() {
    }
}