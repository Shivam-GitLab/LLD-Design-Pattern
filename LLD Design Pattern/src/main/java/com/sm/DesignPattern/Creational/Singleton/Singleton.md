# Singleton Design Pattern — In-Depth Notes (Java)

## 1. What is Singleton?

Singleton is a **creational design pattern** that ensures a class has **only one instance** throughout the lifetime of an application, and provides a **global point of access** to that instance.

**Core idea:** Instead of letting anyone say `new MyClass()` whenever they want, the class controls its own instantiation and hands out the *same* object every time it's asked.

### Why would you want only one instance?
- Some things logically should exist exactly once: a config manager, a logger, a connection pool, a cache, a thread pool.
- Creating multiple instances would be wasteful (duplicate resources) or dangerous (inconsistent state).

---

## 2. Key Characteristics

Every Singleton implementation needs three things:

1. **Private constructor** — prevents `new` from outside the class.
2. **Static instance variable** — holds the one and only object, inside the class itself.
3. **Public static access method** (commonly `getInstance()`) — the only way to get the object.

```java
public class Logger {
    private static Logger instance;

    private Logger() { } // private constructor

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
}
```

This basic version works — but only in a **single-threaded** context. Most of the "different ways to implement Singleton" exist to fix problems that show up under multithreading or class-loading timing.

---

## 3. Ways to Implement Singleton in Java

### 3.1 Eager Initialization

Instance is created **at class-loading time**, whether you need it or not.

```java
public class ConfigManager {
    private static final ConfigManager instance = new ConfigManager();

    private ConfigManager() { }

    public static ConfigManager getInstance() {
        return instance;
    }
}
```

**Pros:**
- Thread-safe by default (JVM guarantees class loading is thread-safe, and static initializers run once).
- Simple, no synchronization overhead.

**Cons:**
- Instance is created even if never used → wastes memory/resources if the object is heavy (e.g., opens a DB connection) and the app never calls `getInstance()`.
- No way to handle exceptions during creation gracefully (they become `ExceptionInInitializerError`).

**Use when:** the object is lightweight, or you know it'll always be needed anyway.

---

### 3.2 Lazy Initialization (Not Thread-Safe)

Instance created **only when first requested**.

```java
public class Logger {
    private static Logger instance;

    private Logger() { }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
}
```

**Problem:** In a multithreaded environment, two threads can both pass the `if (instance == null)` check simultaneously (race condition) and create **two separate instances**, breaking the whole point of Singleton.

**Use when:** you're guaranteed single-threaded use (rare in real backend systems — avoid in production Spring Boot apps).

---

### 3.3 Thread-Safe Singleton (Synchronized Method)

Fix the race condition by synchronizing the whole method.

```java
public class Logger {
    private static Logger instance;

    private Logger() { }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
}
```

**Pros:** Thread-safe, lazy.

**Cons:** Every single call to `getInstance()` acquires a lock — even after the instance already exists. This is unnecessary overhead for the 99.9% of calls that happen *after* initialization. Performance hit under high concurrency.

---

### 3.4 Double-Checked Locking (DCL)

Only synchronize the **first time**, when the instance is actually being created. After that, no locking overhead.

```java
public class Logger {
    private static volatile Logger instance;

    private Logger() { }

    public static Logger getInstance() {
        if (instance == null) {                    // 1st check (no lock)
            synchronized (Logger.class) {
                if (instance == null) {             // 2nd check (with lock)
                    instance = new Logger();
                }
            }
        }
        return instance;
    }
}
```

**Why `volatile` is critical:**
Without `volatile`, due to Java's memory model, another thread could see a **partially constructed object**. Object creation (`new Logger()`) isn't atomic — it involves: (1) allocate memory, (2) run constructor, (3) assign reference to `instance`. The JVM/compiler is allowed to *reorder* steps 2 and 3 for optimization. Without `volatile`, one thread might see `instance != null` while the constructor hasn't fully finished running, and get a broken half-built object.

`volatile` prevents this instruction reordering and ensures visibility of writes across threads.

**Pros:** Thread-safe + lazy + good performance (locking only happens once, ever).

