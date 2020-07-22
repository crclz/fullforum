package crclz.fullforum;

import crclz.fullforum.data.repos.UserRepository;
import crclz.fullforum.services.Auth;
import crclz.fullforum.services.IAuth;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AppConfig {

    @Bean
    @RequestScope
    public IAuth IAuth(HttpServletRequest request, HttpServletResponse response, UserRepository userRepository) {
        return new Auth(request, response, userRepository);
    }

}
