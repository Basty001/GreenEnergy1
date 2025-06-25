package com.example.Usuarios12.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Usuarios12.model.Usuario;
import com.example.Usuarios12.service.UsuarioService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> users = usuarioService.obtenerUsuarios();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")  // este sirve para busar por id
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> crearUsuario(@RequestBody Map<String, Object> datos) {
        try {
            String username = (String) datos.get("username");
            String password = (String) datos.get("password");
            Long rolId = Long.valueOf(datos.get("rolId").toString());
            Usuario nuevo = usuarioService.crearUsuario(username, password, rolId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Map<String, Object> datos) {
        try {
            String username = (String) datos.get("username");
            String password = (String) datos.get("password");
            Long rolId = datos.get("rolId") != null ? Long.valueOf(datos.get("rolId").toString()) : null;
            Usuario actualizado = usuarioService.actualizarUsuario(id, username, password, rolId);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> datos) {
        try {
            String username = datos.get("username");
            String password = datos.get("password");

            if (username == null || password == null) {
                return ResponseEntity.badRequest().body("Faltan campos 'username' o 'password'");
            }

            boolean valido = usuarioService.validarCredenciales(username, password);
            return valido
                ? ResponseEntity.ok("Login exitoso")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error interno: " + e.getMessage());
        }
    }
}
