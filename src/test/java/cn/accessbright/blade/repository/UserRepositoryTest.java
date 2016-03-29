package cn.accessbright.blade.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cn.accessbright.blade.SpringBootTest;
import cn.accessbright.blade.domain.system.User;

public class UserRepositoryTest extends SpringBootTest {

	@Autowired
	UserRepository repository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testSave() {
		User user = new User();
		user.setUsername("liumang");
		user.setPassword("123456");
		repository.save(user);
	}

}
