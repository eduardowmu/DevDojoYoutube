package br.edu.devdojo2.app.config;

import br.edu.devdojo2.app.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
/*Com relação aos testes, os testes de integração não funcionam, ou pelo menos
* a grande maioria não passam pois justamente por conta das proteções de URLs
* que impomos em nossa aplicação*/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//que por padrão é falso, isso habilita o pre authorize do metodo POST da controller
//@RequiredArgsConstructor//notação para não ter a necessidade de contrutor
@SuppressWarnings("java:S5344")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthUserService authUserService;

    @Autowired
    public SecurityConfig(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*Quando recebermos uma requisição http, queremos que todas elas sejam
        * passadas pela autorização*/
        http    /*criação de token CSRF. Este é um cookie do tipo não httpOnly, pois
                as aplicações de front, seja em angular, react, vue não conseguirão
                receber este cookie. O melhor é desabilitar para testes locais, porém
                para produção, sempre habilitar*/
            .csrf().disable()//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            .authorizeHttpRequests()
            /*Aqui queremos proteger nossas URLs, por padrão, supondo que adotemos
            * que todas as requisições tenham "/admin", podemos proteger através de
            * antMatchers(...). A primeira é para ADMIN*/
            .antMatchers("/anime/admin/**").hasRole("ADMIN")
            /*e a seguir ára para USER. Devemos nos atentar que esta sequencia
            * é levado em consideração. O recomendado é sempre colocar o mais
            * restritivo e privilegiado venha a ser primeiro*/
            .antMatchers("/anime/**").hasRole("USER")
            .anyRequest()
            /*E queremos que todas estejam autenticadas*/
            .authenticated().and()
            /*Através disso, podemos fazer autenticação através do browser por um formulário*/
            .formLogin()
            .and()
            /*e com configurações basicas do http*/
            .httpBasic();
    }

    /*Método em que criaremos um usuário em memória, onde também definiremos uma
    * criptografia para sua senha.*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //criptografando senhas
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(passwordEncoder.encode("teste"));
        /*Criação de um auth in memory authentication*/
        auth.inMemoryAuthentication()
                .withUser("edu")
                .password(passwordEncoder.encode("teste"))
                /*método que irá definir outras regras de autenticação*/
                .roles("USER","ADMIN")
                /*Regras apenas para usuário dudu*/
                .and().withUser("dudu")
                .password(passwordEncoder.encode("teste2"))
                .roles("USER");

        /*Este serviço é o padrão na parte de segurança do spring*/
        auth.userDetailsService(authUserService).passwordEncoder(passwordEncoder);
    }
}
