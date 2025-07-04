package com.apirest.demo_park_api.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Slf4j
@RequiredArgsConstructor
@Service
public class JasperService {

    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    private Map<String, Object> params = new HashMap<>();

    private static final String JASPER_DIRETORIO = "classpath:reports/";

    public void addParams(String key, Object value) {
        this.params.put("IMAGEM_DIRETORIO", JASPER_DIRETORIO);
        this.params.put("REPORT_LOCALE", new Locale("pt", "BR"));
        this.params.put(key, value);
    }

    public byte[] geraPDF() {
        byte[] bytes = null;
        try {
            Resource resource = resourceLoader.getResource(JASPER_DIRETORIO.concat("Estacionamento.jasper"));
            InputStream stream = resource.getInputStream();
            JasperPrint print = JasperFillManager.fillReport(stream, params, dataSource.getConnection());
            bytes = JasperExportManager.exportReportToPdf(print);
        }
      
        catch (IOException |  JRException | SQLException e) {
            log.error("Jasper Reports ::: ", e.getCause());
            throw new RuntimeException(e);
        }
       
        return bytes;
    }
}
