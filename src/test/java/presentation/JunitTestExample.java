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

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JunitTestExample {

  private Collection<String> collection;

  @BeforeClass
  public static void oneTimeSetUp() {
    // one-time initialization code
    System.out.println("@BeforeClass - oneTimeSetUp");
  }

  @AfterClass
  public static void oneTimeTearDown() {
    // one-time cleanup code
    System.out.println("@AfterClass - oneTimeTearDown");
  }

  @Before
  public void setUp() {
    // initialization code run before each test
    collection = new ArrayList<String>();
    System.out.println("@Before - setUp");
  }

  @After
  public void tearDown() {
    // clean up code run after each test
    collection.clear();
    System.out.println("@After - tearDown");
  }

  @Test
  public void testEmptyCollection() {
    // given - a collection is created

    // then
    assertThat(collection).isEmpty();
    System.out.println("@Test - testEmptyCollection");
  }

  @Test
  public void testOneItemCollection() {
    // given - a collection is created
    
    // when
    collection.add("itemA");
    
    // then
    assertThat(collection).hasSize(1).containsExactly("itemA");
    System.out.println("@Test - testOneItemCollection");
  }
}