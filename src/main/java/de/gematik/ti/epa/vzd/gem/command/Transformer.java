/*
 * Copyright (c) 2022 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.ti.epa.vzd.gem.command;

import de.gematik.ti.epa.vzd.client.model.BaseDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.CreateDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DistinguishedName;
import de.gematik.ti.epa.vzd.client.model.UserCertificate;
import generated.CommandType;
import generated.DistinguishedNameType;
import generated.UserCertificateType;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.StringUtils;

/**
 * This helper class helps to transform the input data (CommandType) to objects the API needs
 */
public class Transformer {

  /**
   * Transforms CommandType to BaseDirectorEntry
   *
   * @param command <type>CommandType</type>
   * @return baseDirectoryEntry <type>BaseDirectoryEntry</type>
   */
  public static BaseDirectoryEntry getBaseDirectoryEntryFromCommandType(CommandType command) {
    BaseDirectoryEntry baseDirectoryEntry = new BaseDirectoryEntry();

    if (command.getDn() != null) {
      baseDirectoryEntry.setDn(getDnFromDnType(command.getDn()));
    }
    baseDirectoryEntry.setDisplayName(command.getDisplayName());
    baseDirectoryEntry.setGivenName(command.getGivenName());
    baseDirectoryEntry.setStreetAddress(command.getStreetAddress());
    baseDirectoryEntry.setPostalCode(command.getPostalCode());
    baseDirectoryEntry.setCountryCode(command.getCountryCode());
    baseDirectoryEntry.setLocalityName(command.getLocalityName());
    baseDirectoryEntry.setStateOrProvinceName(command.getStateOrProvinceName());
    baseDirectoryEntry.setTitle(command.getTitle());
    baseDirectoryEntry.setOrganization(command.getOrganization());
    baseDirectoryEntry.setOtherName(command.getOtherName());
    baseDirectoryEntry.setTelematikID(command.getTelematikID());
    if (!command.getSpecialization().isEmpty()) {
      baseDirectoryEntry.setSpecialization(command.getSpecialization());
    }
    if (!command.getDomainID().isEmpty()) {
      baseDirectoryEntry.setDomainID(command.getDomainID());
    }
    if (!command.getHolder().isEmpty()) {
      baseDirectoryEntry.setHolder(command.getHolder());
    }
    if (!command.getEntryType().isEmpty()) {
      baseDirectoryEntry.setEntryType(command.getEntryType());
    }
    baseDirectoryEntry.setMaxKOMLEadr(command.getMaxKOMLEadr());
    return baseDirectoryEntry;
  }

  private static DistinguishedName getDnFromDnType(DistinguishedNameType dn) {
    DistinguishedName distinguishedName = new DistinguishedName();
    distinguishedName.setUid(dn.getUid());
    distinguishedName.setCn(dn.getCn());
    if (!dn.getDc().isEmpty()) {
      distinguishedName.setDc(dn.getDc());
    }
    if (!dn.getOu().isEmpty()) {
      distinguishedName.setOu(dn.getOu());
    }
    return distinguishedName;
  }

  /**
   * Transforms CommandType to CreateDirectoryEntry
   *
   * @param command <type>CommandType</type>
   * @return baseDirectoryEntry <type>BaseDirectoryEntry</type>
   */
  public static CreateDirectoryEntry getCreateDirectoryEntry(CommandType command) {
    CreateDirectoryEntry createDirectoryEntry = new CreateDirectoryEntry();
    createDirectoryEntry.setDirectoryEntryBase(getBaseDirectoryEntryFromCommandType(command));
    if (!command.getUserCertificate().isEmpty()) {
      createDirectoryEntry.setUserCertificates(new ArrayList<>());
      for (UserCertificateType cert : command.getUserCertificate()) {
        createDirectoryEntry.getUserCertificates().add(getUserCertificate(cert));
      }
    }
    return createDirectoryEntry;
  }

