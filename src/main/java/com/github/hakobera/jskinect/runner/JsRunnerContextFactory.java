package com.github.hakobera.jskinect.runner;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

public class JsRunnerContextFactory extends ContextFactory {
	
	@Override
	protected boolean hasFeature(Context context, int featureIndex) {
		switch (featureIndex) {
		case Context.FEATURE_STRICT_MODE:
		case Context.FEATURE_STRICT_VARS:
		case Context.FEATURE_STRICT_EVAL:
		case Context.FEATURE_WARNING_AS_ERROR:
			return true;
		}
		return super.hasFeature(context, featureIndex);
	}
	
}
