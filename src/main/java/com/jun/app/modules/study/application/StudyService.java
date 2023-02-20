package com.jun.app.modules.study.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jun.app.modules.account.domain.entity.Account;
import com.jun.app.modules.study.domain.entity.Study;
import com.jun.app.modules.study.endpoint.form.StudyDescriptionForm;
import com.jun.app.modules.study.endpoint.form.StudyForm;
import com.jun.app.modules.study.infra.repository.StudyRepository;

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

    public Study getStudy(Account account, String path) {
        Study study = getStudy(path);
        if (!account.isManagerOf(study)) {
            throw new AccessDeniedException("해당 기능을 사용할 수 없습니다.");
        }
        return study;
    }

    private Study getStudy(String path) {
        Study study = studyRepository.findByPath(path);
        if (study == null) {
            throw new IllegalArgumentException(path + "에 해당하는 스터디가 없습니다.");
        }
        return study;
    }

    public void updateStudyDescription(Study study, StudyDescriptionForm studyDescriptionForm) {
        study.updateDescription(studyDescriptionForm);
    }
}