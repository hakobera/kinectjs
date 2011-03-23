package com.github.hakobera.kinectjs;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ScriptableObject;

/**
 * openkinect.js の Javascript 簡易ラッパー
 * 
 * @author hakobera
 */
public class KinectJS {
	
	public static void main(String args[]) throws Exception {
		String path = "js/app.js";
		if (args.length == 1) {
			path = args[0];
		}
		new KinectJS().run(path);
	}
	
	private Context cx;
	
	private ScriptableObject globalScope;
	
	public KinectJS() {
		init();
	}
	
	public void init() {
		ContextFactory contextFactory = new ContextFactory();
		cx = contextFactory.enterContext();
		globalScope = cx.initStandardObjects();
		defineGlobalFunctions();
	}
	
	private void defineGlobalFunctions() {
		String[] embededFuncNames = { "print", "require" };
		globalScope.defineFunctionProperties(embededFuncNames, EmbededFunctions.class, ScriptableObject.DONTENUM);
		
		globalScope.defineProperty("console", new Console(), ScriptableObject.DONTENUM);
	}
	
	public void run(String mainScriptPath) {
		try {
			ScriptUtil.evalScript(cx, globalScope, mainScriptPath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Context.exit();
		}
	}
	
}
