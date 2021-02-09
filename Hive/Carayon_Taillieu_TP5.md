**Authors**: Carayon Chloé - Taillieu Victor
**Date**: 12/11/2020

---
# TP5:  Apache Hive
---


## Introduction

In this TP, we will use Hadoop cluster and create a connection to Hive Beeline in order to write queries that are transformed into MapReduce jobs.


## 1.1 Create a Connection to Beeline Client

We connect to Hadoop cluster using our SSH key and then initialize a Kerberos ticket:
```
-sh-4.2$ kinit
Password for ccarayon@EFREI.ONLINE: 
-sh-4.2$ klist
Ticket cache: FILE:/tmp/krb5cc_20170231
Default principal: ccarayon@EFREI.ONLINE

Valid starting       Expires              Service principal
10/11/2020 08:25:55  11/11/2020 08:25:48  krbtgt/EFREI.ONLINE@EFREI.ONLINE
	renew until 17/11/2020 08:25:48
```

We create a connection to Hive using Beeline client:
```
-sh-4.2$ beeline
... 
20/11/10 09:40:07 [main]: INFO jdbc.HiveConnection: Connected to hadoop-master03.efrei.online:10001
Connected to: Apache Hive (version 3.1.0.3.1.5.0-152)
Driver: Hive JDBC (version 3.1.0.3.1.5.0-152)
Transaction isolation: TRANSACTION_REPEATABLE_READ
Beeline version 3.1.0.3.1.5.0-152 by Apache Hive
0: jdbc:hive2://hadoop-master01.efrei.online:> 
```

We can connect to Hive using the command !connect, here we created a second connection "1":
```
0: jdbc:hive2://hadoop-master01.efrei.online:> !connect jdbc:hive2://hadoop-master01.efrei.online:2181,hadoop-master02.efrei. online:2181,hadoop-master03.efrei.online:2181/;serviceDiscoveryMode= zooKeeper;zooKeeperNamespace=hiveserver2 enter enter
Connecting to jdbc:hive2://hadoop-master01.efrei.online:2181,hadoop-master02.efrei.
enter
```

To view the jdbc connections, we use !list:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> !list
2 active connections:
 #0  open     jdbc:hive2://hadoop-master01.efrei.online:2181,hadoop-master02.efrei.online:2181,hadoop-master03.efrei.online:2181/default;httpPath=cliservice;principal=hive/_HOST@EFREI.ONLINE;serviceDiscoveryMode=zooKeeper;ssl=true;transportMode=http;zooKeeperNamespace=hiveserver2
enter
 #1  closed   jdbc:hive2://hadoop-master01.efrei.online:2181,hadoop-master02.efrei.
```

To select the connection we want, we use !go and the number of the connection: 
```
1: jdbc:hive2://hadoop-master01.efrei.online: (closed)> !go 0
```

To list all databases, we use show databases:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> show databases;
INFO  : Compiling command(queryId=hive_20201110101055_d07a8a75-6b4d-4d9b-b194-3dcea9ec7b94):  show databases
INFO  : Concurrency mode is disabled, not creating a lock manager
INFO  : Semantic Analysis Completed (retrial = false)
INFO  : Returning Hive schema: Schema(fieldSchemas:[FieldSchema(name:database_name, type:string, comment:from deserializer)], properties:null)
INFO  : Completed compiling command(queryId=hive_20201110101055_d07a8a75-6b4d-4d9b-b194-3dcea9ec7b94); Time taken: 0.003 seconds
INFO  : Concurrency mode is disabled, not creating a lock manager
INFO  : Executing command(queryId=hive_20201110101055_d07a8a75-6b4d-4d9b-b194-3dcea9ec7b94):  show databases
INFO  : Starting task [Stage-0:DDL] in serial mode
INFO  : Completed executing command(queryId=hive_20201110101055_d07a8a75-6b4d-4d9b-b194-3dcea9ec7b94); Time taken: 0.007 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+----------------+
| database_name  |
+----------------+
| comnes         |
| default        |
| table_test     |
+----------------+
```

