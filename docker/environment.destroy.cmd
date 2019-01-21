call S:\ARSIN_V01\docker\environment.stop.cmd
docker rm influxdb
docker rm elasticsearch
docker rm grafana
docker rm kibana
docker ps --all
