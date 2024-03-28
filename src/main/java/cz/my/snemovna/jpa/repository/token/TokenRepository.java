package cz.my.snemovna.jpa.repository.token;

import cz.my.snemovna.jpa.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The access tokens repository.
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

    // TODO: Change query to SQL builder.
    @Query(value = """
      SELECT t FROM sn_tokens t INNER JOIN sn_users u\s
      ON t.user_id = u.id\s
      WHERE u.id = :id AND (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer id);
}
