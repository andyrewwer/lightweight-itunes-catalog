# Lightweight iTunes catalog

## How to use

To boot up the app, clone this project and run 

`./gradlew clean build bootrun`

which will render the UI on http://localhost:8080. 

### API

To test the API directly, boot up the app like above and hit the `/search` endpoint with request parameter `search`.