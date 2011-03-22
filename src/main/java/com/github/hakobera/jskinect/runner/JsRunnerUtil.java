package com.github.hakobera.jskinect.runner;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public final class JsRunnerUtil {
	
	private JsRunnerUtil() {
	}
	
	/**
	 * 指定したリソースファイルを読み込み、それに対応する {@link InputStream} を返します。
	 * 
	 * @param resource 読み込み対象ファイルへのパス
	 * @return 引数で指定したリソースファイルに対応する {@link InputStream}
	 * @throws IOException ファイルが読み込めなかった場合に発生します。
	 */
	public static InputStream getResourceAsStream(String resource) throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream in = null;
		if (loader != null) {
			in = loader.getResourceAsStream(resource);
		}
		if (in == null) {
			in = ClassLoader.getSystemResourceAsStream(resource);
		}
		if (in == null) {
			throw new IOException(String.format("Could not find resource '%s'", resource));
		}
		return in;
	}
	
	/**
	 * 例外のスタックトレースを System.err に出力します。
	 * 
	 * @param t 例外
	 */
	public static void printStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String out = sw.toString();
		System.err.print(out);
		System.err.println();
	}
	
}
