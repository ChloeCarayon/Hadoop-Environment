**Author**: Carayon Chloé - Taillieu Victor
**date**: 30/10/2020

---
# TP3 :  MAP REDUCE Java
---

# Introduction
We have to create our own MapReduce program in java.

But firstly, we have to import our csv file in the cluster:
``` 
-sh-4.2$ hdfs dfs -put tree.csv 
```

Now let's test it with our MapReduce program: 
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar \wordcount trees.csv treeoutput

```
We will obtain those kind of results:
```
-sh-4.2$ hdfs dfs -cat treeoutput/part-r-00000 | head -n 8
"	3
">	8
">0</span>	3
"></span>	4
">Blame</a>	1
">Raw</a>	1
#79b8ff;width:	1
&amp;	1
```

By looking at the intial project:
![](https://i.imgur.com/WJiAFuh.png)
We understand, we have jobs which will use mappers and reducers. 
In order to create new jobs, we will have to create new mappers and reducers, use them in the corresponding job and we will have to modify the AppDriver to run them.


## Districts containing trees (very easy)
We are going to create districttrees which will implement a new mapper and a new reducer.
In our source code, you can see how we implement our mapper and reducer. 
For the mapper, we though about the fact that we only had to get the arrondissement, which mean we will only take one information per line: the arrondissement.
For the reducer, we will not do the sum, we will just do the combination.

Finally, we will have to call our districttree in the AppDriver.

Now let's take a look on our output.
First, we copy the jar:

```
Chloe-Macbook-4:~ chloe$ cd Documents/DATASCIENCE/JAVA/target
Chloe-Macbook-4:target chloe$ scp hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar ccarayon@hadoop-edge01.efrei.online:/home/ccarayon/
Welcome to EFREI Hadoop Cluster

Password: 
hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-wi 100%   51MB 931.6KB/s   00:56 

```
We can see the several MapReduce operations available:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar
An example program must be given as the first argument.
Valid program names are:
  districttrees: A map/reduce program that displays the districts in the trees file.
  wordcount: A map/reduce program that counts the words in the input files.
```


Then we can do the MapReduce operations we want, district trees:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar districttrees trees.csv treeoutput

```

We obtain the following result, it will show us all the districts:
```
-sh-4.2$ hdfs dfs -cat treeoutput/part-r-00000
11	1
12	1
13	1
14	1
15	1
16	1
17	1
18	1
19	1
20	1
3	1
4	1
5	1
6	1
7	1
8	1
9	1
```



## Show all existing species (very easy)
