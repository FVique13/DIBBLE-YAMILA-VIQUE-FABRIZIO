package com.backend.clinica.service;

import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IPacienteService {
    PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente);

    List<PacienteSalidaDto> listarPacientes();


    PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id);

    PacienteSalidaDto buscarPacientePorId(Long id);

    void eliminarPacientePorId(Long id) throws ResourceNotFoundException;
}



