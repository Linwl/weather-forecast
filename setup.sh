#!/usr/bin/env bash
mvn clean package
image_version=$(date +%Y%m%d%H%M)
docker stop weather_forecast || true

docker rm weather_forecast || true

docker rmi -f $(docker images | grep linwl/weather_forecast | awk '{print $3}')

docker build . -t linwl/weather_forecast:$image_version

docker rmi -f $(docker images | grep none | awk '{print $3}')

docker images

docker run  -d --name weather_forecast --restart=always linwl/weather_forecast:$image_version

docker logs weather_forecast