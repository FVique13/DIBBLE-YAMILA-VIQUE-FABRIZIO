package com.backend.clinica.controller;


import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //GET
    @GetMapping("/listar")
    public ResponseEntity<List<PacienteSalidaDto>> listarPacientes() {
        return new ResponseEntity<>(pacienteService.listarPacientes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteSalidaDto> buscarPacientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(pacienteService.buscarPacientePorId(id), HttpStatus.OK);
    }


    //POST
    @PostMapping("/registrar")
    public ResponseEntity<PacienteSalidaDto> registrarPaciente(@RequestBody @Valid PacienteEntradaDto paciente) {
        return new ResponseEntity<>(pacienteService.registrarPaciente(paciente), HttpStatus.CREATED);
    }


    //PUT
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PacienteSalidaDto> actualizarPaciente(@RequestBody PacienteEntradaDto pacienteEntradaDto, @PathVariable Long id) {
        PacienteSalidaDto pacienteActualizado = pacienteService.actualizarPaciente(pacienteEntradaDto, id);
        if (pacienteActualizado != null) {
            return new ResponseEntity<>(pacienteActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //DELETE
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarPaciente(@RequestParam Long id) throws ResourceNotFoundException {
        pacienteService.eliminarPacientePorId(id);
        return new ResponseEntity<>("Paciente eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}
