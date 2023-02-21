package com.jun.app.modules.event.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jun.app.modules.account.domain.entity.Account;
import com.jun.app.modules.account.support.CurrentUser;
import com.jun.app.modules.event.endpoint.form.EventForm;
import com.jun.app.modules.study.application.StudyService;
import com.jun.app.modules.study.domain.entity.Study;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/study/{path}")
@RequiredArgsConstructor
public class EventController {
	
	private final StudyService studyService;
	
	
	@GetMapping("/new-event")
    public String newEventForm(@CurrentUser Account account, @PathVariable String path, Model model) {
        Study study = studyService.getStudyToUpdateStatus(account, path); // (2)
        model.addAttribute(study); // (3)
        model.addAttribute(account);
        model.addAttribute(new EventForm());
        return "event/form";
    }
}
