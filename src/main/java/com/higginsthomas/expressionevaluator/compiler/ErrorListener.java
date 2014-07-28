package com.higginsthomas.expressionevaluator.compiler;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import com.higginsthomas.expressionevaluator.errors.CompileException;


public class ErrorListener extends BaseErrorListener {
    public void syntaxError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol, int line,
                                    int charPositionInLine, String msg,
                                    RecognitionException e) {
        throw new CompileException(msg, charPositionInLine);
    }
}
