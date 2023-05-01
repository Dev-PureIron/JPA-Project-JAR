package com.app.projectjar.entity.inquire;

import com.app.projectjar.audit.Period;
import com.app.projectjar.type.AnswerType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name ="TBL_ANSWER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends Period {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private String answerContent;
    @Enumerated(EnumType.STRING)
    private AnswerType answerStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INQUIRE_ID")
    private Inquire inquire;
}
