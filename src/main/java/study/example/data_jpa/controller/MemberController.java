package study.example.data_jpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.example.data_jpa.dto.MemberDto;
import study.example.data_jpa.entity.Member;
import study.example.data_jpa.repository.MemberRepository;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    // id로 특정 멤버를 찾아서 username을 반환
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        // Optional로 안전하게 처리
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));
        return member.getUsername();
    }

    // PathVariable로 멤버 엔티티를 받아서 username 반환
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    // 페이징 처리된 멤버 리스트를 반환 (DTO로 변환)
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        // Page<Member> 객체를 Page<MemberDto>로 매핑하여 변환한 결과를 반환
        return page.map(MemberDto::new);
    }

    // 초기 데이터 100개 생성
    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
