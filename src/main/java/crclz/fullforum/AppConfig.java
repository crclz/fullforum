package crclz.fullforum;

import crclz.fullforum.data.repos.UserRepository;
import crclz.fullforum.services.Auth;
import crclz.fullforum.services.IAuth;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

    @Bean
    public IAuth IAuth(HttpServletRequest request, UserRepository userRepository) {
        return new Auth(request, userRepository);
    }

}