We create our own database:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> CREATE DATABASE IF NOT EXISTS database_ccarayon;
```

Let's check its creation:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> show databases;
INFO  : Compiling command(queryId=hive_20201110101555_6abc2f67-f219-4319-a6e7-99bb4ee15f41):  show databases
INFO  : Concurrency mode is disabled, not creating a lock manager
INFO  : Semantic Analysis Completed (retrial = false)
INFO  : Returning Hive schema: Schema(fieldSchemas:[FieldSchema(name:database_name, type:string, comment:from deserializer)], properties:null)
INFO  : Completed compiling command(queryId=hive_20201110101555_6abc2f67-f219-4319-a6e7-99bb4ee15f41); Time taken: 0.005 seconds
INFO  : Concurrency mode is disabled, not creating a lock manager
INFO  : Executing command(queryId=hive_20201110101555_6abc2f67-f219-4319-a6e7-99bb4ee15f41):  show databases
INFO  : Starting task [Stage-0:DDL] in serial mode
INFO  : Completed executing command(queryId=hive_20201110101555_6abc2f67-f219-4319-a6e7-99bb4ee15f41); Time taken: 0.006 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+--------------------+
|   database_name    |
+--------------------+
| comnes             |
| database_ccarayon  |
| default            |
| table_hive         |
| table_test         |
+--------------------+
``` 

To use our database:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> USE database_ccarayon;
```

To list the tables:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> show tables;

+-----------+
| tab_name  |
+-----------+
+-----------+
```

We create a table called temp with a column called col of type String:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> CREATE TEMPORARY TABLE IF NOT EXISTS temp (Col String);
```

We confirm the table creation:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> show tables;
+-----------+
| tab_name  |
+-----------+
| temp      |
+-----------+
```

To list the columns (name, data type, etc) of temp table:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> describe temp;
+-----------+------------+----------+
| col_name  | data_type  | comment  |
+-----------+------------+----------+
| col       | string     |          |
+-----------+------------+----------+
```

To remove the table:
``` 
0: jdbc:hive2://hadoop-master01.efrei.online:> DROP TABLE temp;

0: jdbc:hive2://hadoop-master01.efrei.online:> show tables;

+-----------+
| tab_name  |
+-----------+
+-----------+
```

And finally to exit the beeline shell, we enter !quit:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> !quit
Closing: 0: jdbc:hive2://hadoop-master01.efrei.online:2181,hadoop-master02.efrei.online:2181,hadoop-master03.efrei.online:2181/default;httpPath=cliservice;principal=hive/_HOST@EFREI.ONLINE;serviceDiscoveryMode=zooKeeper;ssl=true;transportMode=http;zooKeeperNamespace=hiveserver2
```


## 1.2 Create tables

Create an external table called trees_external:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> CREATE EXTERNAL TABLE trees_external 
. . . . . . . . . . . . . . . . . . . . . . .> (geopoint string, arrondissement int, genre string, 
. . . . . . . . . . . . . . . . . . . . . . .> espece string, famille string, annee_plantation int, 
. . . . . . . . . . . . . . . . . . . . . . .> hauteur double, circonference double, adresse string, 
. . . . . . . . . . . . . . . . . . . . . . .> nom_commun string, variete string, objectid int, nom_ev string)
. . . . . . . . . . . . . . . . . . . . . . .> ROW FORMAT DELIMITED 
. . . . . . . . . . . . . . . . . . . . . . .> FIELDS TERMINATED BY ';'
. . . . . . . . . . . . . . . . . . . . . . .> LOCATION '/user/ccarayon/trees.csv';
```

For the external, when importing we need to skip the header:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> ALTER TABLE trees_external SET TBLPROPERTIES ("skip.header.line.count"="1");
```

