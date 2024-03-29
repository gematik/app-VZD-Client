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
 * DistinguishedName
 */

public class DistinguishedName {

  public static final String SERIALIZED_NAME_UID = "uid";
  @SerializedName(SERIALIZED_NAME_UID)
  private String uid;

  public static final String SERIALIZED_NAME_DC = "dc";
  @SerializedName(SERIALIZED_NAME_DC)
  private List<String> dc = null;

  public static final String SERIALIZED_NAME_OU = "ou";
  @SerializedName(SERIALIZED_NAME_OU)
  private List<String> ou = null;

  public static final String SERIALIZED_NAME_CN = "cn";
  @SerializedName(SERIALIZED_NAME_CN)
  private String cn;


  public DistinguishedName uid(String uid) {

    this.uid = uid;
    return this;
  }

  /**
   * entryID: Name/ID, den den Eintrag eindeutig identifiziert. Hat für den
   * Verzeichnisdienst_Eintrag, Certificate, KOM-LE_Fachdaten und FAD1 eines Verzeichniseintrags den
   * gleichen Wert.
   *
   * @return uid
   **/
  @ApiModelProperty(required = true, value = "entryID: Name/ID, den den Eintrag eindeutig identifiziert. Hat für den Verzeichnisdienst_Eintrag, Certificate, KOM-LE_Fachdaten und FAD1 eines Verzeichniseintrags den gleichen Wert.")

  public String getUid() {
    return uid;
  }


  public void setUid(String uid) {
    this.uid = uid;
  }


  public DistinguishedName dc(List<String> dc) {

    this.dc = dc;
    return this;
  }

  public DistinguishedName addDcItem(String dcItem) {
    if (this.dc == null) {
      this.dc = new ArrayList<>();
    }
    this.dc.add(dcItem);
    return this;
  }

  /**
   * Get dc
   *
   * @return dc
   **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getDc() {
    return dc;
  }


  public void setDc(List<String> dc) {
    this.dc = dc;
  }


  public DistinguishedName ou(List<String> ou) {

    this.ou = ou;
    return this;
  }

  public DistinguishedName addOuItem(String ouItem) {
    if (this.ou == null) {
      this.ou = new ArrayList<>();
    }
    this.ou.add(ouItem);
    return this;
  }

  /**
   * Get ou
   *
   * @return ou
   **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getOu() {
    return ou;
  }


  public void setOu(List<String> ou) {
    this.ou = ou;
  }


  public DistinguishedName cn(String cn) {

    this.cn = cn;
    return this;
  }

  /**
   * Common Name
   *
   * @return cn
   **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Common Name")

  public String getCn() {
    return cn;
  }


  public void setCn(String cn) {
    this.cn = cn;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DistinguishedName distinguishedName = (DistinguishedName) o;
    return Objects.equals(this.uid, distinguishedName.uid) &&
        Objects.equals(this.dc, distinguishedName.dc) &&
        Objects.equals(this.ou, distinguishedName.ou) &&
        Objects.equals(this.cn, distinguishedName.cn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uid, dc, ou, cn);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DistinguishedName {\n");
    sb.append("    uid: ").append(toIndentedString(uid)).append("\n");
    sb.append("    dc: ").append(toIndentedString(dc)).append("\n");
    sb.append("    ou: ").append(toIndentedString(ou)).append("\n");
    sb.append("    cn: ").append(toIndentedString(cn)).append("\n");
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

