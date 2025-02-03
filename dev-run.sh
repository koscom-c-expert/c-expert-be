./gradlew clean build -x test

export SPRING_PROFILES_ACTIVE=local

# set OPENAI_API_KEY environment variables manually
export OPENAI_API_KEY=$OPENAI_API_KEY
java -jar ./build/libs/c-expert-0.0.1-SNAPSHOT.jar