  private static UserCertificate getUserCertificate(
      UserCertificateType userCertificateType) {

    UserCertificate userCertificate = new UserCertificate();

    if (userCertificateType.getDn() != null) {
      userCertificate.setDn(getDnFromDnType(userCertificateType.getDn()));
    }
    userCertificate.setTelematikID(userCertificateType.getTelematikID());
    if (!userCertificateType.getUsage().isEmpty()) {
      for (String usage : userCertificateType.getUsage()) {
        userCertificate.addUsageItem(UserCertificate.UsageEnum.fromValue(usage));
      }
    }
    userCertificate.setDescription(userCertificateType.getDescription());
    if (StringUtils.isNoneBlank(userCertificateType.getUserCertificate())) {
      String cert = userCertificateType.getUserCertificate().replaceAll("[\n\r]", "").trim();
      userCertificate.setUserCertificate(cert);
    }

    return userCertificate;
  }

  public static CommandType getCommandTypeFromDirectoryEntry(DirectoryEntry entry)
      throws DatatypeConfigurationException {
    BaseDirectoryEntry baseEntry = entry.getDirectoryEntryBase();
    CommandType commandType = new CommandType();
    commandType.setDn(getDnFromDnBase(baseEntry.getDn()));
    commandType.setGivenName(baseEntry.getGivenName());
    commandType.setSn(baseEntry.getSn());
    commandType.setCn(baseEntry.getCn());
    commandType.setDisplayName(baseEntry.getDisplayName());
    commandType.setStreetAddress(baseEntry.getStreetAddress());
    commandType.setPostalCode(baseEntry.getPostalCode());
    commandType.setCountryCode(baseEntry.getCountryCode());
    commandType.setLocalityName(baseEntry.getLocalityName());
    commandType.setStateOrProvinceName(baseEntry.getStateOrProvinceName());
    commandType.setTitle(baseEntry.getTitle());
    commandType.setOrganization(baseEntry.getOrganization());
    commandType.setOtherName(baseEntry.getOtherName());
    commandType.setTelematikID(baseEntry.getTelematikID());
    if (baseEntry.getSpecialization() != null) {
      commandType.getSpecialization().addAll(baseEntry.getSpecialization());
    }
    if (baseEntry.getDomainID() != null) {
      commandType.getDomainID().addAll(baseEntry.getDomainID());
    }
    if (baseEntry.getHolder() != null) {
      commandType.getHolder().addAll(baseEntry.getHolder());
    }
    commandType.setMaxKOMLEadr(baseEntry.getMaxKOMLEadr());
    if (baseEntry.getPersonalEntry() != null) {
      commandType.setPersonalEntry(Boolean.toString(baseEntry.getPersonalEntry()));
    }
    if (baseEntry.getDataFromAuthority() != null) {
      commandType.setDataFromAuthority(Boolean.toString(baseEntry.getDataFromAuthority()));
    }
    if (baseEntry.getChangeDateTime() != null) {
      commandType.setChangeDateTime(baseEntry.getChangeDateTime());
    }
    if (baseEntry.getProfessionOID() != null) {
      commandType.getProfessionOID().addAll(baseEntry.getProfessionOID());
    }
    if (baseEntry.getEntryType() != null) {
      commandType.getEntryType().addAll(baseEntry.getEntryType());
    }
    return commandType;
  }

  private static XMLGregorianCalendar convertOffsetDateTimeToGregorianCalendar(
      OffsetDateTime changeDateTime)
      throws DatatypeConfigurationException {
    ZonedDateTime time = changeDateTime.toZonedDateTime();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(time.getSecond() * 1000);
    return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
  }


  private static DistinguishedNameType getDnFromDnBase(DistinguishedName dn) {
    DistinguishedNameType dnType = new DistinguishedNameType();
    dnType.setUid(dn.getUid());
    dnType.setCn(dn.getCn());
    return dnType;
  }

}
