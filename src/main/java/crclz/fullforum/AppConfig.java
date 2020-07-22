package crclz.fullforum;

import crclz.fullforum.services.Auth;
import crclz.fullforum.services.IAuth;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public IAuth IAuth() {
        return new Auth();
    }

}
