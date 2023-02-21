package com.jun.app.modules.account.infra.predicates;

import java.util.Set;
import java.util.function.Predicate;

import com.jun.app.modules.account.domain.entity.Zone;
import com.jun.app.modules.tag.domain.entity.Tag;

public class AccountPredicates {
    public static Predicate findByTagsAndZones(Set<Tag> tags, Set<Zone> zones) {
        QAccount account = QAccount.account;
        return account.zones.any().in(zones).and(account.tags.any().in(tags));
    }
}