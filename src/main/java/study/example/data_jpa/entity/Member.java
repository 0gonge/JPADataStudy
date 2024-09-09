package study.example.data_jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"}) //이건그냥 확인용
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id") //pk
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) //다대일 관계, JPA에서 모든 연관관계는 기본적으로 모두 LAZY(지연관계)로 세팅
    @JoinColumn(name = "team_id") //FK
    private Team team;



    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null) {
            changeTeam(team);
        }
    }

    //연관관계 세팅하는 메서드 필요
    //멤버는 팀을 변경할 수 있다.
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
