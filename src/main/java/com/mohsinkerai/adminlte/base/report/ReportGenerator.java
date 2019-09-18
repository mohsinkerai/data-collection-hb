package com.mohsinkerai.adminlte.base.report;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class ReportGenerator {

  public byte[] generatePdf(List<AbstractReportPart> reportPartList) throws JRException {
    List<JasperPrint> jasperPrints = reportPartList
      .stream()
      .map(AbstractReportPart::getJasperPrint)
      .map(ReportPrint::getJasperPrint)
      .collect(Collectors.toList());

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    JRPdfExporter exporter = new JRPdfExporter();
    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints)); //Set as export input my list with JasperPrint s
    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out)); //or any other out streaam
    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
    configuration.setCreatingBatchModeBookmarks(true); //add this so your bookmarks work, you may set other parameters
    exporter.setConfiguration(configuration);
    exporter.exportReport();

    return out.toByteArray();
  }
}
