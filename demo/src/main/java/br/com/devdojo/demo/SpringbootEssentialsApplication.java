package br.com.devdojo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
*	Uma notação do spring que diz para o spring que terá uma idéia
* 	do que terá que configurar para nós dependendo das dependências
* 	que adicionamos.
*/
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "br.com.devdojo.endpoint")
/*Fará a configuração nas classes que definirmos de configuração*/
//@Configuration
/*Essa notação é que substitui todas as 3 de cima*/
@SpringBootApplication
public class SpringbootEssentialsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEssentialsApplication.class, args);
	}

}
