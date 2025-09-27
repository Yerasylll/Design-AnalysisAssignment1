package com.company.metricsAndCsv.csvWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvWriter  {
    private final PrintWriter out;
    private boolean headerWritten = false;

    public CsvWriter(String fileName) throws IOException {
        out = new PrintWriter(new FileWriter(fileName));
    }

    public void writeHeader(String... headers) {
        if(!headerWritten) {
            out.println(String.join(",", headers));
            headerWritten = true;
        }
    }

    public void writeRow(Object... values) {
        for(int i = 0; i < values.length; i++) {
            out.print(values[i]);
            if(i < values.length - 1) out.print(",");
        }
        out.println();
    }

    public void close() {
        out.flush();
        out.close();
    }
}
