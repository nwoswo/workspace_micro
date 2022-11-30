# Kubernetes - local db

- Plugin para crear la imagen en local
- Para subir la imagen a Kubernetes 
```
  > minikube image load amigoscode-customer:2.0-SNAPSHOT
  > minikube start --insecure-registry true
    in the Deployment yamel -> imagePullPolicy: IfNotPresent

```

- Postgrest in Kubernetes

```
> kubectl delete pods –all
> minikube delete --all 
Stepts 
> kubectl delete pods,deployments,services,statefulset -A --all
```
1 package

2 load images 

```
  > minikube image load nwoswo/amigoscode-customer:2.0-SNAPSHOT
  > minikube image load nwoswo/amigoscode-fraud:2.0-SNAPSHOT
  > minikube image load nwoswo/amigoscode-notification:2.0-SNAPSHOT
```

3 Aply files in kubernetes 
```
  > kubectl apply -f k8s/minikube/bootstrap/rabbitmq
  > kubectl apply -f k8s/minikube/bootstrap/zipkin
  > kubectl apply -f k8s/minikube/services/notification
  > kubectl apply -f k8s/minikube/services/customer
  > kubectl apply -f k8s/minikube/services/fraud
```


> minikube tunnel


Hasta aqui usamos Postgres y pgadmin en docker, pero podemos conectarnos desde kubernetes, al parecer no tiene proxy para salir el pod, afuera 