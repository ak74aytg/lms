# Design Notes

## Why `ArrayList` instead of an array?

The application data changes while the program runs. An array has a fixed length, so adding or removing records requires manually creating and copying into a new array. `ArrayList` grows and shrinks dynamically and provides clear methods such as `add`, `remove`, and `get`. It is a good fit for this small in-memory application.

## Where static members are used

`IdGenerator` uses private static counters and static methods. A counter belongs to the whole application, not to one student or course object, so a static member is appropriate. Each entity type has its own counter, which keeps generated IDs simple and predictable during one run.

## Where inheritance is used

`Person` contains the common identity fields and `getDisplayName()` behavior shared by people. `Student` and `Trainer` extend `Person`, call `super` in their constructors, and can specialize display behavior. This avoids duplicating common fields while demonstrating inheritance and method overriding.

## Separation of concerns

- Entities hold data and small object-specific behavior.
- Services own collections and business operations such as searching and enrolling.
- `Main` handles menus, input, output, and calls to services.
- Utility classes provide reusable validation and ID generation.
- Custom exceptions communicate expected failures without crashing the application.

