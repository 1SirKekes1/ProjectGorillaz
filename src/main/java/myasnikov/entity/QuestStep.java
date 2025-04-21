package myasnikov.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static myasnikov.entity.EndType.NONE;

@Entity
@Table(schema = "game", name = "quest_step")
@Getter @Setter
public class QuestStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestChoice> choices = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @Enumerated(EnumType.STRING)
    @Column(name = "end_type", columnDefinition = "enum('NONE', 'WIN', 'LOSE')")
    private EndType endType = NONE;
}
