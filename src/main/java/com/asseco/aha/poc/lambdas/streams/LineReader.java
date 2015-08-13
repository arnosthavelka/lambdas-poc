package com.asseco.aha.poc.lambdas.streams;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LineReader implements Closeable {

	private static final Logger LOG = LoggerFactory.getLogger(LineReader.class);

	private Stream<String> lines;

	public Stream<String> read(String fileName) {
		LOG.debug("Reading file '{}' ...", fileName);
		// loading file as stream
		Stream<String> lines;
		try {
			lines = Files.lines(Paths.get("target", "classes", fileName));
		} catch (IOException e) {
			throw new RuntimeException(String.format("File %s is not readable!", fileName), e);
		}
		return lines;
	}

	@Override
	public void close() throws IOException {
		lines.close();
	}

}
