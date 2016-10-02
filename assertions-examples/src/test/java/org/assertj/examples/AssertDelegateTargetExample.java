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

import static org.assertj.core.api.Assertions.*;

import javax.swing.JButton;

import org.junit.Test;

import org.assertj.core.api.AssertDelegateTarget;

public class AssertDelegateTargetExample {

  // given a class
  public class MyButton extends JButton {
    private static final long serialVersionUID = 1L;
    private boolean blinking;

    public boolean isBlinking() { return this.blinking; }

    public void setBlinking(boolean blink) { this.blinking = blink; }

  }

  // and its corresponding assertion class implementing AssertDelegateTarget
  private static class MyButtonAssert implements AssertDelegateTarget {
    private MyButton button;

    MyButtonAssert(MyButton button) {
      this.button = button;
    }

    // assertions related to MyButton
    void isBlinking() { assertThat(button.isBlinking()).isTrue(); }
    void isNotBlinking() { assertThat(button.isBlinking()).isFalse(); }
  }

  // as it implements AssertDelegateTarget, MyButtonAssert assertions can be used through assertThat for better readability
  @Test
  public void AssertDelegateTarget_example() {
    MyButton button = new MyButton();
    MyButtonAssert buttonAssert = new MyButtonAssert(button);
    // you can encapsulate MyButtonAssert assertions methods within assertThat
    assertThat(buttonAssert).isNotBlinking(); // same as : buttonAssert.isNotBlinking();
    button.setBlinking(true);
    assertThat(buttonAssert).isBlinking(); // same as : buttonAssert.isBlinking();
  }
}
