package com.apirest.demo_park_api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {
    private static final double PRIMEIROS_15_MINUTES = 5.00;
    private static final double PRIMEIROS_60_MINUTES = 9.25;
    private static final double ADICIONAL_15_MINUTES = 1.75;
    private static final double DESCONTO_PERCENTUAL = 0.30;


    public static BigDecimal calcularCusto(LocalDateTime entrada, LocalDateTime saida) {
        long minutes = entrada.until(saida, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {
            total = PRIMEIROS_15_MINUTES;
        } else if (minutes <= 60) {
            total = PRIMEIROS_60_MINUTES;
        } else {
            long addicionalMinutes = minutes - 60;
            Double totalParts = ((double) addicionalMinutes / 15);
            if (totalParts > totalParts.intValue()) { // 4.66 > 4
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * (totalParts.intValue() + 1));
            } else { // 4.0
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * totalParts.intValue());
            }
        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

       public static BigDecimal calcularDesconto(BigDecimal custo, long numeroDeVezes) {
        BigDecimal desconto = ((numeroDeVezes > 0) && (numeroDeVezes % 10 == 0))
                ? custo.multiply(new BigDecimal(DESCONTO_PERCENTUAL))
                : new BigDecimal(0);
        return desconto.setScale(2, RoundingMode.HALF_EVEN);
    }



    
    // 2023-03-16T15:23:48.616463500
    // 20230316-152121
    public static String gerarRecibo() {
        LocalDateTime date = LocalDateTime.now();
        String recibo = date.toString().substring(0, 19);
        return recibo.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }
}
