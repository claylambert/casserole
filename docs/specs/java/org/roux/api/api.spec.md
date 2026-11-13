# org.roux.api

## 3. Class dependencies on the add-that-spills path

Types that **do not** participate in spill itself: `MemoryInspector` (except later read), snapshot APIs (UNSUPPORTED), `message list` (read path).
**semantic-key:** PK-482
