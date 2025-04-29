package com.example.JournalApplication.service;

/*Business Logic is written in Service */

import com.example.JournalApplication.entity.JournalEntry;
import com.example.JournalApplication.entity.UserEntity;
import com.example.JournalApplication.reposotirory.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName, String Action) throws NullPointerException {
        try {
            UserEntity user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJE = journalEntryRepository.save(journalEntry);
            if(Objects.equals(Action, "Create")) {
                user.getJournalEntries().add(savedJE);
                userService.saveEntry(user);
            }
        }catch (Exception e) {
            logger.info("Exception Catch block of process JournalEntryService");
            throw new NullPointerException("UserName not found in the database");
        }
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public boolean deleteEntry(ObjectId myId, String userName) {
        boolean removed = false;
        try {
            UserEntity user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x->x.getId().equals(myId));
            if (removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(myId);
            }

        } catch (Exception e) {
            logger.error("Exception Catch block of process JournalEntryService");

            throw new RuntimeException("An Error occurred while deleting entry",e);
        }
        return removed;
    }

}