**Cons:** More complex, easy to get subtly wrong if you forget `volatile`.

**This is the "classic interview answer" for production-quality lazy Singleton in Java.**

---

### 3.5 Bill Pugh Singleton (Static Inner Helper Class) — **Recommended**

Uses the JVM's class-loading mechanism itself to guarantee thread safety, without any explicit synchronization.

```java
public class Logger {
    private Logger() { }

    private static class Holder {
        private static final Logger INSTANCE = new Logger();
    }

    public static Logger getInstance() {
        return Holder.INSTANCE;
    }
}
```

**How it works:**
- The inner class `Holder` is **not loaded** until `getInstance()` is called for the first time (JVM classes are loaded lazily on first use).
- When it *is* loaded, the JVM's classloader guarantees thread-safe, one-time initialization of static fields — this is a guarantee built into the language spec, so you get thread safety "for free" without locks.

**Pros:**
- Lazy (instance not created until needed).
- Thread-safe (JVM guarantees it).
- No synchronization overhead at all — better performance than DCL.
- Clean, simple code.

**Cons:** Slightly less intuitive if you've never seen the pattern before.

**This is generally considered the best general-purpose lazy Singleton implementation in Java**, when you're not using an enum.

---

### 3.6 Enum Singleton — **Most Robust**

```java
public enum ConfigManager {
    INSTANCE;

    private Map<String, String> settings = new HashMap<>();

    public String get(String key) {
        return settings.get(key);
    }
}
```

Usage: `ConfigManager.INSTANCE.get("some.key")`

**Why this is considered the safest approach (per Joshua Bloch, *Effective Java*):**
- **Serialization-safe automatically.** Normal Singleton classes can be broken by serialization/deserialization — deserializing creates a *new* object unless you manually implement `readResolve()`. Enums handle this correctly by default.
- **Reflection-safe.** Someone can use reflection (`setAccessible(true)`) to call a private constructor directly and create a second instance of a regular Singleton class. Java's enum mechanism specifically prevents this — the JVM disallows reflective instantiation of enum constants.
- **Thread-safe** by default, same guarantee as static initialization.
- Concise, no boilerplate.

**Cons:**
- Can't extend a class (enums implicitly extend `java.lang.Enum`, and Java has no multiple inheritance).
- Feels unusual/unfamiliar to some teams; harder to lazy-load in a complex way.
- Less flexible if you need to unit-test with mocks/subclasses (enums are hard to mock).

---

## 4. Comparison Table

| Approach | Lazy? | Thread-Safe? | Performance | Complexity | Notes |
|---|---|---|---|---|---|
| Eager | No | Yes | High | Low | Wastes resources if unused |
| Lazy (basic) | Yes | **No** | High | Low | Broken under concurrency |
| Synchronized method | Yes | Yes | Low (lock every call) | Low | Simple but slow |
| Double-checked locking | Yes | Yes | High | Medium | Needs `volatile` |
| Bill Pugh (holder class) | Yes | Yes | High | Medium | Best general-purpose approach |
| Enum | Yes* | Yes | High | Low | Best for serialization/reflection safety |

\*Enum constants are actually initialized eagerly when the enum class loads, but that's still "on first access" in practice.

---

## 5. How Singleton Can Be Broken (and how each fix addresses it)

It's worth knowing the ways a "Singleton" can secretly end up with multiple instances — this is a classic follow-up interview question.

1. **Multithreading race condition** → fixed by synchronization / DCL / Bill Pugh / enum.
2. **Reflection** → someone calls `constructor.setAccessible(true)` and invokes the private constructor directly.
    - Fix: throw an exception inside the constructor if `instance != null`, or use enum (JVM blocks this for enums).
3. **Serialization/Deserialization** → deserializing an object via `ObjectInputStream` creates a brand-new instance bypassing the constructor entirely.
    - Fix: implement `readResolve()` to return the existing instance, or use enum.
4. **Cloning** → if the class implements `Cloneable`, calling `.clone()` creates a second instance.
    - Fix: override `clone()` to throw `CloneNotSupportedException`.
