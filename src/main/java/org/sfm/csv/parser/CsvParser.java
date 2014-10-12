package org.sfm.csv.parser;

import java.io.IOException;
import java.io.Reader;

import org.sfm.utils.RowHandler;

public final class CsvParser {
	public static int _4K = 1 << 12;
	public static int _8K = 1 << 13;
	public static int _16K = 1 << 14;
	public static int _32K = 1 << 15;
	public static int _64K = 1 << 16;
	
	private final int bufferSize;
	
	public CsvParser(final int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public CsvParser() {
		this(_8K);
	}
		
	public <CH extends CharsCellHandler> CH parse(final Reader r, final CH handler) throws IOException {
		new ReaderCsvParser(bufferSize).parse(r, handler);
		return handler;
	}
	
	public <RH extends RowHandler<String[]>> RH readRows(final Reader r, final RH handler) throws IOException {
		new ReaderCsvParser(bufferSize).parse(r, new StringArrayHandler(handler));
		return handler;
	}
}