Create an internal table:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> CREATE TABLE trees_internal 
. . . . . . . . . . . . . . . . . . . . . . .> (geopoint string, arrondissement int, genre string, 
. . . . . . . . . . . . . . . . . . . . . . .> espece string, famille string, annee_plantation int, 
. . . . . . . . . . . . . . . . . . . . . . .> hauteur double, circonference double, adresse string, 
. . . . . . . . . . . . . . . . . . . . . . .> nom_commun string, variete string, objectid int, nom_ev string)
. . . . . . . . . . . . . . . . . . . . . . .> ROW FORMAT DELIMITED 
. . . . . . . . . . . . . . . . . . . . . . .> FIELDS TERMINATED BY ';';
```

Import data to the internal table from the external table:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> INSERT INTO trees_internal SELECT * FROM trees_external;
```

Let's take a look at our columns description:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> describe trees_internal;

+-------------------+------------+----------+
|     col_name      | data_type  | comment  |
+-------------------+------------+----------+
| geopoint          | string     |          |
| arrondissement    | int        |          |
| genre             | string     |          |
| espece            | string     |          |
| famille           | string     |          |
| annee_plantation  | int        |          |
| hauteur           | double     |          |
| circonference     | double     |          |
| adresse           | string     |          |
| nom_commun        | string     |          |
| variete           | string     |          |
| objectid          | int        |          |
| nom_ev            | string     |          |
+-------------------+------------+----------+
```

We verify that the two tables have the same lines count:

* Internal 
```
SELECT COUNT(*) AS nb_columns FROM trees_internal;
+-------------+
| nb_columns  |
+-------------+
| 97          |
+-------------+
1 row selected (14,892 seconds)
```

* External
```
SELECT COUNT(*) AS nb_columns FROM trees_external;
+-------------+
| nb_columns  |
+-------------+
| 97          |
+-------------+
1 row selected (5,159 seconds)
```


## 1.3 Create queries

In this part, we are going to do the same queries as MapReduce ones using the internal table created before. 


### Displays the list of distinct containing trees

#### Query

Distinct is used to return only distinct values of district and avoid duplicate:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> SELECT distinct arrondissement AS district FROM trees_internal;
```

#### Result 
```
----------------------------------------------------------------------------------------------
	VERTICES      MODE        STATUS  TOTAL  COMPLETED  RUNNING  PENDING  FAILED  KILLED  
----------------------------------------------------------------------------------------------
Map 1 .......... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 2 ...... container     SUCCEEDED      1          1        0        0       0       0  
----------------------------------------------------------------------------------------------
VERTICES: 02/02  [==========================>>] 100%  ELAPSED TIME: 6,02 s     
----------------------------------------------------------------------------------------------
INFO  : Completed executing command(queryId=hive_20201112103613_a66b71dc-482a-44f3-b2c3-5afae497c665); Time taken: 6.74 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+-----------+
| district  |
+-----------+
| 3         |
| 4         |
| 5         |
| 6         |
| 7         |
| 8         |
| 9         |
| 11        |
| 12        |
| 13        |
| 14        |
| 15        |
| 16        |
| 17        |
| 18        |
| 19        |
| 20        |
+-----------+
17 rows selected (7,138 seconds)
```


### Displays the list of different species trees

#### Query

```
0: jdbc:hive2://hadoop-master01.efrei.online:>  SELECT DISTINCT espece AS species FROM trees_internal;
```

#### Result
```
----------------------------------------------------------------------------------------------
	VERTICES      MODE        STATUS  TOTAL  COMPLETED  RUNNING  PENDING  FAILED  KILLED  
----------------------------------------------------------------------------------------------
Map 1 .......... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 2 ...... container     SUCCEEDED      1          1        0        0       0       0  
----------------------------------------------------------------------------------------------
VERTICES: 02/02  [==========================>>] 100%  ELAPSED TIME: 3,54 s     
----------------------------------------------------------------------------------------------
INFO  : Completed executing command(queryId=hive_20201112103746_88bb55a4-9688-411d-9c2e-337ee7a9c7ba); Time taken: 4.269 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+-----------------+
|     species     |
+-----------------+
| araucana        |
| atlantica       |
| australis       |
| baccata         |
| bignonioides    |
| biloba          |
| bungeana        |
| cappadocicum    |
| carpinifolia    |
| colurna         |
| coulteri        |
| decurrens       |
| dioicus         |
| distichum       |
| excelsior       |
| fraxinifolia    |
| giganteum       |
| giraldii        |
| glutinosa       |
| grandiflora     |
| hippocastanum   |
| ilex            |
| involucrata     |
| japonicum       |
| kaki            |
| libanii         |
| monspessulanum  |
| nigra           |
| nigra laricio   |
| opalus          |
| orientalis      |
| papyrifera      |
| petraea         |
| pomifera        |
| pseudoacacia    |
| sempervirens    |
| serrata         |
| stenoptera      |
| suber           |
| sylvatica       |
| tomentosa       |
| tulipifera      |
| ulmoides        |
| virginiana      |
| x acerifolia    |
+-----------------+
45 rows selected (4,523 seconds)
``` 

