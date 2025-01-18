# BigDataTP

#Installation : 

Cloner le repo
Build l'image docker présente dans le fichier deploy : docker build -t hadoop-tp3-img .
Run le container docker : docker run --rm --name hadoop-tp3-cont -p 8088:8088 -p 9870:9870 -p 9864:9864 -v path/to/data:/user/epfuser/input -v path/to/jars:/home/jars hadoop-tp3-img
Se connecter au container : docker exec -it hadoop-tp3-cont su - epfuser
Créer les dossiers suivants : hdfs dfs -mkdir -p /user/epfuser/input
Copier les fichiers de données d'entrée dans le dossier input : hdfs dfs -put /data/relationships/data.txt user/superuser/input/data.txt

#Job 1 :

hadoop jar /jars/tpfinal-augustin-nadiedjoa-job1.jar /data/relationships/data.txt /output/job1/ pour éxecuter le job
hdfs dfs -cat /output/job1/part-r-00000 pour afficher le résultat
On voit dans l'output les utilisateurs et la liste de leurs relations

#Job 2 : 

hadoop jar /jars/tpfinal-augustin-nadiedjoa-job3.jar /output/job1 /output/job2/ pour éxecuter le job
hdfs dfs -cat /output/job2/part-r-00000 pour afficher le résultat
On voit dans l'output les paires de relations et le nombre de relations communes

#Job 3 :

hadoop jar /jars/tpfinal-augustin-nadiedjoa-job3.jar /output/job2 /output/job3/ pour éxecuter le job
hdfs dfs -cat /output/job3/part-r-00000 pour afficher le résultat
On voit les recommandations de contacts pour chaque utilisateur
