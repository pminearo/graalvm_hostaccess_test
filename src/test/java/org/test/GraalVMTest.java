package org.test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.foo.bar.JavaScriptAnnotationPredicate;
import org.foo.bar.JsClass;
import org.foo.bar.JsFunction;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GraalVMTest {

    private static final String EXPECTED = "TypeError: Access to host class java.lang.System is not allowed or does not exist.";
    public static final String FAIL_MESSAGE = "Script did not fail.  It should have failed when trying to get java.lang.System";
    public static final String LANGUAGE_ID = "js";

    private static String script;

    private static String legacyScript;

    @BeforeAll
    public static void setup() throws Exception {

        final ClassLoader classLoader = GraalVMTest.class.getClassLoader();

        script = IOUtils.resourceToString("graalvm.js", StandardCharsets.UTF_8, classLoader);

        if (StringUtils.isBlank(script)) {
            fail("Could not find JavaScript file graalvm.js");
        }

        legacyScript = IOUtils.resourceToString("graalvm_legacy.js", StandardCharsets.UTF_8, classLoader);

        if (StringUtils.isBlank(legacyScript)) {
            fail("Could not find JavaScript file graalvm_lefacy.js");
        }

    }

    // ************************
    // ************************
    // * allowAllAccess(true) *
    // ************************
    // ************************

    @Test
    public void testAllowAllAccessTrue() {

        // ****************************************************************************
        // This test will fail because JavaScript is allowed to get to java.lang.System
        // ****************************************************************************

        final Context context = Context.newBuilder().allowAllAccess(true).build();

        try {
            context.eval(LANGUAGE_ID, script);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    @Test
    public void testAllowAllAccessTrueWithLegacy() {

        // ****************************************************************************
        // This test will fail because JavaScript is allowed to get to java.lang.System
        // ****************************************************************************

        final Context context = Context.newBuilder().allowAllAccess(true).build();

        try {
            context.eval(LANGUAGE_ID, legacyScript);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    // ***********************************
    // ***********************************
    // * allowHostAccess(HostAccess.ALL) *
    // ***********************************
    // ***********************************

    @Test
    public void testAllowHostAccessAll() {

        // ****************************************************************************
        // This test will fail because 'Java is not defined'
        // ****************************************************************************

        final Context context = Context.newBuilder().allowHostAccess(HostAccess.ALL).build();

        try {
            context.eval(LANGUAGE_ID, script);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    @Test
    public void testAllowHostAccessAllLegacy() {

        // ****************************************************************************
        // This test will fail because 'Java is not defined'
        // ****************************************************************************

        final Context context = Context.newBuilder().allowHostAccess(HostAccess.ALL).build();

        try {
            context.eval(LANGUAGE_ID, legacyScript);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    // ****************************************
    // ****************************************
    // * allowHostAccess(HostAccess.EXPLICIT) *
    // ****************************************
    // ****************************************

    @Test
    public void testAllowHostAccessExplicit() {

        // ****************************************************************************
        // This test will fail because 'Java is not defined'
        // ****************************************************************************

        final Context context = Context.newBuilder().allowHostAccess(HostAccess.EXPLICIT).build();

        try {
            context.eval(LANGUAGE_ID, script);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    @Test
    public void testAllowHostAccessExplicitLegacy() {

        // ****************************************************************************
        // This test will fail because 'Java is not defined'
        // ****************************************************************************

        final Context context = Context.newBuilder().allowHostAccess(HostAccess.EXPLICIT).build();

        try {
            context.eval(LANGUAGE_ID, legacyScript);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    // *************************************************************
    // *************************************************************
    // * allowAllAccess(true).allowHostAccess(HostAccess.EXPLICIT) *
    // *************************************************************
    // *************************************************************

    @Test
    public void testAllowAllAccessTrueAllowHostAccessExplicit() {

        // ****************************************************************************
        // This test will fail because JavaScript is allowed to get to java.lang.System
        // However, it can not invoke getProperty('java.version').
        // ****************************************************************************

        final Context context = Context.newBuilder().allowAllAccess(true).allowHostAccess(HostAccess.EXPLICIT).build();

        try {
            context.eval(LANGUAGE_ID, script);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    @Test
    public void testAllowAllAccessTrueAllowHostAccessExplicitLegacy() {

        // ****************************************************************************
        // This test will fail because JavaScript is not allowed to access the
        // LegacyDummyJavaObject.
        // ****************************************************************************

        final Context context = Context.newBuilder().allowAllAccess(true).allowHostAccess(HostAccess.EXPLICIT).build();

        try {
            context.eval(LANGUAGE_ID, legacyScript);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    // *************************************
    // *************************************
    // * allowAllAccessTrue with Predicate *
    // *************************************
    // *************************************

    @Test
    public void testAllowAllAccessTrueWithPredicate() {

        // ****************************************************************************
        // This test will fail because JavaScript is allowed to access to
        // DummyJavaObject
        // ****************************************************************************

        final Context context = Context
                .newBuilder()
                .allowAllAccess(true)
                .allowHostClassLookup(new JavaScriptAnnotationPredicate())
                .build();

        try {
            context.eval(LANGUAGE_ID, script);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    @Test
    public void testAllowAllAccessTrueWithPredicateLegacy() {


        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!                     !!!!!
        // !!!!! THIS TEST WILL PASS !!!!!
        // !!!!!                     !!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        final Context context = Context
                .newBuilder()
                .allowAllAccess(true)
                .allowHostClassLookup(new JavaScriptAnnotationPredicate())
                .build();

        try {
            context.eval(LANGUAGE_ID, legacyScript);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    // *******************************************************
    // *******************************************************
    // * allowHostAccess(HostAccess.EXPLICIT) with Predicate *
    // *******************************************************
    // *******************************************************

    @Test
    public void testAllowHostAccessExplicitWithPredicate() {

        // ****************************************************************************
        // This test will fail because JavaScript is not allowed to access
        // DummyJavaObject.
        // ****************************************************************************

        final Context context = Context
                .newBuilder()
                .allowHostAccess(HostAccess.EXPLICIT)
                .allowHostClassLookup(new JavaScriptAnnotationPredicate())
                .build();

        try {
            context.eval(LANGUAGE_ID, script);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    @Test
    public void testAllowHostAccessExplicitWithPredicateLegacy() {

        // ****************************************************************************
        // This test will fail because: TypeError:
        // instantiate on org.foo.bar.LegacyDummyJavaObject failed due to: Message not supported.
        // ****************************************************************************

        final Context context = Context
                .newBuilder()
                .allowHostAccess(HostAccess.EXPLICIT)
                .allowHostClassLookup(new JavaScriptAnnotationPredicate())
                .build();

        try {
            context.eval(LANGUAGE_ID, legacyScript);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    @Test
    public void testAllowHostAccessExplicitWithPredicateModifiedLegacy() throws Exception {

        // ****************************************************************************
        // This test will fail because: TypeError:
        // invokeMember (reverseString) on org.foo.bar.ModifiedLegacyDummyJavaObject@41c07648 failed due to: Unknown identifier: reverseString
        // ****************************************************************************

        final ClassLoader classLoader = getClass().getClassLoader();
        final String modifiedScript = IOUtils.resourceToString("graalvm_modified_legacy.js", StandardCharsets.UTF_8, classLoader);

        final Context context = Context
                .newBuilder()
                .allowHostAccess(HostAccess.EXPLICIT)
                .allowHostClassLookup(new JavaScriptAnnotationPredicate())
                .build();

        try {
            context.eval(LANGUAGE_ID, modifiedScript);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    // *************************************
    // *************************************
    // * Build HostAccess with Annotations *
    // *************************************
    // *************************************

    @Test
    public void testBuildHostAccessWithAnnotations() {

        // ****************************************************************************
        // This test will fail because: 'Java is not defined'
        // ****************************************************************************

        final HostAccess hostAccess = HostAccess
                .newBuilder(HostAccess.EXPLICIT)
                .allowAccessAnnotatedBy(JsClass.class)
                .allowAccessAnnotatedBy(JsFunction.class)
                .build();

        final Context context = Context
                .newBuilder()
                .allowHostAccess(hostAccess)
                .build();

        try {
            context.eval(LANGUAGE_ID, script);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

    @Test
    public void testBuildHostAccessWithAnnotationsLegacy()  {

        // ****************************************************************************
        // This test will fail because: 'Java is not defined'
        // ****************************************************************************

        final HostAccess hostAccess = HostAccess
                .newBuilder(HostAccess.EXPLICIT)
                .allowAccessAnnotatedBy(JsClass.class)
                .allowAccessAnnotatedBy(JsFunction.class)
                .build();

        final Context context = Context
                .newBuilder()
                .allowHostAccess(hostAccess)
                .build();

        try {
            context.eval(LANGUAGE_ID, legacyScript);
            fail(FAIL_MESSAGE);
        } catch (final PolyglotException pge) {
            assertEquals(EXPECTED, pge.getMessage());
        }

    }

}