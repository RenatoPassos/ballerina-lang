/*
 *  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.ballerinalang.test.imports;

import org.ballerinalang.test.BCompileUtil;
import org.ballerinalang.test.BRunUtil;
import org.ballerinalang.test.CompileResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.ballerinalang.test.BAssertUtil.validateError;
import static org.testng.Assert.assertEquals;

/**
 * Test cases for imports.
 */
public class ImportsTest {

    @Test(description = "Test self import")
    public void testSelfImport() {
        CompileResult result = BCompileUtil.compile("test-src/imports/SelfImportTestProject");
        assertEquals(result.getErrorCount(), 1);
        validateError(result, 0, "cyclic module imports detected " +
                        "'testorg/selfimport.foo:1.0.0 -> testorg/selfimport.foo:1.0.0'", 2, 1);
    }

    // https://github.com/ballerina-platform/ballerina-lang/issues/27371
    @Test(description = "Test cyclic imports", enabled = false)
    public void testCyclicImports() {
        CompileResult result = BCompileUtil.compile("test-src/imports/cyclic-imports");
        assertEquals(result.getErrorCount(), 3);
        validateError(result, 0, "cyclic module imports detected " +
                                 "'cyclic_imports/def:1.0.0 -> cyclic_imports/ghi:1.0.0 -> cyclic_imports/def:1.0.0'",
                2, 1);
        validateError(result, 1, "cyclic module imports detected " +
                                 "'cyclic_imports/abc:1.0.0 -> cyclic_imports/def:1.0.0 -> " +
                                 "cyclic_imports/ghi:1.0.0 -> cyclic_imports/jkl:1.0.0 -> cyclic_imports/abc:1.0.0'",
                2, 1);
        validateError(result, 2, "cyclic module imports detected 'cyclic_imports/abc:1.0.0 -> " +
                                 "cyclic_imports/def:1.0.0 -> cyclic_imports/ghi:1.0.0 -> cyclic_imports/abc:1.0.0'",
                3, 1);
    }

    @Test(description = "Test importing same module name but with different org names")
    public void testSameModuleNameDifferentOrgImports() {
        CompileResult result = BCompileUtil.compile("test-src/imports/lang.float");
        Object returns = BRunUtil.invoke(result, "getStringValueOfPI");
        Assert.assertTrue(returns.toString().startsWith("3.14"));
    }

    @Test(description = "Test auto imports")
    public void testPredeclaredModules() {
        CompileResult result = BCompileUtil.compile("test-src/imports/PredeclaredImportsTestProject");
        BRunUtil.invoke(result, "testPredeclaredModules");
    }

    @Test(description = "Test overridden predeclared modules")
    public void testOverriddenPredeclaredModules() {
        CompileResult result = BCompileUtil.compile("test-src/imports/OverriddenPredeclaredImportsTestProject");
        BRunUtil.invoke(result, "testOverriddenPredeclaredModules");
    }

    @Test(description = "Test overridden predeclared modules using keywords")
    public void testOverriddenPredeclaredModulesUsingKeywords() {
        CompileResult result = BCompileUtil.compile("test-src/imports/PredeclaredImportsTestProject");
        BRunUtil.invoke(result, "testPredeclaredModules2");
    }
}
