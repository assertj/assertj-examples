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

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.junit.Test;

/**
 * {@link CompletableFuture} assertions example.
 * 
 * @author Joel Costigliola
 */
public class CompletableFutureAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void completableFuture_assertions_examples() {

    CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("something");
    assertThat(completedFuture).isCompleted()
                               .isCompletedWithValue("something")
                               .isCompletedWithValueMatching(result -> result.startsWith("some"))
                               .isCompletedWithValueMatching(result -> result.startsWith("some"), "error message")
                               .isDone();

    CompletableFuture<?> futureExplosion = new CompletableFuture<>();
    futureExplosion.completeExceptionally(new RuntimeException("boom !"));
    assertThat(futureExplosion).isCompletedExceptionally()
                               .isDone();

    // failed = CompletedExceptionally() + isNotCancelled()
    assertThat(futureExplosion).hasFailed()
                               .hasFailedWithThrowableThat()
                               .isInstanceOf(RuntimeException.class)
                               .hasMessage("boom !");

    CompletableFuture<?> cancelledFuture = new CompletableFuture<>();
    cancelledFuture.cancel(true);
    assertThat(cancelledFuture).isCancelled()
                               .isDone()
                               .isCompletedExceptionally()
                               .hasNotFailed();

    // log some error messages to have a look at them
    try {
      assertThat(completedFuture).isCompletedWithValueMatching(result -> result == null, "expected null");
    } catch (AssertionError e) {
      logAssertionErrorMessage("CompletableFutureAssert.isCompletedWithValueMatching", e);
    }
    try {
      assertThat(completedFuture).hasFailed();
    } catch (AssertionError e) {
      logAssertionErrorMessage("CompletableFutureAssert.hasFailed", e);
    }
  }

  @Test
  public void completionStage_assertions_examples() {
    CompletionStage<String> actual = completedFuture("done");
    assertThat(actual).isDone()
                      .hasNotFailed();
  }

}
