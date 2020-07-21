package crclz.fullforum;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

//@TestConfiguration
@TestPropertySource("classpath:unittest.properties")
@Rollback
@Transactional
public class BaseTest {
}
