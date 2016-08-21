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
package org.assertj.examples.data;

public class Mansion {
  private int guests = 6;
  private int revolverAmmo = 6;
  private String kitchen = "clean";
  private String library = "clean";
  private String candlestick = "pristine";
  private String colonel = "well kempt";
  private String professor = "well kempt";

  public void hostPotentiallyMurderousDinnerParty() {
    library = "messy";
    candlestick = "bent";
    professor = "bloodied and disheveled";
  }

  public int guests() {
    return guests;
  }

  public int revolverAmmo() {
    return revolverAmmo;
  }

  public String kitchen() {
    return kitchen;
  }

  public String library() {
    return library;
  }

  public String professor() {
    return professor;
  }

  public String colonel() {
    return colonel;
  }

  public String candlestick() {
    return candlestick;
  }
}
