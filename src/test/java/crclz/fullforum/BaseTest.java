package crclz.fullforum;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

//@TestConfiguration
@TestPropertySource("classpath:unittest.properties")
@Rollback
@Transactional
@SpringBootTest
public class BaseTest {
}
