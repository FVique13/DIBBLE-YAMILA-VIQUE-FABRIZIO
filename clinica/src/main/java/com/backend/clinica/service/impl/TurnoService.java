package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.repository.TurnoRepositiry;
import com.backend.clinica.service.ITurnoService;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


import java.util.List;
import java.util.Optional;


@Service

public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepositiry turnoRepositiry;
    private final ModelMapper modelMapper;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(TurnoRepositiry turnoRepositiry, ModelMapper modelMapper, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepositiry = turnoRepositiry;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {
        TurnoSalidaDto turnoSalidaDto;
        PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteId());
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologoId());

        String pacienteNoEnBdd = "El paciente no se encuentra registrado";
        String odontologoNoEnBdd = "El odontologo no se encuentra registrado";
        String ambosnulos = "No se encuentran reigstrados ni el odontologo ni el paciente";


        if (paciente == null || odontologo == null) {
            if (paciente == null && odontologo == null) {
                LOGGER.error(ambosnulos);
                throw new BadRequestException(ambosnulos);
            } else if (paciente == null) {
                LOGGER.error(pacienteNoEnBdd);
                throw new BadRequestException(pacienteNoEnBdd);
            } else {
                LOGGER.error(odontologoNoEnBdd);
                throw new BadRequestException(odontologoNoEnBdd);
            }

        } else {
            Turno turnoNuevo = turnoRepositiry.save(modelMapper.map(turnoEntradaDto, Turno.class));
            turnoSalidaDto = entidadADtoSalida(turnoNuevo);
            LOGGER.info("Turno registrado correctamente");
        }

        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {

        List<TurnoSalidaDto> turnoSalidaDtos = turnoRepositiry.findAll().stream().map(this::entidadADtoSalida).toList();
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnoSalidaDtos));
        return turnoSalidaDtos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {

        Turno turnoBuscado = turnoRepositiry.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = entidadADtoSalida(turnoBuscado);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else LOGGER.error("Turno no encontrado");
        return turnoEncontrado;
    }


    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        Optional<Turno> optionalTurno = turnoRepositiry.findById(id);

        if (optionalTurno.isPresent()) {
            Turno turnoAEliminar = optionalTurno.get();
            turnoRepositiry.delete(turnoAEliminar);
            LOGGER.info("Turno eliminado correctamente con ID: {}", id);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado con ID: " + id);
        }


    }

    @Override
    public TurnoSalidaDto modificarTurno(Long id, TurnoEntradaDto turnoEntradaDto) throws ResourceNotFoundException {
        Optional<Turno> optionalTurno = turnoRepositiry.findById(id);

        if (optionalTurno.isPresent()) {
            Turno turnoExistente = optionalTurno.get();

            BeanUtils.copyProperties(turnoEntradaDto, turnoExistente);

            Turno turnoActualizado = turnoRepositiry.save(turnoExistente);
            TurnoSalidaDto turnoSalidaDto = entidadADtoSalida(turnoActualizado);

            return turnoSalidaDto;


        } else {
            throw new ResourceNotFoundException("Turno no encontrado con ID: " + id);
        }
    }


    private PacienteSalidaDto pacienteSalidaDtoASalidaTurnoDto(Long id) {
        return pacienteService.buscarPacientePorId(id);
    }

    private OdontologoSalidaDto odontologoSalidaDtoASalidaTurnoDto(Long id) {
        return odontologoService.buscarOdontologoPorId(id);
    }

    private TurnoSalidaDto entidadADtoSalida(Turno turno) {
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(pacienteSalidaDtoASalidaTurnoDto(turno.getPaciente().getId()));
        turnoSalidaDto.setOdontologoSalidaDto(odontologoSalidaDtoASalidaTurnoDto(turno.getOdontologo().getId()));
        return turnoSalidaDto;
    }

}
