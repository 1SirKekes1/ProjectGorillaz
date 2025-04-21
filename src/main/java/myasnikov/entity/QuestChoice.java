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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id") // ссылка на текущий шаг
    private QuestStep step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_step_id") // ссылка на следующий шаг
    private QuestStep nextStep;

    @Transient
    private String nextStepReference;
}
