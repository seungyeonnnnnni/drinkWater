package drinking.water.repository;

import drinking.water.domain.User;
import drinking.water.domain.userweb.RegisterForm;
import drinking.water.domain.userweb.UserRes;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    UserRes save(RegisterForm registerForm);

    Optional<User> findById(Long userId);

    Optional<User> findByLoginId(String loginId);

    List<User> findAll();
}
