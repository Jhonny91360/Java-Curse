package com.gestion.eventos.api.service;

import com.gestion.eventos.api.domain.Event;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    List<Event> findAll();
    Event save(Event event);
    Event findById(Long id);
    void  deleteById(Long id);
}
