package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.ConfiguracaoEstatistica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.IEventoRepository;

@Configuration
public class ConfiguradorCalculoEstatistica{

    private IEventoRepository eventoRep;

    @Autowired
    public ConfiguradorCalculoEstatistica(IEventoRepository eventoRep){
        this.eventoRep = eventoRep;
    }

    @Bean
    @ConditionalOnProperty(name = "calculo.estatistica", havingValue = "original", matchIfMissing = true)
    public CalculoEstatistica opcaoRegraClassica() {
        return new CalculoOriginal(eventoRep);
    }

    @Bean
    @ConditionalOnProperty(name = "calculo.estatistica", havingValue = "desconsidera")
    public CalculoEstatistica opcaoDesconsidera() {
        return new CalculoDesconsidera(eventoRep);
    }
    
    @Bean
    @ConditionalOnProperty(name = "calculo.estatistica", havingValue = "anoCorrente")
    public CalculoEstatistica opcaoAnoCorrente() {
        return new CalculoAnoCorrente(eventoRep);
    }
}
