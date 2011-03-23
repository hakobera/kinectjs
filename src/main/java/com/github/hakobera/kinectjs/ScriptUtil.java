package com.github.hakobera.kinectjs;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class ScriptUtil {
	
	/**
	 * スクリプトファイルを指定したコンテキスト、指定したスコープ内で評価します。
	 * 
	 * @param cx　コンテキスト
	 * @param scope 評価スコープ
	 * @param path スクリプトファイルへのパス
	 * @return スクリプトの評価結果
	 * @throws Exception エラーが発生した場合
	 */
	public static Object evalScript(Context cx, Scriptable scope, String path) throws Exception {
		File f = new File(path);
		if (!f.exists()) {
			f = new File("js/lib/" + path);
		}
		Reader script = new FileReader(f);
		Object ret = cx.evaluateReader(scope, script, path, 1, null);
		return ret;
	}

	/**
	 * オブジェクトがプリミティブ型かどうかを判定して返します。
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isPrimitive(Object o) {
		return 
			o instanceof Integer
		||	o instanceof Character
		||	o instanceof Short
		||	o instanceof Long
		||	o instanceof Float
		||	o instanceof Double
		||	o instanceof BigDecimal
		||	o instanceof BigInteger;
	}
	
	
	
}
