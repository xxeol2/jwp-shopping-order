package cart.application.service;

import cart.application.repository.MemberRepository;
import cart.domain.Member;
import cart.exception.auth.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;

    public AuthService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(final String email, final String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(UnauthorizedException::new);
        if (!member.checkPassword(password)) {
            throw new UnauthorizedException();
        }
        return member;
    }
}
