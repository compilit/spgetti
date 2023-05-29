# Spgetti - everybody loves spaghetti

While spaghetti in real life might be a good thing, in code, it can be an absolute nightmare. Contrary to what the name
of this library might suggest, this 'mediator' pattern implementation is meant to make your code more readable,
manageable and
easier to test. Why call it 'Spgetti' then? Because it's easier to remember than something like 'Unspgetti' :D

The framework consists of several libraries, all split-up into API and implementations, so you'll never have to rely on
any implementation stuff for your code to compile.

The purpose of this library is to take away all the boilerplate code that is connected to
implementing this pattern. This library tries to achieve several goals:

1. Make your application as loosely coupled as possible.
2. Separate concerns
3. Make your code easier to test
4. (Optionally) Help enforce CQRS. Which is more important in bigger projects

### Without CQRS

The basic API you need is the spgetti-api, which is implemented in the spgetti-core and used in the
spgetti-spring-boot-starter. So the most basic way of using Spgetti is by grabbing that dependency and bootstrapping it
by annotating one of your configuration classes or beans with @EnableMediator.

You'll have access to a simple API consisting of a RequestDispatcher, and an
EventEmitter. These are the only dependencies you'll ever need to inject in any of your
classes/services that wish to interact with others. The internal Mediator will handle all of this
interaction. The interaction takes place through the respective Requests and Events which
are internally connected to their RequestHandler and EventHandler counterparts.

# How to use

For any operation, whether it is retrieving, changing or storing data, you'll write a Request<R> implementation, where R
represents the return type. This Request<R> encapsulates the data required for the operations. The operation is an
implementation of RequestHandler<T, R> where T is the exact implementation of the request, and R the return type.

Once this RequestHandler is turned into a registered bean and you use the RequestDispatcher to dispatch your Request,
the framework will take over an provide you with the correct return type after initiating the logic.

### With CQRS

By applying CQRS, you mildly force the programmer to split up their data-retrieving and data-altering implementations.
This can be a powerful tool for applications that need to be scalable.

If you wish to collect data from somewhere, write an implementation of a Query<ReturnType>. This query can then be
dispatched using the registered QueryDispatcher.

If you wish to mutate/add some data somewhere, write an implementation of a Command<InputData, ReturnType>. This command
can be dispatched using the registered CommandHandler.

And if you wish to notify your application that some event has occurred, write an implementation of Event and emit it
with the registered EventEmitter.

For each implemented Command, Query and Event, you need to write your own implementation of CommandHandler, QueryHandler
and EventHandler. These must be registered beans managed by the framework.

To make it a bit more clear what an implementation might look like:

```java

@Service
class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, UserResult> {

  @Override
  public UserResult handle(CreateUserCommand createUserCommand) {
    (...)
  }

}
```

Other that registering your own handler implementations and dispatching your own request implementations you don't need
to do anything. The framework will take care of it all.

# Features

### Automatic Events

All Handlers will automatically emit the events described in the overridden onAccepted and onFinished methods of each
handler. If you don't override these default methods, no events shall be emitted on these life cycle hooks.
Due to type erasure, the framework will not see any difference between TestEvent<One> and TestEvent<Two>, keep that in
mind.

# cqrs-mediator-spring

An implementation of the Mediator/CQERS pattern using Spring.

This library is an extension of the cqrs-mediator-core/api package. It adds a convenient way to bootstrap all necessary
objects into the Spring application context.

# Installation

First make sure you are in fact using Spring as your IOC supplier. This project depends on a provided spring-context
dependency
Get this dependency with the latest version.

```xml

<dependency>
  <artifactId>cqrs-mediator-spring</artifactId>
  <groupId>com.compilit</groupId>
</dependency>
```

Then add the EnableMediator annotation to your project:

```Java

import com.compilit.spgetti.EnableCqrsMediator;

@EnableCqrsMediator
@SpringBootApplication
public class Launcher {

  public static void main(String[] args) {
    SpringApplication.run(Launcher.class, args);
  }

}
```

Now all you now need to do is register QueryHandler, CommandHandler and/or EventHandler implementations.

# Usage

The Mediator pattern is about making your application loosely
coupled. <a href="https://www.compilit.com/definitions/cqrs/">CQRS</a> is
meant to make the
application robust and predictable by separating reading operations from writing operations.
Combining them is quite a
popular approach. The idea is that a Mediator is in between all requests (requests can be read or
write requests), so
that is no direct interaction with resources. This means that there are only 3 specific dependencies
which connect your
api layer to the domain layer: the CommandDispatcher, the QueryDispatcher and the EventEmitter. Why
3 instead of just
1 'Mediator' class? Because that would introduce the 'Service Locator antipattern' and defeat the
purpose of this
library. By having a separate interface for Commands and Queries, CQRS is enforced.

In the com.compilit.spgetti.api package of spgetti-cqrs-api, you'll find all interfaces which you can use to write your
own Commands and Queries, and their respective handlers.

All components which a user of the API can to interact with:

### Command-related

- <b>Command:</b> a writing operation which is handled by a single handler. It provides a return
  value option to return
  an Id of a created entity for example. Or you could return
  a <a href="https://github.com/compilit/compilit-commons/tree/main/results">Result</a>. This return value should
  never be
  filled by a reading operation.
- <b>CommandHandler:</b> the handler for a specific Command.
- <b>CommandDispatcher:</b> the main interactor for dispatching Commands.

### Query-related

- <b>Query:</b> a reading operation which is handled by a single handler.
- <b>QueryHandler:</b> the handler for a specific Query.
- <b>QueryDispatcher:</b> the main interactor for dispatching Queries.

### Event-related

- <b>Event:</b> something that has happened which other operations (EventHandlers) can subscribe to.
  Can be handled by multiple
  EventHandlers.
- <b>EventHandler:</b> the handler for a specific Event.
- <b>EventEmitter:</b> the main interactor for emitting Events.

Here is an example:

```java
import QueryDispatcher;

public class TestQuery implements Query<String> {

  private final String someData;

  public TestQuery(String someData) {
    this.someData = someData;
  }

  public String getData() {
    return someData;
  }
}


public class TestQueryHandler implements QueryHandler<TestQuery, String> {

  //This class could interact with other systems/clients/hibernate etc.
  @Override
  public String handle(TestQuery query) {
    return query.getData();
  }

}

@RestController
public class ExampleController {

  private final QueryDispatcher queryDispatcher;

  public ExampleController(QueryDispatcher queryDispatcher) {
    this.queryDispatcher = queryDispatcher;
  }

  @GetMapping("/some-example")
  public ResponseEntity<String> interact() {
    return queryDispatcher.dispatch(new TestQuery());
  }

}
```

### For those paying attention

If you've looked at my implementation you might have noticed that there is no difference in
behaviour between the
CommandHandlers and the QueryHandlers. Both have the ability to return values. It is true that
returning a value from a
Command enables the user of this library to still break with CQRS and perform reading operations
inside CommandHandlers.
This, however, is always possible. I considered this and decided that it was more important to
provide an API that is
consistent with other frameworks and libraries. Most writing operations return either the written
object, the ID of the
written object or some other kind of result. The whole point of having a separate Query and Command
class is for reading
purposes only. This way it should be clear to the reader that some operation is only about reading
or about writing.





