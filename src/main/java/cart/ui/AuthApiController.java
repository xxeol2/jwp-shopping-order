package cart.ui;

import cart.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthApiController {

    @PostMapping("login")
    public ResponseEntity<Void> login(Member member) {
        return ResponseEntity.ok().build();
    }
}
