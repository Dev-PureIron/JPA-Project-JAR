package com.app.projectjar.entity.diary;

import com.app.projectjar.audit.Period;
import com.app.projectjar.entity.member.Member;
import com.app.projectjar.type.DiaryType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name ="TBL_DIARY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private String diaryTitle;
    @NotNull
    private String diaryContent;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DiaryType diaryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
