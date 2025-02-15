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
package org.ballerinalang.langlib.test.statements.foreach;

import org.ballerinalang.test.BCompileUtil;
import org.ballerinalang.test.BRunUtil;
import org.ballerinalang.test.CompileResult;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for typed binding patterns in foreach.
 *
 * @since 0.985.0
 */
public class ForeachRecordTypedBindingPatternsTests {

    private CompileResult program;

    @BeforeClass
    public void setup() {
        program = BCompileUtil.compile("test-src/statements/foreach/foreach-record-typed-binding-patterns.bal");
    }

    @AfterClass
    public void tearDown() {
        program = null;
    }

    @Test
    public void testSimpleRecordWithoutType() {
        Object returns = BRunUtil.invoke(program, "testSimpleRecordWithoutType");
        Assert.assertEquals(returns.toString(), "0:1 1:A 2:1.0 ");
    }

    @Test
    public void testSimpleRecordWithType() {
        Object returns = BRunUtil.invoke(program, "testSimpleRecordWithType");
        Assert.assertEquals(returns.toString(), "0:1 1:A 2:1.0 ");
    }

    @Test
    public void testRecordInRecordWithoutType() {
        Object returns = BRunUtil.invoke(program, "testRecordInRecordWithoutType");
        Assert.assertEquals(returns.toString(), "0:2 1:B 2:{\"i\":1,\"s\":\"A\",\"f\":1.0} ");
    }

    @Test
    public void testRecordInRecordWithType() {
        Object returns = BRunUtil.invoke(program, "testRecordInRecordWithType");
        Assert.assertEquals(returns.toString(), "0:2 1:B 2:{\"i\":1,\"s\":\"A\",\"f\":1.0} ");
    }

    @Test
    public void testTupleInRecordWithoutType() {
        Object returns = BRunUtil.invoke(program, "testTupleInRecordWithoutType");
        Assert.assertEquals(returns.toString(), "0:1 1:[2,\"A\"] ");
    }

    @Test
    public void testTupleInRecordWithType() {
        Object returns = BRunUtil.invoke(program, "testTupleInRecordWithType");
        Assert.assertEquals(returns.toString(), "0:1 1:[2,\"A\"] ");
    }

    @Test
    public void testEmptyRecordIteration() {
        Object returns = BRunUtil.invoke(program, "testEmptyRecordIteration");
        Assert.assertEquals(returns.toString(), "");
    }

    @Test
    public void testForeachWithClosedRecordWithNoFields() {
        BRunUtil.invoke(program, "testForeachWithClosedRecordWithNoFields");
    }
}
