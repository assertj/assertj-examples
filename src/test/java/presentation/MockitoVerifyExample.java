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

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class MockitoVerifyExample {

  private User user;

  @Before
  public void beforeEachTest() {
    user = Mockito.mock(User.class);
  }

  @Test
  public void verifySimpleInteractionWithMockUser() {
    // given user defined in beforeEachTest

    // when
    user.getLogin();

    // vérifie que la méthode getLogin a été appelée sur user
    Mockito.verify(user).getLogin();

    // when
    user.setLogin("bob");

    // vérifie que la méthode getLogin a été appelée sur user, avec une String strictement égale à "bob"
    verify(user).setLogin("bob");
  }

  @Test
  public void verifyInteractionWithMockUser() {
    // given user defined in beforeEachTest

    // when
    // vérifie que la méthode wait n'a jamais été appelée sur l'objet user
    verify(user, never()).hasValidLogin();

    // vérifie que la méthode m3 a été appelée exactement 2 fois sur l'objet obj
    user.getLogin();
    user.getLogin();
    verify(user, times(2)).getLogin();

    // idem avec un nombre minimum et maximum
    verify(user, atLeast(1)).getLogin();
    verify(user, atMost(3)).getLogin();
  }

  @Test
  public void verifyInteractionOrderWithTwoMockUsers() {
    // given
    User u1 = mock(User.class);
    User u2 = mock(User.class);

    // when
    u1.setLogin("bob");
    u2.setLogin("drake");

    // then
    // ajoute ces 2 mocks à l'ordre de vérification
    InOrder inOrder = Mockito.inOrder(u1, u2);

    // en inversant ces instructions, le test va échouer
    inOrder.verify(u1).setLogin("bob");
    inOrder.verify(u2).setLogin("drake");
  }

}