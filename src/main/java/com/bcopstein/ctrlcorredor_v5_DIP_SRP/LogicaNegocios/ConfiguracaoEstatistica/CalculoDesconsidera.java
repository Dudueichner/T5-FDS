package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.ConfiguracaoEstatistica;

import java.util.List;
import java.util.stream.Collectors;

import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.EstatisticasDTO;
import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.Evento;
import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.IEventoRepository;
import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.PerformanceDTO;

public class CalculoDesconsidera implements CalculoEstatistica {

    private IEventoRepository eventoRep;

    public CalculoDesconsidera(IEventoRepository eventoRep){
        this.eventoRep = eventoRep;
    }

    @Override
    public EstatisticasDTO calculaEstatisticas(int distancia) {
            // Seleciona os eventos da distancia informada
            List<Evento> eventos = 
            eventoRep.todos()
                .stream()
                .filter(e->e.getDistancia() == distancia)
                .collect(Collectors.toList());
            // Obtém um stream com os valores ordenados

            //tira o menor e o maior
            if(eventos.size() >= 3){
                int maiorDistancia = Integer.MIN_VALUE;
                int menorDistancia = Integer.MAX_VALUE;
                int indexMaior = 0;
                int indexMenor = 0;
    
                for(int i = 0; i < eventos.size(); i++){
                    int dist = eventos.get(i).getDistancia();
                    if(maiorDistancia < dist){
                        maiorDistancia = dist;
                        indexMaior = i;
                    }
                    if(menorDistancia > dist){
                        menorDistancia = dist;
                        indexMenor = i;
                    }
                }
                eventos.remove(indexMenor);
                eventos.remove(indexMaior);
            }
            
            List<Double> valores = eventos
                .stream()
                .map(e-> e.getHoras()*60*60 + e.getMinutos()*60.0 + e.getSegundos())
                .sorted()
                .collect(Collectors.toList());
            // Calcula a média
            double media = valores
                .stream()
                .mapToDouble(v->v)
                .average()
                .orElse(Double.NaN);
            // Calcula mediana
            Double mediana = Double.NaN;
            if (valores.size() > 0){
                mediana =
                    ((valores.size() % 2 == 0) ?
                    (valores.get(valores.size()/2 - 1))+(valores.get(valores.size()/2))/2.0 :
                    (valores.get(valores.size()/2)));
            }
            // Calcula o desvio padrao
            double varianca;
            double desvioPadrao = Double.NaN;
            if (mediana != Double.NaN){
                varianca = valores
                    .stream()
                    .mapToDouble(v -> v - media)
                    .map(v -> v*v)
                    .average().getAsDouble();
                desvioPadrao = Math.sqrt(varianca);
            }
            return new EstatisticasDTO(media, mediana, desvioPadrao);
    }

    @Override
    public PerformanceDTO calculaAumentoPerformance(int distancia, int ano) {
        //ESTA ERRADO
        List<Evento> eventos = eventoRep
                        .todos()
                        .stream()
                        .filter(e->e.getAno() == ano)
                        .collect(Collectors.toList());

        
        if(eventos.size() >= 3){
            int maiorDistancia = Integer.MIN_VALUE;
            int menorDistancia = Integer.MAX_VALUE;
            int indexMaior = 0;
            int indexMenor = 0;

            for(int i = 0; i < eventos.size(); i++){
                int dist = eventos.get(i).getDistancia();
                if(maiorDistancia < dist){
                    maiorDistancia = dist;
                    indexMaior = i;
                }
                if(menorDistancia > dist){
                    menorDistancia = dist;
                    indexMenor = i;
                }
            }
            eventos.remove(indexMenor);
            eventos.remove(indexMaior);
        }
        
        
        int indiceMaiorDif = 0;
        double maiorDif = -1.0;
        for(int i=0;i<eventos.size()-1;i++){
            Evento e1 = eventos.get(i);
            Evento e2 = eventos.get(i+1);
            double tempo1  = e1.getHoras()*60*60 + e1.getMinutos()*60.0 + e1.getSegundos();
            double tempo2  = e2.getHoras()*60*60 + e2.getMinutos()*60.0 + e2.getSegundos();
            if ((tempo1-tempo2)>maiorDif){
                maiorDif = tempo1-tempo2;
                indiceMaiorDif = i;
            }
        }         
        return new PerformanceDTO(eventos.get(indiceMaiorDif).getNome(),
                                  eventos.get(indiceMaiorDif+1).getNome(),
                                  maiorDif);
    }
    
}
