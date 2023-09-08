# phr-hippo-api-http
Personal Health Record HTTP API. Track your health-related events.

## Tech stack
* scala
* http4s
* doobie
* postgres

## Development environment¡¡

To run docker compose for dev environment run:

```bash
docker compose -f docker-compose.yml -f docker-compose-dev.yml up
```

### Testing
* `MUnit` is used for unit and integration testing.
* `Nodejs` with `mocha`, `chai` and `supertest` are used for end-to-end testing.
