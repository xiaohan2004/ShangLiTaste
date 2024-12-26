#!/bin/bash

nohup java -jar ShangLiTaste-backend-0.0.1-SNAPSHOT.jar --server.port=8080 &
tailf nohup.out