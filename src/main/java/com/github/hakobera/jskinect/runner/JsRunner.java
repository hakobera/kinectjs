package com.github.hakobera.jskinect.runner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.security.auth.callback.LanguageCallback;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.WrappedException;

/**
 * 指定した Javascript ファイルを実行します。
 */
public class JsRunner extends ScriptableObject {

	private static final long serialVersionUID = -6365232972256143336L;

	private Scriptable topLevelScope;
	
	private Context globalContext;
	
	public static void main(String[] args) {
		new JsRunner().run(args);
	}

	@Override
	public String getClassName() {
		return "global";
	}
	
	public void run(String[] args) {
		ContextFactory contextFactory = new JsRunnerContextFactory();
		globalContext = contextFactory.enterContext();
		
		try {
			// globalContext.setLanguageVersion(170);
			String[] names = { "print", "load" };
			defineFunctionProperties(names, JsRunner.class, ScriptableObject.DONTENUM);
			
			String app = "app.js";
			if (args.length > 0) {
				app = args[0];
			}
			
			Object[] array;
			if (args.length == 0) {
				array = new Object[0];
			} else {
				int length = args.length - 1;
				array = new Object[length];
				System.arraycopy(args, 1, array, 0, length);
			}
			Scriptable argsObj = globalContext.newArray(this, array);
			defineProperty("arguments", argsObj, ScriptableObject.DONTENUM);
			
			topLevelScope = new ImporterTopLevel(globalContext);
			topLevelScope.put("intf", topLevelScope, this);
			
			processScript(globalContext, app);
		} finally {
			Context.exit();
		}
	}
	
	/**
	 * 引数の内容をコンソールに出力します。
	 * 
	 * @param context
	 * @param thisObj
	 * @param args
	 * @param func
	 */
	public static void print(Context context, Scriptable thisObj, Object[] args, Function func) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < args.length; ++i) {
			if (i > 0) {
				buffer.append(" ");
			}
			buffer.append(Context.toString(args[i]));
		}
		System.out.print(buffer.toString());
		System.out.println();
	}
	
	/**
	 * 引数 args で指定したスクリプトファイルを読み込んで実行します。
	 * 
	 * @param context
	 * @param thisObj
	 * @param args
	 * @param func
	 */
	public static void load(Context context, Scriptable thisObj, Object[] args, Function func) {
		JsRunner runner = (JsRunner) getTopLevelScope(thisObj);
		for (int i = 0; i < args.length; ++i) {
			runner.processScript(context, Context.toString(args[i]));
		}
	}
	
	/**
	 * スクリプトファイルを実行します。
	 * 
	 * @param context コンテキスト
	 * @param filename ファイル名
	 */
	protected void processScript(Context context, String filename) {
		Reader in = null;
		try {
			in = new InputStreamReader(JsRunnerUtil.getResourceAsStream(filename));
		} catch (IOException e) {
			Context.reportError(String.format("Couldn't open resource: '%s'.", filename));
			return;
		}
		
		try {
			context.evaluateReader(topLevelScope, in, filename, 1, null);
		} catch (WrappedException we) {
			JsRunnerUtil.printStackTrace(we.getWrappedException());
		} catch (Exception e) {
			JsRunnerUtil.printStackTrace(e);
		} finally {
			try {
				in.close();
			} catch (IOException ioe) {
				JsRunnerUtil.printStackTrace(ioe);
			}
		}
		
	}
	
}
