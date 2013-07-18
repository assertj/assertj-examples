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
 * Copyright 2012-2013 the original author or authors.
 */
package presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

public class MockitoStubExample {

  @Test
  public void stubUser() {
    // given
    User user = Mockito.mock(User.class);

    // when
    Mockito.when(user.getLogin()).thenReturn("bob");

    // then
    assertThat(user.getLogin()).isEqualTo("bob");

    // given the same mocked user
    // when
    user.setLogin("drake");

    // then
    assertThat(user.getLogin()).isEqualTo("bob");
  }

  @Test
  public void stubUserButUseRealSetLogin() {
    // given
    User user = Mockito.mock(User.class);
    // quelques pré vérifications ...
    when(user.getLogin()).thenReturn("bob");
    assertThat(user.getLogin()).isEqualTo("bob");
    user.setLogin("drake");
    assertThat(user.getLogin()).isEqualTo("bob");

    // when
    // on rétablit le comportement des méthodes getLogin() et setLogin()
    when(user.getLogin()).thenCallRealMethod();
    doCallRealMethod().when(user).setLogin(Mockito.anyString());
    user.setLogin("drake");

    // then
    assertThat(user.getLogin()).isEqualTo("drake");
  }

  @Test
  public void throwExceptionOnSetLoginAndCheckWithAssertJ() {
    // given
    User user = Mockito.mock(User.class);
    doThrow(new IllegalArgumentException("pas bien !")).when(user).setLogin("bad");

    // when
    try {
      user.setLogin("bad");
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e) {
      // then
      assertThat(e).hasMessage("pas bien !");
    }
  }

  // another way of checking that the correct exception has been thrown
  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_on_setLogin_and_check_with_JUNit() {
    // given
    User user = Mockito.mock(User.class);
    doThrow(new IllegalArgumentException("pas bien !")).when(user).setLogin("bad");

    // when
    user.setLogin("bad");

    // then expected exception is thrown (but we can't check the message)
  }

}