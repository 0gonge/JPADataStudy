package study.example.data_jpa.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") //이 클래스 관점에서 생각해주면 된다.
    private List<Member> members = new ArrayList<>();

    //constructure
    public Team(String name) {
        this.name = name;
    }
}
