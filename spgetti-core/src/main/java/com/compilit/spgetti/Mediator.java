package com.compilit.spgetti;

import com.compilit.spgetti.api.Event;
import com.compilit.spgetti.api.Request;

interface Mediator {

  <T extends Request<R>, R> R mediateRequest(Reflection<T> request);

  <T extends Event> void mediateEvent(Reflection<T> event, boolean async);

}
