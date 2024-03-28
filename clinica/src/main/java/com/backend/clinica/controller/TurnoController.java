package com.backend.clinica.controller;


import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping()
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos() {
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(turnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registraTurno(@RequestBody @Valid TurnoEntradaDto turno) throws BadRequestException {
        return new ResponseEntity<>(turnoService.registrarTurno(turno), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TurnoSalidaDto> modificarTurno(@PathVariable Long id, @RequestBody TurnoEntradaDto turnoEntradaDto) throws ResourceNotFoundException {
        TurnoSalidaDto turnoModificado = turnoService.modificarTurno(id, turnoEntradaDto);
        return new ResponseEntity<>(turnoModificado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) {
        try {
            turnoService.eliminarTurno(id);
            return new ResponseEntity<>("Turno eliminado correctamente con ID: " + id, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
