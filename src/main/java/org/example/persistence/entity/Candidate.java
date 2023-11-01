package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidates")
public class Candidate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "father_name")
    private String fatherName;
    @Column(name = "description")
    private String description;
    @ManyToMany
    @JoinTable(
            name = "candidate_direction",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "direction_id")
    )
    private List<Direction> directions;

    @OneToOne
    @JoinColumn(name = "photo_file_id")
    private File photo;

    @OneToOne
    @JoinColumn(name = "cv_file_id")
    private File cv;
}
