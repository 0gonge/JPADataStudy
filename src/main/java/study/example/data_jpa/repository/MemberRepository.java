package study.example.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.data_jpa.entity.Member;

public interface MemberRepository extends JpaRepository <Member, Long> {

}
