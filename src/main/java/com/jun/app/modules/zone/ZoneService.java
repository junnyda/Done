package com.jun.app.modules.zone;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.jun.app.modules.account.domain.entity.Zone;
import com.jun.app.modules.zone.infra.repository.ZoneRepository;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;

    @PostConstruct
    public void initZoneData() throws IOException {
        if (zoneRepository.count() == 0) {
            Resource resource = new ClassPathResource("zones_kr.csv");
            List<String> allLines = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);
            List<Zone> zones = allLines.stream().map(Zone::map).collect(Collectors.toList());
            zoneRepository.saveAll(zones);
        }
    }
}