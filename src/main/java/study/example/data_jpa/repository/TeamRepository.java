package study.example.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.data_jpa.entity.Team;

public interface TeamRepository extends JpaRepository <Team, Long> {
}
