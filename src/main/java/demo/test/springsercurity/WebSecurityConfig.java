package demo.test.springsercurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        // tạo ra user trong bộ nhớ
        // lưu ý, chi sửa dụng cách này để minh họa
        // còn thực tế chúng ta sẽ kiểm tra user trong csdl
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withDefaultPasswordEncoder()// suer dụng mã hóa passord đơn giản
                        .username("somith")
                        .password("1234")
                        .roles("User")
                        .build()
        );
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll()// cho phép tất cả mọi người được truy cập và 2 địa chỉ nhày
                    .anyRequest().authenticated()// tất cả request khác đều phải xác thực mới truy cập được
                    .and()
                .formLogin()// cho phép người dùng xác thực bằng form login
                    .defaultSuccessUrl("/hello")
                    .permitAll() // tất cả đều được truy cập vào địa chỉ này
                    .and()
                .logout()// cho phép logout
                    .permitAll();
    }
}
