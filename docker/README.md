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

* open grafana: http://localhost:5601/
** create index
