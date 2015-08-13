package com.asseco.aha.poc.lambdas.streams;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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

	public Stream<String> readZip(String zipName, String fileName) {
		LOG.debug("Reading zip '{}/{}' ...", zipName, fileName);

		List<String> data = new ArrayList<>();
		try (ZipFile zipFile = new ZipFile("target/classes/" + zipName)) {
			ZipEntry zipEntry = zipFile.stream().filter(f -> fileName.equals(f.getName())).findFirst().get();
			LOG.debug("Name={}/{}, size={}", zipName, zipEntry.getName(), zipEntry.getSize());
			// read zip entry content as line stream
			InputStream is = zipFile.getInputStream(zipEntry);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				data.add(line);
			}
			LOG.info("No. of lines={}", data.size());
			return data.stream();
		} catch (IOException e) {
			LOG.error(String.format("Error reading file Name=%s/%s", zipName, fileName), e);
			return Stream.empty();
		}
	}

	@Override
	public void close() throws IOException {
		lines.close();
	}

}
