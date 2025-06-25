package com.example.Notificaciones1.controller;

import com.example.Notificaciones1.model.Notificacion;
import com.example.Notificaciones1.service.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    
    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Notificacion>> getAllNotificaciones() {
        return ResponseEntity.ok(notificacionService.getAllNotificaciones());
    }

    @PostMapping
    public ResponseEntity<Notificacion> saveNotificacion(
            @RequestBody Notificacion notificacion,
            @RequestHeader("X-User-Id") Long userId) {
        notificacion.setUsuarioId(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.saveNotificacion(notificacion));
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("#usuarioId == authentication.principal.id")
    public ResponseEntity<List<Notificacion>> getNotificacionesByUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.getNotificacionesByUsuarioId(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    @PreAuthorize("#usuarioId == authentication.principal.id")
    public ResponseEntity<List<Notificacion>> getNotificacionesNoLeidasByUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.getNotificacionesNoLeidasByUsuarioId(usuarioId));
    }

    @PutMapping("/{id}/marcar-leida")
    public ResponseEntity<Notificacion> marcarNotificacionComoLeida(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id, userId));
    }
}