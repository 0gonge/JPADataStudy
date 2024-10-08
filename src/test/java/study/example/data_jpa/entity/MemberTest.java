package study.example.data_jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.example.data_jpa.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;
//team이랑 member가 연관관계가 잘 적용되는지 테스트
    @Test
    public void testEntity() {
       Team teamA = new Team("teamA");
       Team teamB = new Team("teamB");
       em.persist(teamA);
       em.persist(teamB); //각각을 persist로 저장

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamA);
        Member member3 = new Member("member3", 10, teamB);
        Member member4 = new Member("member4", 10, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush(); //jpa에서 곧 바로 DB에 가는게 아니라 영속성 컨텍스트에 저장한다.
        //flush를 하면 강제로 db에 insertquery를 날린다.
        em.clear();

        //확인
        List<Member> members = em.createQuery("select  m from Member m", Member.class)
                .getResultList();

        for(Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }
    }
    @Test
    public void JpaEventBaseEntity() throws Exception {
        //given
        Member member = new Member("member1");
        memberRepository.save(member); //@PrePersist

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush(); //@PreUpdate
        em.clear();
        //when
        Member findMember = memberRepository.findById(member.getId()).get();
        
        //then
        System.out.println("findMember.getUpdateDate = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreateDate = " + findMember.getCreatedDate());
        System.out.println("findMember.getCreatedBy = " + findMember.getCreatedBy());
        System.out.println("findMember.getLastModifiedBy = " + findMember.getLastModifiedBy());
    }
}