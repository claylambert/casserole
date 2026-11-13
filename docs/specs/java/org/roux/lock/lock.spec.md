# org.roux.lock

**Depends:** This container names no other spec.

### PinTable

Per-page pin counts so a spill waits for an in-flight read. Pin raises the count. A rotate waits until the hot page count is zero, or until the wait times out.
**semantic-key:** L40
