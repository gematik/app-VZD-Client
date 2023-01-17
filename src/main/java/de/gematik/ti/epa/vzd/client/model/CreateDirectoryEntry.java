/*
 * I_Directory_Administration
 * REST Schnittstelle zur Pflege der Verzeichniseinträge. Über diese Schnittstelle können Verzeichniseinträge inklusive Zertifikaten erzeugt, aktualisiert und gelöscht werden. Die Administration von Fachdaten erfolgt über Schnittstelle I_Directory_Application_Maintenance und wird durch die Fachanwendungen durchgeführt. Lesender Zugriff auf die Fachdaten ist mit Operation getDirectoryEntries in vorliegender Schnittstelle möglich.
 *
 * The version of the OpenAPI document: 1.6.3
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package de.gematik.ti.epa.vzd.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CreateDirectoryEntry
 */

public class CreateDirectoryEntry {

  public static final String SERIALIZED_NAME_DIRECTORY_ENTRY_BASE = "DirectoryEntryBase";
  @SerializedName(SERIALIZED_NAME_DIRECTORY_ENTRY_BASE)
  private BaseDirectoryEntry directoryEntryBase;

  public static final String SERIALIZED_NAME_USER_CERTIFICATES = "userCertificates";
  @SerializedName(SERIALIZED_NAME_USER_CERTIFICATES)
  private List<UserCertificate> userCertificates = null;


  public CreateDirectoryEntry directoryEntryBase(BaseDirectoryEntry directoryEntryBase) {

    this.directoryEntryBase = directoryEntryBase;
    return this;
  }

  /**
   * Get directoryEntryBase
   *
   * @return directoryEntryBase
   **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BaseDirectoryEntry getDirectoryEntryBase() {
    return directoryEntryBase;
  }


  public void setDirectoryEntryBase(BaseDirectoryEntry directoryEntryBase) {
    this.directoryEntryBase = directoryEntryBase;
  }


  public CreateDirectoryEntry userCertificates(List<UserCertificate> userCertificates) {

    this.userCertificates = userCertificates;
    return this;
  }

  public CreateDirectoryEntry addUserCertificatesItem(UserCertificate userCertificatesItem) {
    if (this.userCertificates == null) {
      this.userCertificates = new ArrayList<>();
    }
    this.userCertificates.add(userCertificatesItem);
    return this;
  }

  /**
   * Get userCertificates
   *
   * @return userCertificates
   **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<UserCertificate> getUserCertificates() {
    return userCertificates;
  }


  public void setUserCertificates(List<UserCertificate> userCertificates) {
    this.userCertificates = userCertificates;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateDirectoryEntry createDirectoryEntry = (CreateDirectoryEntry) o;
    return Objects.equals(this.directoryEntryBase, createDirectoryEntry.directoryEntryBase) &&
        Objects.equals(this.userCertificates, createDirectoryEntry.userCertificates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(directoryEntryBase, userCertificates);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateDirectoryEntry {\n");
    sb.append("    directoryEntryBase: ").append(toIndentedString(directoryEntryBase)).append("\n");
    sb.append("    userCertificates: ").append(toIndentedString(userCertificates)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first
   * line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
