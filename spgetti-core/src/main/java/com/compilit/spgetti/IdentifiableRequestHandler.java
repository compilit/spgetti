package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import java.util.Objects;

final class IdentifiableRequestHandler<H extends RequestHandler<T, R>, T extends Request<R>, R>
  implements RequestHandler<T, R>, Identifiable {

  private final H innerHandler;

  IdentifiableRequestHandler(H innerHandler) {this.innerHandler = innerHandler;}

  @Override
  public R handle(T request) {
    return (R) innerHandler.handle(request);
  }

  @Override
  public <T1 extends Request<?>> boolean canHandle(T1 request) {
    var identifiableRequest = new IdentifiableRequest<>(request);
    return getIdentifier().matches(identifiableRequest.getIdentifier());
  }

  @Override
  public Identifier getIdentifier() {
    return new HandlerIdentifier(innerHandler.getClass());
  }

  public H innerHandler() {return innerHandler;}

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (IdentifiableRequestHandler) obj;
    return Objects.equals(this.innerHandler, that.innerHandler);
  }

  @Override
  public int hashCode() {
    return Objects.hash(innerHandler);
  }

  @Override
  public String toString() {
    return "IdentifiableRequestHandler[" +
      "innerHandler=" + innerHandler + ']';
  }

}