5. **Multiple classloaders** → in complex environments (app servers, OSGi), the same class loaded by two different classloaders is treated as two different classes → two "singleton" instances. This is an edge case in most typical apps but real in enterprise/plugin architectures.

---

## 6. Real-World Use Cases

- **Logging frameworks** (e.g., a single `Logger` instance writing to one file/stream).
- **Configuration managers** — reading `application.properties` / `application.yml` once and sharing settings app-wide.
- **Database connection pools** — you want exactly one pool managing connections, not one pool per request.
- **Caching layer** — a single in-memory cache shared across the app.
- **Thread pools / Executor services** — centrally managed, not recreated per task.
- **Spring Beans**: by default, every Spring `@Component`/`@Bean` is a **Singleton scope** bean — Spring's IoC container manages exactly one instance per application context. This is Singleton pattern applied at the framework level, without you writing the `getInstance()` boilerplate yourself.

### Connecting to your WMS/Falcon WCS domain
Places you'd naturally reach for Singleton-like behavior:
- A **WCS integration client/connector** — you generally want one managed connection/session handler talking to Falcon WCS, not multiple competing instances hammering the same interface tables.
- A **wave planning configuration holder** — rules like cutoff times, sortation rules, etc., loaded once and shared read-only across the picking/packing services.
- In Spring Boot, you get this "for free" — any `@Service`/`@Component` (like a `WcsIntegrationService`) is a Singleton-scoped bean managed by the container, so you rarely hand-roll classic `getInstance()` code in Spring apps. Worth knowing the manual pattern for interviews/LLD rounds even though Spring handles it under the hood in real projects.

---

## 7. Singleton in LLD / System Design Interviews

When asked "design X" (rate limiter, cache, logger, ID generator) in an LLD round, interviewers often expect you to:
1. Recognize where a single shared instance/resource makes sense.
2. Justify **why** (shared state, expensive resource, global coordination).
3. Pick an implementation and explain thread-safety tradeoffs (this is where Bill Pugh / DCL / Enum knowledge pays off).
4. Discuss the **downsides** (see Section 8) — a good candidate doesn't just apply the pattern blindly.

---

## 8. Criticisms / Why Singleton Is Sometimes Called an "Anti-Pattern"

Worth knowing both sides — interviewers like candidates who can critique patterns, not just apply them.

- **Global state is hard to test.** Singletons behave like global variables — hard to mock/replace in unit tests, and state can leak between tests if not reset.
- **Hidden dependencies.** A class calling `Logger.getInstance()` internally hides that dependency from its constructor/interface — harder to see what a class actually depends on, compared to constructor-injecting a `Logger`.
- **Violates Single Responsibility Principle (arguably).** The class is responsible both for its core business logic *and* for managing its own lifecycle/instantiation.
- **Tight coupling.** Code directly calling `getInstance()` is coupled to that concrete class, making it harder to swap implementations.
- **Modern alternative: Dependency Injection.** Frameworks like Spring solve the "I want exactly one instance" problem via **container-managed singleton-scoped beans**, while still allowing constructor injection, easy mocking in tests, and loose coupling. This gets you the *benefit* of Singleton (one shared instance) without the *drawbacks* (hidden global state, poor testability).

**Practical takeaway:** In modern Java backend development (Spring Boot), you rarely hand-write the classic GoF Singleton — you let the framework manage bean scope. But understanding the raw pattern (and its thread-safety pitfalls) is essential for LLD interviews and for understanding *why* frameworks are designed the way they are.

---

## 9. Quick Revision Summary

- Singleton = one instance, global access, private constructor + static instance + static getter.
- Thread safety is the central challenge in "lazy" versions.
- **Bill Pugh (static holder)** = best general-purpose lazy + thread-safe approach.
- **Enum** = best when you also care about reflection/serialization safety.
- Can be broken via reflection, serialization, cloning, multiple classloaders — know the fixes.
- Spring Boot beans are Singleton-scoped by default — this is the pattern in action in your day-to-day Spring work.
- Be ready to discuss the anti-pattern criticisms (testability, hidden dependencies, DI as the modern alternative) — shows depth in interviews.