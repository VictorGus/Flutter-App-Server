SHELL = bash

PGPORT     ?= 5443
PGHOST     ?= localhost
PGUSER     ?= postgres
PGDATABASE ?= mobiledb
PGPASSWORD ?= postgres
PGIMAGE    ?= postgres:latest

APP_PORT   ?= 9090
APP_IMAGE  ?= victor13533/mobile-backend:latest

.EXPORT_ALL_VARIABLES:
.PHONY: test build

repl:
	rm -rf ./cpcache && clj -A:test:nrepl -m nrepl.cmdline --middleware "[cider.nrepl/cider-middleware]"

react-native:
	cd ./mobile/MobileApp/ && react-native start --port=8088

deps:
	cd ./mobile/MobileApp/ && yarn install && npm install

app:
	cd ./mobile/MobileApp/ && react-native run-android --port=8088

build:
	clojure -A:build
	mv target/app-1.0.0-SNAPSHOT-standalone.jar app.jar

run-jar:
	java -jar app.jar

test:
	clojure -A:test:runner

up:
	docker-compose up -d

down:
	docker-compose down

postgres-up:
	docker-compose -f docker-compose.db.yaml up -d

postgres-down:
	docker-compose -f docker-compose.db.yaml down

docker-build:
	docker build -f Dockerfile -t victor13533/mobile-backend .

unlock-pgdata:
	sudo chmod a+rwx pgdata && sudo chown -R root:${USER} pgdata

pub:
	docker push victor13533/mobile-backend:latest

show-screen:
	scrcpy

# deployment:
# 	kubectl apply -f ./deploy/backend.yaml && kubectl apply -f ./deploy/front-end.yaml && kubectl apply -f ./deploy/pg-cm.yaml

