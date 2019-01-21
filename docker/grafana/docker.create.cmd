docker run --name grafana --detach --publish 3000:3000 --link=influxdb:influxdb --link=elasticsearch:elasticsearch --volume C:\docker\sima-grafana:/var/lib/grafana grafana/grafana
docker exec -it grafana grafana-cli plugins install natel-influx-admin-panel
docker restart grafana
