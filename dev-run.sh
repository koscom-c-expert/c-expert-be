PID=$(ps aux | grep '[j]ava' | awk '{print $2}')
if [ -n "$PID" ]; then
  echo "shutdown server..."
  kill -9 $PID
else
  echo "server is not up..."
fi

git checkout develop
git pull
./gradlew clean build -x test

export SPRING_PROFILES_ACTIVE=dev

# set environment variables below manually
export DB_HOST=$DB_HOST
export DB_NAME=$DB_NAME
export DB_USERNAME=$DB_USERNAME
export DB_PASSWORD=$DB_PASSWORD
export OPENAI_API_KEY=$OPENAI_API_KEY

java -jar ./build/libs/c-expert-0.0.1-SNAPSHOT.jar