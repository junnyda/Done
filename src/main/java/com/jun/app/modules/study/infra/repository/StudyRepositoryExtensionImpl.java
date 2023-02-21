package com.jun.app.modules.study.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.jun.app.modules.account.domain.entity.Zone;
import com.jun.app.modules.study.domain.entity.Study;

public class StudyRepositoryExtensionImpl extends QuerydslRepositorySupport implements
StudyRepositoryExtension {

    public StudyRepositoryExtensionImpl() {
        super(Study.class);
    }

    @Override
    public List<Study> findByAccount(Set<Tag> tags, Set<Zone> zones) {
      QStudy study = QStudy.study;
      JPQLQuery<Study> query = from(study).where(study.published.isTrue()
              .and(study.closed.isFalse())
              .and(study.tags.any().in(tags))
              .and(study.zones.any().in(zones)))
          .leftJoin(study.tags, QTag.tag).fetchJoin()
          .leftJoin(study.zones, QZone.zone).fetchJoin()
          .orderBy(study.publishedDateTime.desc())
          .distinct()
          .limit(9);
      return query.fetch();
    }
  }