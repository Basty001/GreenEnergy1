package com.example.Soporte.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Soporte.model.TicketSoporte;
import com.example.Soporte.service.SoporteService;

@RestController
@RequestMapping("/api/v1/soporte")
public class TicketController {
    @Autowired
    private SoporteService soporteService;

    @GetMapping
    public ResponseEntity<List<TicketSoporte>> obtenerTicket() {
        List<TicketSoporte> lista = soporteService.getTicket();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<?> crearTicket(@RequestBody TicketSoporte nuevo) {
        try {
            TicketSoporte ticket = soporteService.saveTicket(nuevo);
            return ResponseEntity.status(201).body(ticket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TicketSoporte>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<TicketSoporte> lista = soporteService.obtenerPorUsuarioId(usuarioId);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TicketSoporte>> obtenerPorEstado(@PathVariable String estado) {
        List<TicketSoporte> lista = soporteService.obtenerPorEstado(estado);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TicketSoporte>> buscarPorTitulo(@RequestParam String titulo) {
        List<TicketSoporte> lista = soporteService.buscarPorTitulo(titulo);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
}