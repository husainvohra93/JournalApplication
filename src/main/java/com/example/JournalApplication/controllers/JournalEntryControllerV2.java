package com.example.JournalApplication.controllers;

import com.example.JournalApplication.entity.JournalEntry;
import com.example.JournalApplication.entity.UserEntity;
import com.example.JournalApplication.service.JournalEntryService;
import com.example.JournalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userService.findByUserName(userName);
        if(user!=null){
            List<JournalEntry> all = user.getJournalEntries();
            if(all!=null && !all.isEmpty()){
                return new ResponseEntity<>(all, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Journal Entry not found for the requested User.",HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("User provided does not exists",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).toList();

        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<Object> deleteJournal(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteEntry(myId, userName);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<String> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newJE) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).toList();
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()){
                JournalEntry oldJE = journalEntry.get();
                oldJE.setTitle(!newJE.getTitle().isEmpty() ? newJE.getTitle() : oldJE.getTitle());
                oldJE.setContent(newJE.getContent() != null && !newJE.getContent().isEmpty() ? newJE.getContent() : oldJE.getContent());
                journalEntryService.saveEntry(oldJE, userName);
                return new ResponseEntity<>("Journal Updated Successfully... !!!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}