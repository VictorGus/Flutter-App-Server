FROM openjdk:11-jre
ENV PGPORT 5432
ENV PGHOST database.cqwpiwzeijs3.eu-west-2.rds.amazonaws.com
ENV PGUSER team2user
ENV PGDATABASE project
ENV PGPASSWORD jw8s0F4
ENV PORT 9090

ADD app.jar /app.jar

CMD java -XX:-OmitStackTraceInFastThrow \
  -Dcom.sun.management.jmxremote \
  -Dcom.sun.management.jmxremote.host=127.0.0.1 \
  -Djava.rmi.server.hostname=127.0.0.1 \
  -Dcom.sun.management.jmxremote.port=9099 \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false\
  -jar /app.jar -m app.core