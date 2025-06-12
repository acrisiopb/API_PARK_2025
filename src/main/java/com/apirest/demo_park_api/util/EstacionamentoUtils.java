package com.apirest.demo_park_api.util;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access =  AccessLevel.PRIVATE)
public class EstacionamentoUtils {
      //2025-06-09T11:02:46.548052
      //20250609-110246
    public static String gerarRecibo(){
        LocalDateTime date = LocalDateTime.now();
        String recibo = date.toString().substring(0,19);
        return recibo.replace("-", "")
        .replace(":", "")
        .replace("T", "-");
    }
}
