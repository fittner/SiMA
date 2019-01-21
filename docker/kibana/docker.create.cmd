docker run --name kibana --detach --publish 5601:5601 --link=elasticsearch:elasticsearch docker.elastic.co/kibana/kibana:6.5.4
