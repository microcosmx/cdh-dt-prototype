

Local environment:

system:
sudo usermod -aG docker xiangzhx
sudo su -

run:
http://localhost:16601/hello1?cal=60
http://10.100.1.34:16600

mvn repo:
https://repo1.maven.org/maven2/

clean images:
docker volume rm $(docker volume ls -qf dangling=true)
docker images|grep none|awk '{print $3 }'|xargs docker rmi

build:
mvn -Dmaven.test.skip=true clean package
docker-compose -f docker-compose.yml build

run:
docker-compose -f docker-compose.yml up -d
docker-compose down
docker-compose logs -f

rest url:
http://localhost:16006/hello6?cal=50
http://rest-service-6:16006/hello6?cal=50

rabbit mq queue:
docker run -d -p 5672:5672 -p 15672:15672 --name rest-service-queue rabbitmq:management
http://localhost:15672

redis:
docker run -d --name myredis -p 6379:6379 redis






Cluster environment (swarm):

registry:
10.100.1.38:5000/v2/_catalog

insecure centos:
sudo su -
/etc/docker/daemon.json:
{"insecure-registries":["10.100.1.38:5000"]}
cp -r /opt/cdh-dt-prototype/daemon.json /etc/docker/
scp -r /opt/cdh-dt-prototype/daemon.json 10.100.1.35:/etc/docker/
ssh 10.100.1.35
service docker restart

build:
mvn clean package
docker-compose build
docker tag my-service-cluster/rest-service-dashboard 10.100.1.38:5000/my-rest-service-dashboard
docker tag my-service-cluster/rest-service-sync 10.100.1.38:5000/my-rest-service-sync
docker tag my-service-cluster/rest-service-analysis 10.100.1.38:5000/my-rest-service-analysis

docker push 10.100.1.38:5000/my-rest-service-dashboard
docker push 10.100.1.38:5000/my-rest-service-sync
docker push 10.100.1.38:5000/my-rest-service-analysis

docker stack deploy --compose-file=docker-compose-swarm.yml my-compose-swarm
docker stack ls
docker stack services my-compose-swarm
docker stack ps my-compose-swarm
docker stack rm my-compose-swarm

docker service ls --format "{{.Name}}" | grep "rest-service" | xargs docker service rm
docker service ls --format "{{.Name}}" | xargs docker service rm
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker swarm leave --force
docker node ls
docker node rm 0pvy8v3sugtmcbqualswp1rv5

docker ui:
docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
http://10.100.1.34:9000/

swarm ui:
http://10.100.1.34:9000/

run:
http://10.100.1.34:16601/hello1?cal=60
http://10.100.1.34:16600

monitoring:
ps auxw --sort=%cpu
ps auxw --sort=rss
ps auxw --sort=vsz