### The number of trees of each kind

#### Query

By using group by, we group by kinds to perform the count for each kind:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> SELECT genre AS kind, COUNT(genre) AS nb_trees FROM trees_internal GROUP BY genre;
```

#### Result
```
----------------------------------------------------------------------------------------------
	VERTICES      MODE        STATUS  TOTAL  COMPLETED  RUNNING  PENDING  FAILED  KILLED  
----------------------------------------------------------------------------------------------
Map 1 .......... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 2 ...... container     SUCCEEDED      1          1        0        0       0       0  
----------------------------------------------------------------------------------------------
VERTICES: 02/02  [==========================>>] 100%  ELAPSED TIME: 3,63 s     
----------------------------------------------------------------------------------------------
INFO  : Completed executing command(queryId=hive_20201112110141_fb333d26-df21-4ebd-8120-5280e4f688aa); Time taken: 4.298 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+-----------------+-----------+
|      kind       | nb_trees  |
+-----------------+-----------+
| Acer            | 3         |
| Aesculus        | 3         |
| Ailanthus       | 1         |
| Alnus           | 1         |
| Araucaria       | 1         |
| Broussonetia    | 1         |
| Calocedrus      | 1         |
| Catalpa         | 1         |
| Cedrus          | 4         |
| Celtis          | 1         |
| Corylus         | 3         |
| Davidia         | 1         |
| Diospyros       | 4         |
| Eucommia        | 1         |
| Fagus           | 8         |
| Fraxinus        | 1         |
| Ginkgo          | 5         |
| Gymnocladus     | 1         |
| Juglans         | 1         |
| Liriodendron    | 2         |
| Maclura         | 1         |
| Magnolia        | 1         |
| Paulownia       | 1         |
| Pinus           | 5         |
| Platanus        | 19        |
| Pterocarya      | 3         |
| Quercus         | 4         |
| Robinia         | 1         |
| Sequoia         | 1         |
| Sequoiadendron  | 5         |
| Styphnolobium   | 1         |
| Taxodium        | 3         |
| Taxus           | 2         |
| Tilia           | 1         |
| Ulmus           | 1         |
| Zelkova         | 4         |
+-----------------+-----------+
36 rows selected (1,176 seconds)
```

### Calculates the height of the tallest tree of each kind

#### Query

MAX() allows us to obtain the maximum. By grouping by kind, it will select the maximum height of each kind:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> SELECT genre AS kind, MAX(hauteur) AS max_height FROM trees_internal GROUP BY genre; 
```

