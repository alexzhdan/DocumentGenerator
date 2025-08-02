package org.documentGenerator.service;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

@Component
public class WordTemplateProcessor {
    public byte[] generateDocument(byte[] templateContent, Map<String, String> values) throws Exception {
        try (InputStream inputStream = new ByteArrayInputStream(templateContent);
             XWPFDocument doc = new XWPFDocument(inputStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                replaceTextInParagraph(paragraph, values);
            }

            for (XWPFTable table : doc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceTextInParagraph(paragraph, values);
                        }
                    }
                }
            }

            doc.write(outputStream);
            return outputStream.toByteArray();
        }
    }


    private void replaceTextInParagraph(XWPFParagraph paragraph, Map<String, String> values) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) {
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    text = text.replace("${" + entry.getKey() + "}", entry.getValue());
                }
                run.setText(text, 0);
            }
        }
    }
}
