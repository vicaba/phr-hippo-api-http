# phr-hippo-api-http
Personal Health Record HTTP API. Track your health-related events.

## Tech stack
* Scala
* http4s
* doobie
* postgress

## Development environment and testing

To run docker compose for dev environment run:

```bash
docker compose -f docker-compose.yml -f docker-compose-dev.yml up
```

### Testing e2e
Docker image used for e2e testing:
