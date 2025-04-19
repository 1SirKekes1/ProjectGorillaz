package myasnikov.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "game", name = "quest_choice")
@Getter
@Setter
public class QuestChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private QuestStep nextStep;

}
