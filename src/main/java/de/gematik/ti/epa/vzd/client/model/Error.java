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
import java.util.Objects;

/**
 * Error
 */

public class Error {

  public static final String SERIALIZED_NAME_ATTRIBUTE_NAME = "attributeName";
  @SerializedName(SERIALIZED_NAME_ATTRIBUTE_NAME)
  private String attributeName;

  public static final String SERIALIZED_NAME_ATTRIBUTE_ERROR = "attributeError";
  @SerializedName(SERIALIZED_NAME_ATTRIBUTE_ERROR)
  private String attributeError;


  public Error attributeName(String attributeName) {

    this.attributeName = attributeName;
    return this;
  }

  /**
   * Name des Attributs, in dem ein Fehler erkannt wurde
   *
   * @return attributeName
   **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Name des Attributs, in dem ein Fehler erkannt wurde")

  public String getAttributeName() {
    return attributeName;
  }


  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }


  public Error attributeError(String attributeError) {

    this.attributeError = attributeError;
    return this;
  }

  /**
   * Beschreibung des erkannten Fehlers
   *
   * @return attributeError
   **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Beschreibung des erkannten Fehlers")

  public String getAttributeError() {
    return attributeError;
  }


  public void setAttributeError(String attributeError) {
    this.attributeError = attributeError;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.attributeName, error.attributeName) &&
        Objects.equals(this.attributeError, error.attributeError);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributeName, attributeError);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    sb.append("    attributeName: ").append(toIndentedString(attributeName)).append("\n");
    sb.append("    attributeError: ").append(toIndentedString(attributeError)).append("\n");
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

