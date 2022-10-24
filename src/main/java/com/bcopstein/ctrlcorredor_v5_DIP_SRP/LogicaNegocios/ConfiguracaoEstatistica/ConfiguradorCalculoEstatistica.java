package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.ConfiguracaoEstatistica;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguradorCalculoEstatistica{

    @Bean
    @ConditionalOnProperty(name = "calculo.estatistica", havingValue = "original", matchIfMissing = true)
    public CalculoEstatistica opcaoRegraClassica() {
        return new CalculoOriginal();
    }

    @Bean
    @ConditionalOnProperty(name = "calculo.estatistica", havingValue = "desconsidera")
    public CalculoEstatistica opcaoDesconsidera() {
        return new CalculoDesconsidera();
    }
    
    @Bean
    @ConditionalOnProperty(name = "calculo.estatistica", havingValue = "anoCorrente")
    public CalculoEstatistica opcaoAnoCorrente() {
        return new CalculoAnoCorrente();
    }
}
