package com.jun.app.modules.event.application;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jun.app.modules.account.domain.entity.Account;
import com.jun.app.modules.event.domain.entity.Event;
import com.jun.app.modules.event.endpoint.form.EventForm;
import com.jun.app.modules.event.infra.repository.EventRepository;
import com.jun.app.modules.study.domain.entity.Study;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;


	public Event createEvent(Study study, @Valid EventForm eventForm, Account account) {
		Event event = Event.from(eventForm, account, study);
        return eventRepository.save(event);
    }
}