#### Result
```
----------------------------------------------------------------------------------------------
	VERTICES      MODE        STATUS  TOTAL  COMPLETED  RUNNING  PENDING  FAILED  KILLED  
----------------------------------------------------------------------------------------------
Map 1 .......... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 2 ...... container     SUCCEEDED      1          1        0        0       0       0  
----------------------------------------------------------------------------------------------
VERTICES: 02/02  [==========================>>] 100%  ELAPSED TIME: 4,26 s     
----------------------------------------------------------------------------------------------
INFO  : Completed executing command(queryId=hive_20201112104335_7511274f-45a6-4681-a56c-6328a74b4366); Time taken: 4.938 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+-----------------+-------------+
|      kind       | max_height  |
+-----------------+-------------+
| Acer            | 16.0        |
| Aesculus        | 30.0        |
| Ailanthus       | 35.0        |
| Alnus           | 16.0        |
| Araucaria       | 9.0         |
| Broussonetia    | 12.0        |
| Calocedrus      | 20.0        |
| Catalpa         | 15.0        |
| Cedrus          | 30.0        |
| Celtis          | 16.0        |
| Corylus         | 20.0        |
| Davidia         | 12.0        |
| Diospyros       | 14.0        |
| Eucommia        | 12.0        |
| Fagus           | 30.0        |
| Fraxinus        | 30.0        |
| Ginkgo          | 33.0        |
| Gymnocladus     | 10.0        |
| Juglans         | 28.0        |
| Liriodendron    | 35.0        |
| Maclura         | 13.0        |
| Magnolia        | 12.0        |
| Paulownia       | 20.0        |
| Pinus           | 30.0        |
| Platanus        | 45.0        |
| Pterocarya      | 30.0        |
| Quercus         | 31.0        |
| Robinia         | 11.0        |
| Sequoia         | 30.0        |
| Sequoiadendron  | 35.0        |
| Styphnolobium   | 10.0        |
| Taxodium        | 35.0        |
| Taxus           | 13.0        |
| Tilia           | 20.0        |
| Ulmus           | 15.0        |
| Zelkova         | 30.0        |
+-----------------+-------------+
36 rows selected (5,192 seconds)
```

### Sort the trees height from smallest to largest

#### Query

To sort the trees height, we select the height and the objectid and order them by their height. We specify that we don't take into account the NULL value for the height:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> SELECT hauteur AS height, objectid AS tree_id FROM trees_internal WHERE hauteur IS NOT NULL ORDER BY hauteur;
```

#### Result
```
----------------------------------------------------------------------------------------------
	VERTICES      MODE        STATUS  TOTAL  COMPLETED  RUNNING  PENDING  FAILED  KILLED  
----------------------------------------------------------------------------------------------
Map 1 .......... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 2 ...... container     SUCCEEDED      1          1        0        0       0       0  
----------------------------------------------------------------------------------------------
VERTICES: 02/02  [==========================>>] 100%  ELAPSED TIME: 4,47 s     
----------------------------------------------------------------------------------------------
INFO  : Completed executing command(queryId=hive_20201112104608_253b450c-a77d-472f-8d3e-84dd1e801df0); Time taken: 5.196 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+---------+----------+
| height  | tree_id  |
+---------+----------+
| 2.0     | 3        |
| 5.0     | 89       |
| 6.0     | 62       |
| 9.0     | 39       |
| 10.0    | 95       |
| 10.0    | 32       |
| 10.0    | 61       |
| 10.0    | 63       |
| 10.0    | 44       |
| 11.0    | 4        |
| 12.0    | 7        |
| 12.0    | 50       |
| 12.0    | 66       |
| 12.0    | 58       |
| 12.0    | 48       |
| 12.0    | 93       |
| 12.0    | 71       |
| 12.0    | 33       |
| 13.0    | 6        |
| 13.0    | 36       |
| 14.0    | 94       |
| 14.0    | 68       |
| 14.0    | 96       |
| 15.0    | 98       |
| 15.0    | 5        |
| 15.0    | 91       |
| 15.0    | 70       |
| 15.0    | 2        |
| 16.0    | 75       |
| 16.0    | 78       |
| 16.0    | 16       |
| 16.0    | 28       |
| 18.0    | 83       |
| 18.0    | 23       |
| 18.0    | 60       |
| 18.0    | 64       |
| 20.0    | 43       |
| 20.0    | 12       |
| 20.0    | 15       |
| 20.0    | 51       |
| 20.0    | 13       |
| 20.0    | 34       |
| 20.0    | 1        |
| 20.0    | 8        |
| 20.0    | 20       |
| 20.0    | 35       |
| 20.0    | 11       |
| 20.0    | 87       |
| 22.0    | 14       |
| 22.0    | 88       |
| 22.0    | 86       |
| 22.0    | 10       |
| 22.0    | 47       |
| 23.0    | 18       |
| 25.0    | 92       |
| 25.0    | 49       |
| 25.0    | 31       |
| 25.0    | 84       |
| 25.0    | 97       |
| 25.0    | 24       |
| 26.0    | 73       |
| 27.0    | 42       |
| 27.0    | 65       |
| 28.0    | 85       |
| 30.0    | 59       |
| 30.0    | 54       |
| 30.0    | 27       |
| 30.0    | 25       |
| 30.0    | 22       |
| 30.0    | 19       |
| 30.0    | 38       |
| 30.0    | 41       |
| 30.0    | 52       |
| 30.0    | 55       |
| 30.0    | 69       |
| 30.0    | 29       |
| 30.0    | 72       |
| 30.0    | 76       |
| 30.0    | 77       |
| 30.0    | 30       |
| 30.0    | 37       |
| 31.0    | 9        |
| 31.0    | 80       |
| 32.0    | 82       |
| 33.0    | 46       |
| 34.0    | 45       |
| 35.0    | 56       |
| 35.0    | 17       |
| 35.0    | 57       |
| 35.0    | 81       |
| 35.0    | 53       |
| 40.0    | 40       |
| 40.0    | 74       |
| 40.0    | 26       |
| 42.0    | 90       |
| 45.0    | 21       |
+---------+----------+
96 rows selected (5,462 seconds)
```

### Displays the district where the oldest tree is

#### Query

We specify that we won't take the NULL value for the year of plantation, we select the minimum year of plantation for each district. Then, we order them and to obtain only the district with the oldest tree, we use limit to take the first response:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> SELECT arrondissement AS district FROM trees_internal WHERE annee_plantation IS NOT NULL GROUP BY arrondissement ORDER BY MIN(annee_plantation) LIMIT 1;
```

