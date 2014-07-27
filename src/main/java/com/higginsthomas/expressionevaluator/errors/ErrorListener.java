package com.higginsthomas.expressionevaluator.errors;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;


public class ErrorListener extends BaseErrorListener {
    public void syntaxError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol, int line,
                                    int charPositionInLine, String msg,
                                    RecognitionException e) {
        throw new RuntimeException(
                String.format("%s at %d (%s)",
                              msg,
                              charPositionInLine + 1,
                              offendingSymbol));
    }
}
