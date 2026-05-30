package com.enterprise.automation.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Path;

public class TestDataFactory {
    private final ObjectMapper json = new ObjectMapper();
    private final ObjectMapper yaml = new ObjectMapper(new YAMLFactory());
    private final CsvMapper csv = new CsvMapper();

    public <T> T json(Path path, Class<T> type) throws IOException {
        return json.readValue(path.toFile(), type);
    }

    public <T> T yaml(Path path, Class<T> type) throws IOException {
        return yaml.readValue(path.toFile(), type);
    }

    public CsvMapper csv() {
        return csv;
    }
}
