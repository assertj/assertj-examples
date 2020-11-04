/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2016 the original author or authors.
 */
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.assertj.core.api.BDDAssertions.thenCode;
import static org.assertj.core.api.BDDAssertions.thenNoException;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Exception assertions examples.
 *
 * @author Joel Costigliola
 */
public class ExceptionAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void exceptions_assertions_examples() {
    Exception cause = new Exception("chemical explosion");
    try {
      throw new RuntimeException("Boom!", cause);
    } catch (Exception e) {
      // you can check exception type
      assertThat(e).isInstanceOf(RuntimeException.class);

      // you can check exception's cause
      assertThat(e).hasCause(cause)
                   .hasCauseReference(cause)
                   .hasCauseInstanceOf(Exception.class);

      // you can check exception message (with String#format syntax support)
      assertThat(e).hasMessage("Boom!")
                   .hasMessage("%s!", "Boom")
                   .hasRootCauseMessage("chemical explosion")
                   .hasRootCauseMessage("chemical %s", "explosion");

      // sometimes message are not entirely predictible, you can then check for start, end or containing string.
      assertThat(e).hasMessageStartingWith("Boo")
                   .hasMessageContaining("oom")
                   .hasMessageContainingAll("Boo", "oom")
                   .hasMessageNotContainingAny("bar", "foo")
                   .hasMessageEndingWith("!");
      // this equivalent to (unless for error message which is more explicit in assertThat(e).hasMessageXXX)
      assertThat(e.getMessage()).startsWith("Boo")
                                .contains("oom")
                                .endsWith("!");

      Throwable throwableWithMessage = new IllegalArgumentException("wrong amount 123");

      assertThat(throwableWithMessage).hasMessageStartingWith("%s a", "wrong")
                                      .hasMessageContaining("wrong %s", "amount")
                                      .hasMessageEndingWith("%s 123", "amount")
                                      .hasStackTraceContaining("%s amount", "wrong");

      assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
        throw new RuntimeException(new IllegalArgumentException("cause message"));
      })
                                                       .havingCause()
                                                       .withMessage("cause message");

    }
  }

  @Test
  public void stack_trace_filtering() {
    // stack trace filtering is handy for Eclipse integration, because when a test fails Eclipse JUnit view
    // won't be cleaned of AssertJ statck trace elements.

    // check the err output to see the difference between a classic and a filtered stack trace.
    System.err.println("--------------- stack trace not filtered -----------------");
    Assertions.setRemoveAssertJRelatedElementsFromStackTrace(false);
    try {
      assertThat("Messi").isEqualTo("Ronaldo");
    } catch (AssertionError e) {
      assertThat(e).hasStackTraceContaining("newAssertionError");
      e.printStackTrace();
    }

    // --------------- stack trace not filtered -----------------
    // org.junit.ComparisonFailure: expected:<'[Ronaldo]'> but was:<'[Messi]'>
    // at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
    // at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:57)
    // at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
    // at java.lang.reflect.Constructor.newInstance(Constructor.java:532)
    // at org.assertj.core.error.ConstructorInvoker.newInstance(ConstructorInvoker.java:34)
    // at org.assertj.core.error.ShouldBeEqual.newComparisonFailure(ShouldBeEqual.java:180)
    // at org.assertj.core.error.ShouldBeEqual.comparisonFailure(ShouldBeEqual.java:171)
    // at org.assertj.core.error.ShouldBeEqual.newAssertionError(ShouldBeEqual.java:119)
    // at org.assertj.core.internal.Failures.failure(Failures.java:73)
    // at org.assertj.core.internal.Objects.assertEqual(Objects.java:138)
    // at org.assertj.core.api.AbstractAssert.isEqualTo(AbstractAssert.java:86)
    // at
    // org.assertj.core.examples.ExceptionAssertionsExamples.stack_trace_filtering(ExceptionAssertionsExamples.java:56)
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
    Assertions.setRemoveAssertJRelatedElementsFromStackTrace(true);
    try {
      assertThat("Messi").isEqualTo("Ronaldo");
    } catch (AssertionError e) {
      e.printStackTrace();
      e.getStackTrace();
    }
  }

  // see below that elements :

  // at org.assertj.core.error.ConstructorInvoker.newInstance(ConstructorInvoker.java:34)
  // at org.assertj.core.error.ShouldBeEqual.newComparisonFailure(ShouldBeEqual.java:180)
  // at org.assertj.core.error.ShouldBeEqual.comparisonFailure(ShouldBeEqual.java:171)
  // at org.assertj.core.error.ShouldBeEqual.newAssertionError(ShouldBeEqual.java:119)
  // at org.assertj.core.internal.Failures.failure(Failures.java:73)
  // at org.assertj.core.internal.Objects.assertEqual(Objects.java:138)
  // at org.assertj.core.api.AbstractAssert.isEqualTo(AbstractAssert.java:86)
  // at
  // org.assertj.core.examples.ExceptionAssertionsExamples.stack_trace_filtering(ExceptionAssertionsExamples.java:56)

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

  @Test
  public void exception_cause_assertion_examples() {

    Throwable throwable = new Throwable(new NullPointerException("boom"));

    assertThat(throwable).hasCause(new NullPointerException("boom"));

    // hasCauseInstanceOf will match inheritance.
    assertThat(throwable).hasCauseInstanceOf(NullPointerException.class);
    assertThat(throwable).hasCauseInstanceOf(RuntimeException.class);

    // hasCauseExactlyInstanceOf will match only exact same type
    assertThat(throwable).hasCauseExactlyInstanceOf(NullPointerException.class);
    try {
      assertThat(throwable).hasCauseExactlyInstanceOf(RuntimeException.class);
    } catch (AssertionError e) {
      logAssertionErrorMessage("hasCauseExactlyInstanceOf", e);
    }

    Throwable throwable_root = new Throwable(new IllegalStateException(new NullPointerException()));

    // hasRootCauseInstanceOf will match inheritance
    assertThat(throwable_root).hasRootCauseInstanceOf(NullPointerException.class);
    assertThat(throwable_root).hasRootCauseInstanceOf(RuntimeException.class);

    // hasRootCauseExactlyInstanceOf will match only exact same type
    assertThat(throwable_root).hasRootCauseExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  public void thrown_exception_assertion_examples() {
    // @format:off
    assertThatThrownBy(() -> { throw new IllegalArgumentException("boom!"); })
                     .isInstanceOf(IllegalArgumentException.class)
                     .hasMessageContaining("boom");
    // @format:on
  }

  @Test
  public void thrown_exception_assertion_alternative() {
    // @format:off
    assertThatExceptionOfType(IOException.class)
                     .isThrownBy(() -> { throw new IOException("boom!"); })
                     .withMessage("boom!")
                     .withMessageContaining("oom")
                     .withMessage("%s!", "boom")
                     .withStackTraceContaining("IOException")
                     .withNoCause();
    // @format:on
  }

  @Test
  public void thrown_exception_assertion_alternative_withStackTraceContaining() {
    // @format:off
    Throwable runtime = new RuntimeException("no way", new Exception("you shall not pass"));

    assertThat(runtime).hasCauseInstanceOf(Exception.class)
                       .hasStackTraceContaining("no way")
                       .hasStackTraceContaining("you shall not pass");


    // assertion will pass
    assertThatExceptionOfType(RuntimeException.class)
               .as("check passage")
               .isThrownBy(() -> {throw runtime;})
               .withStackTraceContaining("you shall not pass");

    assertThatNullPointerException()
                .as("check explosion")
               .isThrownBy(() -> {throw new NullPointerException("null !");})
               .withMessage("null !");
    assertThatIllegalArgumentException()
               .isThrownBy(() -> {throw new IllegalArgumentException("that's illegal !");})
               .withMessage("that's illegal !");

    assertThatIOException().isThrownBy(() -> { throw new IOException("boom!"); })
                           .withMessage("boom!")
                           .withMessageContaining("oom")
                           .withMessage("%s!", "boom")
                           .withStackTraceContaining("IOException")
                           .withNoCause();
    // @format:on
  }

  @Test
  public void bdd_style_exception_testing() {
    // given
    String[] names = { "Pier ", "Pol", "Jak" };
    // when
    Throwable thrown = catchThrowable(() -> System.out.println(names[10]));

    // then
    assertThat(thrown).isInstanceOf(ArrayIndexOutOfBoundsException.class)
                      .hasMessageContaining("10");
  }

  @Test
  public void use_as_with_exception_testing() {
    // @format:off
    Throwable assertionError = catchThrowable(() -> {
      assertThat(catchThrowable(() -> { throw new Exception("boom!"); }))
         .as("boom expected")
         .isInstanceOf(Exception.class)
         // make the assertion fail
         .hasMessageContaining("bam");
    });
    // @format:on
    assertThat(assertionError).isInstanceOf(AssertionError.class)
                              .hasMessageContaining("[boom expected]");

    // same stuff with assertThatCode
    // @format:off
    assertionError = catchThrowable(() -> {
      assertThatCode(() -> { throw new Exception("boom!");})
         .as("boom expected")
         .isInstanceOf(Exception.class)
         // make the assertion fail
         .hasMessageContaining("bam");
    });
    // @format:on
    assertThat(assertionError).isInstanceOf(AssertionError.class)
                              .hasMessageContaining("[boom expected]");
  }

  @Test
  public void check_code_does_not_throw_exceptions() {
    assertThatNoException().isThrownBy(() -> System.out.println("OK"));
    // or
    thenNoException().isThrownBy(() -> System.out.println("OK"));
    // or
    assertThatCode(() -> {}).doesNotThrowAnyException();
    // or
    thenCode(() -> {}).doesNotThrowAnyException();
  }

  @Test
  public void catchThrowableOfType_examples() {
    assertThat(catchThrowableOfType(() -> {}, Exception.class)).isNull();
  }

}
