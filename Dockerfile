FROM java:8
MAINTAINER linwl
VOLUME /tmp
ENV TimeZone=Asia/Shanghai
COPY target/weather-forecast-jar-with-dependencies.jar weather-forecast.jar
RUN ln -snf /usr/share/zoneinfo/$TimeZone /etc/localtime && echo $TimeZone > /etc/timezone
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=dev","weather-forecast.jar"]