package cart.domain;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.Objects;

@Hidden
public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;

    public Member(final String email, final String password, final String nickname) {
        this(null, email, password, nickname);
    }

    public Member(final Long id, final String email, final String password, final String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public boolean checkPassword(String password) {
        return Objects.equals(this.password, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
