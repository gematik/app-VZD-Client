/*
 * Copyright (c) 2020 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.ti.epa.vzd.gemClient;

public enum CommandNamesEnum {

  ADD_DIR_ENTRY("addDirectoryEntries"),
  READ_DIR_ENTRY("readDirectoryEntries"),
  MOD_DIR_ENTRY("modifyDirectoryEntries"),
  DEL_DIR_ENTRY("deleteDirectoryEntries"),
  ADD_DIR_CERT("addDirectoryEntryCertificate"),
  READ_DIR_CERT("readDirectoryEntryCertificate"),
  MOD_DIR_CERT("modifyDirectoryEntryCertificate"),
  DEL_DIR_CERT("deleteDirectoryEntryCertificate");

  private final String name;

  CommandNamesEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public static CommandNamesEnum getEntry(String name) {
    for (CommandNamesEnum cn : CommandNamesEnum.values()) {
      if (name.equals(cn.getName())) {
        return cn;
      }
    }
    return null;
  }
}
