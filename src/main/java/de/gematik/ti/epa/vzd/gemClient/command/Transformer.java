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

package de.gematik.ti.epa.vzd.gemClient.command;

import de.gematik.ti.epa.vzd.client.model.BaseDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.CreateDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DistinguishedName;
import de.gematik.ti.epa.vzd.client.model.UserCertificate;
import generated.CommandType;
import generated.DistinguishedNameType;
import generated.UserCertificateType;
import java.util.ArrayList;
import java.util.List;
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
        baseDirectoryEntry.setStreetAddress(command.getStreetAddress());
        baseDirectoryEntry.setPostalCode(command.getPostalCode());
        baseDirectoryEntry.setLocalityName(command.getLocalityName());
        baseDirectoryEntry.setStateOrProvinceName(command.getStateOrProvinceName());
        // This setter is manually added to the generated class BaseDirectoryEntry
        baseDirectoryEntry.setCn(command.getCn());
        baseDirectoryEntry.setTitle(command.getTitle());
        baseDirectoryEntry.setOrganization(command.getOrganization());
        baseDirectoryEntry.setOtherName(command.getOtherName());
        if (!command.getSpecialization().isEmpty()) {
            baseDirectoryEntry.setSpecialization(command.getSpecialization());
        }
        if (!command.getDomainID().isEmpty()) {
            baseDirectoryEntry.setDomainID(command.getDomainID());
        }
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
            for (UserCertificateType cert:command.getUserCertificate()){
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

}
