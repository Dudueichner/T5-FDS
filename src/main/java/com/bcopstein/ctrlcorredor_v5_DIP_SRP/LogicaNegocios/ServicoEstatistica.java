package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.ConfiguracaoEstatistica.CalculoEstatistica;

@Component
public class ServicoEstatistica {

    private CalculoEstatistica calcEstatistica;

    @Autowired
    // REFATORAR: necessita uma interface específica
    // Esta desrespeitando SRP, afinal IEventoResitory tem o método create que não
    // é necessário para ServicoEstatistica. Além disseo este serviço poderia contar
    // com outras operações como por exemplo buscar apenas os eventos de uma
    // certa distancia
    public ServicoEstatistica(CalculoEstatistica calcEstatistica){
        this.calcEstatistica = calcEstatistica;
    }

    public EstatisticasDTO calculaEstatisticas(int distancia){
        return calcEstatistica.calculaEstatisticas(distancia);
    }

    public PerformanceDTO calculaAumentoPerformance(int distancia,int ano){
        return calcEstatistica.calculaAumentoPerformance(distancia, ano);
    }
}
