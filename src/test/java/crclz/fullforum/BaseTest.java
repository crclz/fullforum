package crclz.fullforum;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

@TestPropertySource("classpath:unittest.properties")
@Rollback
@Transactional
@SpringBootTest
//@Import(TestServiceConfiguration.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class BaseTest {
}
