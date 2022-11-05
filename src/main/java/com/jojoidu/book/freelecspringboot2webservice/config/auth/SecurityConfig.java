package com.jojoidu.book.freelecspringboot2webservice.config.auth;

import com.jojoidu.book.freelecspringboot2webservice.web.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // spring security 설정은 대부분 httpSecurity 에서 설정
        http
                .csrf().disable()
                .headers().frameOptions().disable()// h2 - console 화면 사용을  위한 옵션들을 disable
                .and()
                .authorizeRequests()//url 별 권환 관리 // andMatchers : 권환 관리 대상 지정
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() // 설정된 값들 이외 나머지 url들을 말함
                                            // authenticated 인증된 사용자들에게 허용
                .and()
                .logout() // 로그아웃 기능에 대한 설정 진입점
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login() // 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //사용자 정보를 가져올 때의 설정
                .userService(customOAuth2UserService);// 로그인 성공시 후속 조치를 진행할 userService 인터페이스 구현체 등록
    }
}
