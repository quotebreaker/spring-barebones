package repositories;

import org.springframework.stereotype.Repository;

import pojo.AuthUser;

@Repository
public interface AuthUserRepository extends BaseRepository<AuthUser, Long> {
	public AuthUser findByEmail(String email);
}
