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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

/**
 * {@link Future} assertions example.
 * 
 * @author Joel Costigliola
 */
public class FutureAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void future_assertions_examples() throws Exception {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    Future<String> future = executorService.submit(new Callable<String>() {
      @Override
      public String call() throws Exception {
        return "done";
      }
    });

    future.get(); // to make sure the future is done
    assertThat(future).isDone()
                      .isNotCancelled();

    future = executorService.submit(new Callable<String>() {
      @Override
      public String call() throws Exception {
        Thread.sleep(10000);
        return "done";
      }
    });

    assertThat(future).isNotDone()
                      .isNotCancelled();
    future.cancel(true);
    assertThat(future).isCancelled();

  }

}
