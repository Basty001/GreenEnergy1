package com.example.Proyecto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Proyecto.Model.Proyecto;
import com.example.Proyecto.Service.ProyectoService;

@RestController
@RequestMapping("/api/v1/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // Crear un nuevo proyecto
    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto){
        Proyecto nuevo = proyectoService.creaProyecto(proyecto);
        return ResponseEntity.ok(nuevo);
    }

    // Obtener todos los proyectos
    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerTodosLosProyectos(){
        return ResponseEntity.ok(proyectoService.obtenerTodosLProyectos());
    }

    // Obtener proyecto por ID 
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
        return ResponseEntity.ok(proyecto);
    }

    // Actualizar proyecto por ID 
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        Proyecto actualizado = proyectoService.actualizarpProyecto(id, proyecto);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar proyecto por ID 
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProyecto(@PathVariable Long id){
        proyectoService.elininarProyecto(id);
        return ResponseEntity.ok("proyecto con Id " + id + " eliminar correctamente");
    }
}