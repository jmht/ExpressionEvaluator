package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.executer.operations.*;


public class IntermediateCompiler_SetTests extends IntermediateCompilerTestBase {
    
    @Test
    public void testRelation_InCollection_Set() {
        ParseTree tree = parser("x in {1, 2, 3}").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();

//        assertThat(result.getOperationAt(-1), instanceOf(IsIn.class));
        assertThat(result.getOperationAt(-1), instanceOf(LoadIdentifier.class));
    }
}