package com.jun.app.modules.settings.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.app.WithAccount;
import com.jun.app.account.infra.repository.AccountRepository;
import com.jun.app.modules.account.application.AccountService;
import com.jun.app.modules.account.domain.entity.Account;
import com.jun.app.modules.account.domain.entity.Zone;
import com.jun.app.modules.tag.domain.entity.Tag;
import com.jun.app.modules.tag.infra.repository.TagRepository;
import com.jun.app.modules.zone.infra.repository.ZoneRepository;

import main.endpoint.controller.SettingsController;
import main.endpoint.controller.TagForm;
import main.endpoint.controller.ZoneForm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SettingsControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired AccountRepository accountRepository;
    @Autowired TagRepository tagRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired ObjectMapper objectMapper;
    @Autowired AccountService accountService;
    @Autowired ZoneRepository zoneRepository;
    

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
        zoneRepository.deleteAll();
    }
    @Test
    @DisplayName("????????? ??????: ????????? ??????")
    @WithAccount("jaime")
    void updateProfile() throws Exception {
        String bio = "??? ??? ??????";
        mockMvc.perform(post(SettingsController.SETTINGS_PROFILE_URL)
                        .param("bio", bio)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingsController.SETTINGS_PROFILE_URL))
                .andExpect(flash().attributeExists("message"));
        Account jaime = accountRepository.findByNickname("jaime");
        assertEquals(bio, jaime.getProfile().getBio());
    }

    @Test
    @DisplayName("????????? ??????: ????????? ??????")
    @WithAccount("jaime")
    void updateProfileWithError() throws Exception {
        String bio = "35??? ????????? ??????35??? ????????? ??????35??? ????????? ??????35??? ????????? ??????";
        mockMvc.perform(post(SettingsController.SETTINGS_PROFILE_URL)
                        .param("bio", bio)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_PROFILE_VIEW_NAME))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"));
        Account jaime = accountRepository.findByNickname("jaime");
        assertNull(jaime.getProfile().getBio());
    }

    @Test
    @DisplayName("????????? ??????")
    @WithAccount("jaime")
    void updateProfileForm() throws Exception {
        mockMvc.perform(get(SettingsController.SETTINGS_PROFILE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_PROFILE_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"));
    }

    @Test
    @DisplayName("???????????? ?????? ???")
    @WithAccount("jaime")
    void updatePasswordForm() throws Exception {
        mockMvc.perform(get(SettingsController.SETTINGS_PASSWORD_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_PASSWORD_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("passwordForm"));
    }

    @Test
    @DisplayName("???????????? ??????: ????????? ??????")
    @WithAccount("jaime")
    void updatePassword() throws Exception {
        mockMvc.perform(post(SettingsController.SETTINGS_PASSWORD_URL)
                        .param("newPassword", "12341234")
                        .param("newPasswordConfirm", "12341234")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingsController.SETTINGS_PASSWORD_URL))
                .andExpect(flash().attributeExists("message"));
        Account account = accountRepository.findByNickname("jaime");
        assertTrue(passwordEncoder.matches("12341234", account.getPassword()));
    }

    @Test
    @DisplayName("???????????? ??????: ????????? ??????(?????????)")
    @WithAccount("jaime")
    void updatePasswordWithNotMatchedError() throws Exception {
        mockMvc.perform(post(SettingsController.SETTINGS_PASSWORD_URL)
                        .param("newPassword", "12341234")
                        .param("newPasswordConfirm", "12121212")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_PASSWORD_VIEW_NAME))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("passwordForm"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    @DisplayName("???????????? ??????: ????????? ??????(??????)")
    @WithAccount("jaime")
    void updatePasswordWithLengthError() throws Exception {
        mockMvc.perform(post(SettingsController.SETTINGS_PASSWORD_URL)
                        .param("newPassword", "1234")
                        .param("newPasswordConfirm", "1234")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_PASSWORD_VIEW_NAME))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("passwordForm"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ???")
    @WithAccount("jaime")
    void updateNotificationForm() throws Exception {
        mockMvc.perform(get(SettingsController.SETTINGS_NOTIFICATION_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_NOTIFICATION_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("notificationForm"));
    }

    @Test
    @DisplayName("?????? ?????? ??????: ????????? ??????")
    @WithAccount("jaime")
    void updateNotification() throws Exception {
        mockMvc.perform(post(SettingsController.SETTINGS_NOTIFICATION_URL)
                        .param("studyCreatedByEmail", "true")
                        .param("studyCreatedByWeb", "true")
                        .param("studyRegistrationResultByEmail", "true")
                        .param("studyRegistrationResultByWeb", "true")
                        .param("studyUpdatedByEmail", "true")
                        .param("studyUpdatedByWeb", "true")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingsController.SETTINGS_NOTIFICATION_URL))
                .andExpect(flash().attributeExists("message"));
        Account account = accountRepository.findByNickname("jaime");
        assertTrue(account.getNotificationSetting().isStudyCreatedByEmail());
        assertTrue(account.getNotificationSetting().isStudyCreatedByWeb());
        assertTrue(account.getNotificationSetting().isStudyRegistrationResultByEmail());
        assertTrue(account.getNotificationSetting().isStudyRegistrationResultByWeb());
        assertTrue(account.getNotificationSetting().isStudyUpdatedByEmail());
        assertTrue(account.getNotificationSetting().isStudyUpdatedByWeb());
    }

    @Test
    @DisplayName("????????? ?????? ???")
    @WithAccount("jaime")
    void updateNicknameForm() throws Exception {
        mockMvc.perform(get(SettingsController.SETTINGS_ACCOUNT_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_ACCOUNT_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("nicknameForm"));
    }

    @Test
    @DisplayName("????????? ??????: ????????? ??????")
    @WithAccount("jaime")
    void updateNickname() throws Exception {
        String newNickname = "jaime2";
        mockMvc.perform(post(SettingsController.SETTINGS_ACCOUNT_URL)
                        .param("nickname", newNickname)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingsController.SETTINGS_ACCOUNT_URL))
                .andExpect(flash().attributeExists("message"));
        Account account = accountRepository.findByNickname(newNickname);
        assertEquals(newNickname, account.getNickname());
    }

    @Test
    @DisplayName("????????? ??????: ??????(??????)")
    @WithAccount("jaime")
    void updateNicknameWithShortNickname() throws Exception {
        String newNickname = "j";
        mockMvc.perform(post(SettingsController.SETTINGS_ACCOUNT_URL)
                        .param("nickname", newNickname)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_ACCOUNT_VIEW_NAME))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("nicknameForm"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    @DisplayName("????????? ??????: ??????(??????)")
    @WithAccount("jaime")
    void updateNicknameWithDuplicatedNickname() throws Exception {
        String newNickname = "jaime";
        mockMvc.perform(post(SettingsController.SETTINGS_ACCOUNT_URL)
                        .param("nickname", newNickname)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_ACCOUNT_VIEW_NAME))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("nicknameForm"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    @DisplayName("?????? ?????? ???")
    @WithAccount("jaime")
    void updateTagForm() throws Exception {
        mockMvc.perform(get(SettingsController.SETTINGS_TAGS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_TAGS_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("whitelist"))
                .andExpect(model().attributeExists("tags"));
    }


    @Test
    @DisplayName("?????? ??????")
    @WithAccount("jaime")
    void addTag() throws Exception {
        TagForm tagForm = new TagForm();
        String tagTitle = "newTag";
        tagForm.setTagTitle(tagTitle);
        mockMvc.perform(post(SettingsController.SETTINGS_TAGS_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagForm))
                        .with(csrf()))
                .andExpect(status().isOk());

        Tag tag = tagRepository.findByTitle(tagTitle).orElse(null);
        assertNotNull(tag);
        assertTrue(accountRepository.findByNickname("jaime").getTags().contains(tag));
    }

    @Test
    @DisplayName("?????? ??????")
    @WithAccount("jaime")
    void removeTag() throws Exception {
        Account jaime = accountRepository.findByNickname("jaime");
        Tag newTag = tagRepository.save(Tag.builder().title("newTag").build());
        accountService.addTag(jaime, newTag);
        assertTrue(jaime.getTags().contains(newTag));
        TagForm tagForm = new TagForm();
        String tagTitle = "newTag";
        tagForm.setTagTitle(tagTitle);
        mockMvc.perform(post(SettingsController.SETTINGS_TAGS_URL + "/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagForm))
                        .with(csrf()))
                .andExpect(status().isOk());
        assertFalse(jaime.getTags().contains(newTag));
    }
    
    @Test
    @DisplayName("????????? ?????? ?????? ?????? ???")
    @WithAccount("jaime")
    void updateZonesForm() throws Exception {
        mockMvc.perform(get(SettingsController.SETTINGS_ZONE_URL))
                .andExpect(view().name(SettingsController.SETTINGS_ZONE_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("whitelist"))
                .andExpect(model().attributeExists("zones"));
    }

    @Test
    @DisplayName("????????? ?????? ?????? ??????")
    @WithAccount("jaime")
    void addZone() throws Exception {
        Zone testZone = Zone.builder().city("test").localNameOfCity("????????????").province("????????????").build();
        zoneRepository.save(testZone);
        ZoneForm zoneForm = new ZoneForm();
        zoneForm.setZoneName(testZone.toString());
        mockMvc.perform(post(SettingsController.SETTINGS_ZONE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zoneForm))
                        .with(csrf()))
                .andExpect(status().isOk());
        Account account = accountRepository.findByNickname("jaime");
        assertTrue(account.getZones().contains(testZone));
    }

    @Test
    @DisplayName("????????? ?????? ?????? ??????")
    @WithAccount("jaime")
    void removeZone() throws Exception {
        Account jaime = accountRepository.findByNickname("jaime");
        Zone testZone = Zone.builder().city("test").localNameOfCity("????????????").province("????????????").build();
        zoneRepository.save(testZone);
        accountService.addZone(jaime, testZone);
        assertTrue(jaime.getZones().contains(testZone));
        ZoneForm zoneForm = new ZoneForm();
        zoneForm.setZoneName(testZone.toString());
        mockMvc.perform(post(SettingsController.SETTINGS_ZONE_URL + "/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zoneForm))
                        .with(csrf()))
                .andExpect(status().isOk());
        assertFalse(jaime.getZones().contains(testZone));
    }
    
}