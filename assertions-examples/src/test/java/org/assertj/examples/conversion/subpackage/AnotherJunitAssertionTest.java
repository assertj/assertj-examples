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
package org.assertj.examples.conversion.subpackage;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.assertj.examples.data.Person;
import org.junit.Test;

/**
 * This class is just used to check the effect of the script that migrates JUnit assertions to AssertJ assertions.
 */
public class AnotherJunitAssertionTest {

  @Test
  public void using_various_junit_assertions() {
    List<String> list = newArrayList();
    assertEquals("test context", 0, list .size());
    assertEquals("test context",0,list.size());
    assertEquals(0, list.size());
    assertEquals(0,list.size());

    list = newArrayList("a", "b", "c");
    assertEquals("test context", 3, list.size());
    assertEquals("test context",3,list.size());
    assertEquals(3, list.size());
    assertEquals(3,list.size());

    Person joe = new Person("Joe", 39);
    assertEquals("test name", "Joe", joe.getName());
    assertEquals("test age",39,joe.getAge());
    assertEquals("Joe", joe.getName() );
    assertEquals(39, joe.getAge());

    assertEquals("test context", 1.0, 1.1, 0.2);
    assertEquals("test context",1.0,1.1,0.2);
    assertEquals(1.0, 1.1, 0.2);
    assertEquals(1.0,1.1,0.2);

    Object[] actual = {"a"};
    Object[] expected = {"a"};
    assertArrayEquals("test context", expected, actual);
    assertArrayEquals("test context",expected,actual);
    assertArrayEquals(expected, actual);
    assertArrayEquals(expected,actual);

    actual = expected;
    assertSame("test context", expected, actual);
    assertSame("test context",expected,actual);
    assertSame(expected, actual);
    assertSame(expected,actual);

    actual = new Object[] {"not the same"};
    assertNotSame("test context", expected, actual);
    assertNotSame("test context",expected,actual);
    assertNotSame(expected, actual);
    assertNotSame(expected,actual);

    actual = null;
    assertNull("test context",  actual);
    assertNull("test context",actual);
    assertNull( actual);
    assertNull(actual);

    actual = new Object[] {"not null"};
    assertNotNull("test context",  actual);
    assertNotNull("test context",actual);
    assertNotNull( actual);
    assertNotNull(actual);

    assertTrue("test context",  actual != null);
    assertTrue("test context",actual != null);
    assertTrue( actual != null);
    assertTrue(actual != null);

    assertFalse("test context",  actual == null);
    assertFalse("test context",actual == null);
    assertFalse( actual == null);
    assertFalse(actual == null);
  }
}
