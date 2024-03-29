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
 * Fachdaten
 */

public class Fachdaten {

  public static final String SERIALIZED_NAME_DN = "dn";
  @SerializedName(SERIALIZED_NAME_DN)
  private DistinguishedName dn;

  public static final String SERIALIZED_NAME_F_A_D1 = "FAD1";
  @SerializedName(SERIALIZED_NAME_F_A_D1)
  private List<FAD1> FAD1 = null;


  public Fachdaten dn(DistinguishedName dn) {

    this.dn = dn;
    return this;
  }

  /**
   * Get dn
   *
   * @return dn
   **/
  @ApiModelProperty(required = true, value = "")

  public DistinguishedName getDn() {
    return dn;
  }


  public void setDn(DistinguishedName dn) {
    this.dn = dn;
  }


  public Fachdaten FAD1(List<FAD1> FAD1) {

    this.FAD1 = FAD1;
    return this;
  }

  public Fachdaten addFAD1Item(FAD1 FAD1Item) {
    if (this.FAD1 == null) {
      this.FAD1 = new ArrayList<>();
    }
    this.FAD1.add(FAD1Item);
    return this;
  }

  /**
   * Get FAD1
   *
   * @return FAD1
   **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<FAD1> getFAD1() {
    return FAD1;
  }


  public void setFAD1(List<FAD1> FAD1) {
    this.FAD1 = FAD1;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Fachdaten fachdaten = (Fachdaten) o;
    return Objects.equals(this.dn, fachdaten.dn) &&
        Objects.equals(this.FAD1, fachdaten.FAD1);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dn, FAD1);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Fachdaten {\n");
    sb.append("    dn: ").append(toIndentedString(dn)).append("\n");
    sb.append("    FAD1: ").append(toIndentedString(FAD1)).append("\n");
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

