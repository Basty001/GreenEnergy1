package com.example.resena1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.resena1.model.Resena;
import com.example.resena1.service.ResenaService;

@RestController
@RequestMapping("/api/v1/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    // Crear una nueva reseña
    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody Resena resena){
        Resena nueva = resenaService.crearResena(resena);
        return ResponseEntity.ok(nueva);
    }

    // Obtener todas las reseñas
    @GetMapping
    public ResponseEntity<List<Resena>> obtenerTodasLasResenas(){
        return ResponseEntity.ok(resenaService.obtenerTodasLasResenas());
    }

    // Obtener reseña por ID
    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) {
        Resena resena = resenaService.obtenerResenaPorId(id);
        return ResponseEntity.ok(resena);
    }

    // Actualizar reseña por ID
    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @RequestBody Resena resena) {
        Resena actualizada = resenaService.actualizarResena(id, resena);
        return ResponseEntity.ok(actualizada);
    }

    // Banear reseña por ID (solo admin)
    @PutMapping("/banear/{id}/{adminId}")
    public ResponseEntity<Resena> banearResena(@PathVariable Long id, @PathVariable Long adminId) {
        Resena baneada = resenaService.banearResena(id, adminId);
        return ResponseEntity.ok(baneada);
    }

    // Eliminar reseña por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarResena(@PathVariable Long id){
        resenaService.eliminarResena(id);
        return ResponseEntity.ok("Reseña con Id " + id + " eliminada correctamente");
    }
}