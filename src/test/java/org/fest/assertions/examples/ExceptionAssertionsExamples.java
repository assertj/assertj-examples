package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import org.fest.assertions.internal.Failures;

/**
 * Exception assertions examples.
 * 
 * @author Joel Costigliola
 */
public class ExceptionAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void exceptions_assertions_examples() {
    assertThat(fellowshipOfTheRing).hasSize(9);
    try {
      fellowshipOfTheRing.get(9); // argggl !
    } catch (Exception e) {
      // you can check exception type
      assertThat(e).isInstanceOf(IndexOutOfBoundsException.class);

      // you can check if exception nas no cause
      assertThat(e).hasNoCause();

      // you can check exception message
      assertThat(e).hasMessage("Index: 9, Size: 9");

      // sometimes message are not entirely predictible, you can then check for start, end or containing string.
      assertThat(e).hasMessageStartingWith("Index: 9").hasMessageContaining("9").hasMessageEndingWith("Size: 9");
      // this equivalent to (unless for error message which is more explicit in assertThat(e).hasMessageXXX)
      assertThat(e.getMessage()).startsWith("Index: 9").contains("9").endsWith("Size: 9");
    }
  }

  @Test
  public void stack_trace_filtering() {
    // stack trace filtering is handy for Eclipse integration, because when a test fails Eclipse JUnit view
    // won't be cleaned of Fest statck trace elements.

    // check the err output to see the difference between a classic and a filtered stack trace.
    System.err.println("--------------- stack trace not filtered -----------------");
    Failures.instance().setRemoveFestRelatedElementsFromStackTrace(false);
    try {
      assertThat("Messi").isEqualTo("Ronaldo");
    } catch (AssertionError e) {
      e.printStackTrace();
    }

    // --------------- stack trace not filtered -----------------
    // org.junit.ComparisonFailure: expected:<'[Ronaldo]'> but was:<'[Messi]'>
    // at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
    // at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:57)
    // at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
    // at java.lang.reflect.Constructor.newInstance(Constructor.java:532)
    // at org.fest.assertions.error.ConstructorInvoker.newInstance(ConstructorInvoker.java:34)
    // at org.fest.assertions.error.ShouldBeEqual.newComparisonFailure(ShouldBeEqual.java:180)
    // at org.fest.assertions.error.ShouldBeEqual.comparisonFailure(ShouldBeEqual.java:171)
    // at org.fest.assertions.error.ShouldBeEqual.newAssertionError(ShouldBeEqual.java:119)
    // at org.fest.assertions.internal.Failures.failure(Failures.java:73)
    // at org.fest.assertions.internal.Objects.assertEqual(Objects.java:138)
    // at org.fest.assertions.api.AbstractAssert.isEqualTo(AbstractAssert.java:86)
    // at
    // org.fest.assertions.examples.ExceptionAssertionsExamples.stack_trace_filtering(ExceptionAssertionsExamples.java:56)
    // at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    // at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
    // at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    // at java.lang.reflect.Method.invoke(Method.java:616)
    // at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
    // at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
    // at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
    // at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
    // at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
    // at org.junit.runners.BlockJUnit4ClassRunner.runNotIgnored(BlockJUnit4ClassRunner.java:79)
    // at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:71)
    // at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:49)
    // at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
    // at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
    // at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
    // at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
    // at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
    // at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
    // at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
    // at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
    // at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
    // at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
    // at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
    // at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)

    System.err.println("\n--------------- stack trace filtered -----------------");
    Failures.instance().setRemoveFestRelatedElementsFromStackTrace(true);
    try {
      assertThat("Messi").isEqualTo("Ronaldo");
    } catch (AssertionError e) {
      e.printStackTrace();
      e.getStackTrace();
    }
  }
  // see below that elements :
  
  // at org.fest.assertions.error.ConstructorInvoker.newInstance(ConstructorInvoker.java:34)
  // at org.fest.assertions.error.ShouldBeEqual.newComparisonFailure(ShouldBeEqual.java:180)
  // at org.fest.assertions.error.ShouldBeEqual.comparisonFailure(ShouldBeEqual.java:171)
  // at org.fest.assertions.error.ShouldBeEqual.newAssertionError(ShouldBeEqual.java:119)
  // at org.fest.assertions.internal.Failures.failure(Failures.java:73)
  // at org.fest.assertions.internal.Objects.assertEqual(Objects.java:138)
  // at org.fest.assertions.api.AbstractAssert.isEqualTo(AbstractAssert.java:86)
  // at
  // org.fest.assertions.examples.ExceptionAssertionsExamples.stack_trace_filtering(ExceptionAssertionsExamples.java:56)
  
  // don't appear in :

  // --------------- stack trace filtered -----------------
  // org.junit.ComparisonFailure: expected:<'[Ronaldo]'> but was:<'[Messi]'>
  // at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
  // at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:57)
  // at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
  // at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
  // at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
  // at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
  // at java.lang.reflect.Method.invoke(Method.java:616)
  // at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
  // at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
  // at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
  // at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
  // at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
  // at org.junit.runners.BlockJUnit4ClassRunner.runNotIgnored(BlockJUnit4ClassRunner.java:79)
  // at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:71)
  // at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:49)
  // at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
  // at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
  // at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
  // at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
  // at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
  // at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
  // at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
  // at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
  // at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
  // at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
  // at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
  // at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)
}
