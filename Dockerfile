FROM java:8-jdk-alpine

COPY ./target/kafka-publisher-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch kafka-publisher-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","kafka-publisher-0.0.1-SNAPSHOT.jar"]

EXPOSE 9091
ARG KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
ARG ALLOW_PLAINTEXT_LISTENER=yes
ARG KAFKA_ADVERTISED_PORT=9092
ARG KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092

## Kafka Connect JDBC
ARG KAFKA_CONNECT_JDBC_VERSION=5.2.1-lkd-r0
ARG KAFKA_CONNECT_JDBC_URL="${ARCHIVE_SERVER}/lkd/packages/connectors/third-party/kafka-connect-jdbc/kafka-connect-jdbc-${KAFKA_CONNECT_JDBC_VERSION}.tar.gz"
RUN wget $DEVARCH_USER $DEVARCH_PASS "$KAFKA_CONNECT_JDBC_URL" \
         -O /opt/kafka-connect-jdbc.tar.gz \
    && mkdir -p /opt/landoop/connectors/third-party/ \
    && tar --no-same-owner -xf /opt/kafka-connect-jdbc.tar.gz \
           -C /opt/landoop/connectors/third-party/ \
    && rm -rf /opt/kafka-connect-jdbc.tar.gz