package br.checker.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuracao de CORS (Cross-Origin Resource Sharing).
 *
 * <p>Libera requisicoes HTTP originadas do frontend Angular rodando em http://localhost:4200.
 * Sem essa configuracao, o navegador bloquearia requisicoes do frontend para o backend
 * por politica de mesma origem (Same-Origin Policy).
 *
 * <p>O filtro CORS intercepta requisicoes OPTIONS (preflight) e adiciona os cabeçalhos
 * Access-Control-Allow-*, permitindo que o navegador execute requisicoes reais.
 */
@Configuration
public class CorsConfig {

    /**
     * Cria um filtro CORS que permite requisicoes de qualquer origem, metodo e cabecalho.
     * Em ambiente de producao, recomenda-se restringir allowedOrigins ao dominio do frontend.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // permite qualquer origem (ajustar em producao)
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}