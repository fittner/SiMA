mkdir C:\docker
mkdir C:\docker\sima-elasticsearch
mkdir C:\docker\sima-grafana
mkdir C:\docker\sima-influxdb

cd /d S:\ARSIN_V01\docker
docker stack deploy -c docker-compose.yml sima
