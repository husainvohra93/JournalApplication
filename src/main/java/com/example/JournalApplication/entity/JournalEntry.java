package com.example.JournalApplication.entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
/* Above @Data Annotation removes th need to use @Getter @Setter @NoArgs @AllArgs @EqualsAndHashCode
@Getter //This is from Lombok library defined in the pom which eliminates creating Getter manually in JournalEntry Entity
@Setter //This is from Lombok library defined in the pom which eliminates creating Setter manually in JournalEntry Entity
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder*/
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}