package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;
import java.util.Objects;

final class IdentifiableRequest<T extends Request> implements Request, Identifiable {

  private final T innerRequest;

  IdentifiableRequest(T innerRequest) {this.innerRequest = innerRequest;}

  @Override
  public Identifier getIdentifier() {
    return Identifiers.from(innerRequest);
  }

  public T innerRequest() {return innerRequest;}

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (IdentifiableRequest) obj;
    return Objects.equals(this.innerRequest, that.innerRequest);
  }

  @Override
  public int hashCode() {
    return Objects.hash(innerRequest);
  }

  @Override
  public String toString() {
    return "IdentifiableRequest[" +
      "innerRequest=" + innerRequest + ']';
  }


}
