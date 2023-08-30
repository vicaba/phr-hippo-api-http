until $(curl --output /dev/null -v --fail http://localhost:8081/server-status/health); do
    printf '.'
    sleep 5
done
