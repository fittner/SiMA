influxdb and grafana docker environment

* create the environment with environment.create.cmd
* open grafana: http://localhost:3000/
* import Grafana Dashboards
** Grafana-dashboard-Influx-Admin.json
** Grafana-dashboard-SiMA.json
* create influxdb databases using dashboard-Influx-Admin
** CREATE DATABASE "sima"
** CREATE DATABASE "telegraf"
* connect grafana to influxdb databases
** Name: sima, URL: http://influxdb:8086, Database: sima
** Name: telegraf, URL: http://influxdb:8086, Database: telegraf

* start Filebeat to ship logs from SiMA to Elasticsearch
** create kibana index
*** cd S:\ARSIN_V01\docker\filebeat
*** filebeat.exe setup
** run filebeat to ship SiMA logs
*** S:\ARSIN_V01\docker\filebeat\run.sh
