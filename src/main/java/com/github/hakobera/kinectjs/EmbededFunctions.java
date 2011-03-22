package com.github.hakobera.kinectjs;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Embeded global functions for KinectJS shell.
 * 
 * @author hakobera
 */
public final class EmbededFunctions {

	private EmbededFunctions() {
	}
	
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
		Reader script = new FileReader(new File(path)); 
		Object ret = cx.evaluateReader(scope, script, path, 1, null);
		return ret;
	}

	/**
	 * コンソールにオブジェクトを文字列として出力します。
	 * 
	 * @param cx
	 * @param thisObj
	 * @param args
	 * @param funObj
	 * @return
	 */
	public static Object print(Context cx, Scriptable thisObj, Object[] args, Function funObj) {
		for (int i = 0; i < args.length; i++) {
			if (i > 0) {
				System.out.print(" ");
			}
			System.out.print(Context.toString(args[i]));
		}
		System.out.println();
		return Context.getUndefinedValue();
	}
	
	/**
	 * CommonJS <a href="http://wiki.commonjs.org/wiki/Modules/1.1.1">Modules/1.1.1</a> の
	 * require 関数の簡易実装。
	 * モジュールを読み込みます。
	 * 
	 * @param moduleId モジュール ID
	 * @return モジュール ID に対応するモジュール
	 */
	public static Object require(Context cx, Scriptable thisObj, Object[] args, Function funObj) {
		// モジュールスコープを作成
		Scriptable moduleScope = cx.newObject(thisObj);
		moduleScope.setPrototype(thisObj);
		
		Scriptable exports = cx.newObject(moduleScope);
		ScriptableObject.putProperty(moduleScope, "exports", exports);
		
		String moduleId = (String) args[0];
		String path = moduleId.concat(".js");
		try {
			evalScript(cx, moduleScope, path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Object module = ScriptableObject.getProperty(moduleScope, "exports");
		return module;
	}

}
