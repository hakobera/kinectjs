package com.github.hakobera.kinectjs;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptableObject;

import processing.core.PApplet;

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
			NativeJavaObject javaObj = (NativeJavaObject) ScriptUtil.evalScript(cx, globalScope, mainScriptPath);
			final PApplet applet = (PApplet) javaObj.unwrap();
			JFrame frame = new ProcessingWrapper(applet);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent event) {
					applet.stop();
					System.exit(0);
				}
			});
			frame.setVisible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Context.exit();
		}
	}
	
}
