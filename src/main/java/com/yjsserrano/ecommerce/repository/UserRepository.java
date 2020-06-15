package com.yjsserrano.ecommerce.repository;

import com.yjsserrano.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository - {@link User}
 * <p>
 * Here it will be requested all the info about the {@link User} from the database
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="http://zetcode.com/springboot/repository/">Usage of Repository</a>
 * @see <a href="https://attacomsian.com/blog/derived-query-methods-spring-data-jpa">Derived Queries I</a>
 * @see <a href="https://www.baeldung.com/spring-data-derived-queries">Derived Queries II</a>
 * @see <a href="https://thoughts-on-java.org/ultimate-guide-derived-queries-with-spring-data-jpa/">Derived Queries III</a>
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
