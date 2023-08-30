until $(curl --output /dev/null --silent --head --fail http://localhost:8081); do
    printf '.'
    sleep 5
done
