package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.ConfiguracaoEstatistica;

import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.EstatisticasDTO;
import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.PerformanceDTO;

public interface CalculoEstatistica {
    
    EstatisticasDTO calculaEstatisticas(int distancia);
    PerformanceDTO calculaAumentoPerformance(int distancia, int ano);
}
