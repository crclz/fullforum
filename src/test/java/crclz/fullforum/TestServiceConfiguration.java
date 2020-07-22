package crclz.fullforum;

import crclz.fullforum.dependency.FakeAuth;
import crclz.fullforum.services.IAuth;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestServiceConfiguration {

    @Bean
    @Primary// Configuration一直是生效的，本类只是“重写”了某些东西，所以primary
    public FakeAuth constructFakeAuth() {
        return new FakeAuth();
    }
}
