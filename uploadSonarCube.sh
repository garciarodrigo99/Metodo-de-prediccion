#!/bin/bash

 sonar-scanner \
  -Dsonar.projectKey=Clasificacion \
  -Dsonar.sources=. \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=5eb4bf25993486e48ee389ffb6da5770530ad869
