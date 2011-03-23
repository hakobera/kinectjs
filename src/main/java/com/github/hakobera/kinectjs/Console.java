package com.github.hakobera.kinectjs;

import java.util.Arrays;

import org.mozilla.javascript.Context;

import net.arnx.jsonic.JSON;

/**
 * CommonJS Console オブジェクトの簡易実装
 * 
 * @author hakobera
 */
public class Console {

	/**
	 * 標準出力にオブジェクトを出力します。
	 * 
	 * @param params 出力したいオブジェクト
	 */
	public void log(Object... params) {
		if (params.length == 0) {
			return;
		}

		Object out = "";
		if (params[0] instanceof String) {
			out = params[0];
			if (params.length > 1) {
				Object[] p = Arrays.copyOfRange(params, 1, params.length);
				out = String.format((String) out, p);
			}
		} else if (ScriptUtil.isPrimitive(params[0])) {
			out = params[0];
		} else {
			out = Context.toString(params[0]) + JSON.encode(params[0], true);
		}
		System.out.println(out);
	}
		
}
