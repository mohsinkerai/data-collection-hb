package com.mohsinkerai.adminlte.base.report;

import com.google.common.collect.Maps;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 * Each Report can have one or many part
 */
public abstract class AbstractReportPart {

  private final JasperReport jasperReport;

  public AbstractReportPart(String reportName) throws JRException {
    InputStream employeeReportStream = getClass()
      .getResourceAsStream(reportName);
    this.jasperReport = JasperCompileManager.compileReport(employeeReportStream);
  }

  /**
   * Knows how to fetch data for report and return it.
   */
  public abstract Collection<?> getDataForReport();

  public ReportPrint getJasperPrint() {
    try {
      Collection<?> dataForReport = getDataForReport();
      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dataForReport);
      return new ReportPrint(JasperFillManager.fillReport(jasperReport, Maps.newHashMap(), ds));
    } catch (JRException ex) {
      throw new RuntimeException(ex);
    }
  }
}
