package com.jun.app.modules.study.application;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jun.app.modules.account.domain.entity.Account;
import com.jun.app.modules.study.domain.entity.Study;
import com.jun.app.modules.study.endpoint.form.StudyForm;
import com.jun.app.modules.study.infra.repository.StudyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {
	private final StudyRepository studyRepository;
	
	public Study createNewStudy(StudyForm studyForm, Account account) {
        Study study = Study.from(studyForm);
        study.addManager(account);
        return studyRepository.save(study);
    }
} 