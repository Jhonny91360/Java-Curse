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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final IEventService eventService;

    private final EventMapper eventMapper;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity< List<EventResponseDto> > getAllEvents(){
        List<Event> events = eventService.findAll();
        List<EventResponseDto> eventsDTo = eventMapper.toEventResponseDtoList(events);
        return ResponseEntity.ok(eventsDTo);
    }

    //@Valid para que se valide con las reglas declaradas en "EventRequestDto" con la dependencia jakarta.validation.constraints
    //@RequestBody para que se espere esa estrucuta del dato en el body de la peticion
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EventResponseDto>  createEvent (@Valid @RequestBody EventRequestDto eventRequestDto){
        Event event = eventMapper.toEntity(eventRequestDto);
        Event savedEvent = eventService.save(event);

        EventResponseDto responseDto = eventMapper.toResponseDto(savedEvent);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id){
        Event event = eventService.findById(id);

        EventResponseDto eventResponseDto = eventMapper.toResponseDto(event);

        //return ResponseEntity.ok(eventResponseDto);  // otra manera
        return new ResponseEntity<>(eventResponseDto,HttpStatus.FOUND);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id ,@Valid @RequestBody EventRequestDto requestDto){
        Event eventToUpdate = eventService.findById(id);
        eventMapper.updateEventFromDto(requestDto, eventToUpdate);
        Event updatedEvent = eventService.save(eventToUpdate);

        return ResponseEntity.ok(eventMapper.toResponseDto(updatedEvent));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id){
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
