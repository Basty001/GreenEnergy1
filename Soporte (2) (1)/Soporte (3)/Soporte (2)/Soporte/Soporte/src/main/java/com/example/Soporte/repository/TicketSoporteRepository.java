package com.example.Soporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Soporte.model.TicketSoporte;

@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long>{
    List<TicketSoporte> findByUsuarioId(Long usuarioId);
    List<TicketSoporte> findByEstadoIgnoreCase(String estado);
    List<TicketSoporte> findByTituloContainingIgnoreCase(String palabra);
   
}