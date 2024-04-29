package com.tehnovsky.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tehnovsky.task.util.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "document_number", unique = true, nullable = false)
    private String uniqueDocumentNumber;

    @Column(name = "document_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DocumentType documentType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", uniqueDocumentNumber='" + uniqueDocumentNumber + '\'' +
                ", documentType=" + documentType +
                '}';
    }
}
