package com.gestion.eventos.api.controller;

import com.gestion.eventos.api.domain.Event;
import com.gestion.eventos.api.dto.EventRequestDto;
import com.gestion.eventos.api.dto.EventResponseDto;
import com.gestion.eventos.api.mapper.EventMapper;
import com.gestion.eventos.api.service.IEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final IEventService eventService;

    private final EventMapper eventMapper;


    @GetMapping
    public List<EventResponseDto> getAllEvents(){
        List<Event> events = eventService.findAll();

        return eventMapper.toEventResponseDtoList(events);
    }

    //@Valid para que se valide con las reglas declaradas en "EventRequestDto" con la dependencia jakarta.validation.constraints
    //@RequestBody para que se espere esa estrucuta del dato en el body de la peticion
    @PostMapping
    public ResponseEntity<EventResponseDto>  createEvent (@Valid @RequestBody EventRequestDto eventRequestDto){
        Event event = eventMapper.toEntity(eventRequestDto);
        Event savedEvent = eventService.save(event);

        EventResponseDto responseDto = eventMapper.toResponseDto(savedEvent);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