#### Result
```
----------------------------------------------------------------------------------------------
	VERTICES      MODE        STATUS  TOTAL  COMPLETED  RUNNING  PENDING  FAILED  KILLED  
----------------------------------------------------------------------------------------------
Map 1 .......... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 2 ...... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 3 ...... container     SUCCEEDED      1          1        0        0       0       0  
----------------------------------------------------------------------------------------------
VERTICES: 03/03  [==========================>>] 100%  ELAPSED TIME: 4,65 s     
----------------------------------------------------------------------------------------------
INFO  : Completed executing command(queryId=hive_20201112105653_197c304a-a341-45bc-b368-2bfc36c74532); Time taken: 5.335 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+-----------+
| district  |
+-----------+
| 5         |
+-----------+
```

### Displays the district that contains the most trees

#### Query

We count for each district the number of its occurencies, then we order them in a decreasing order and take the first line:
```
0: jdbc:hive2://hadoop-master01.efrei.online:> SELECT arrondissement AS district, COUNT(*) AS nb_trees FROM trees_internal GROUP BY arrondissement ORDER BY nb_trees DESC LIMIT 1; 
```

#### Result
```
----------------------------------------------------------------------------------------------
	VERTICES      MODE        STATUS  TOTAL  COMPLETED  RUNNING  PENDING  FAILED  KILLED  
----------------------------------------------------------------------------------------------
Map 1 .......... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 2 ...... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 3 ...... container     SUCCEEDED      1          1        0        0       0       0  
----------------------------------------------------------------------------------------------
VERTICES: 03/03  [==========================>>] 100%  ELAPSED TIME: 4,05 s     
----------------------------------------------------------------------------------------------
INFO  : Completed executing command(queryId=hive_20201112110006_aa14f130-fc5d-4018-b89c-094f9aa416bb); Time taken: 4.744 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+-----------+-----------+
| district  | nb_trees  |
+-----------+-----------+
| 16        | 36        |
+-----------+-----------+
1 row selected (5,01 seconds)
```


## Conclusion 

Hive allows us to perform Map Reduce operations in a faster and easier way without imple
mented the Map Reduce Jobs by ourselves. 
