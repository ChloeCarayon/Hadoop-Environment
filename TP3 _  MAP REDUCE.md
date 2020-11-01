**Authors**: Carayon Chloé - Taillieu Victor
**Date**: 30/10/2020

---
# TP3:  MapReduce Java
---

## Introduction

In this TP, we have to create our own MapReduce programs in Java.

But firstly, we need to import the csv file in the cluster:
```
-sh-4.2$ hdfs dfs -put tree.csv
```

Now let's test it with the wordcount MapReduce program:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar \wordcount trees.csv treeoutput
```

We obtain this result:
```
-sh-4.2$  hdfs dfs -cat treeoutput/part-r-00000 | head -n 8
(48.8183933679,	1
(48.8201249835,	1
(48.8204495642,	1
(48.8210086122,	1
(48.8213214388,	1
(48.8215800145,	1
(48.8220238534,	1
(48.8224956954,	1
```

Our mission is to create several MapReduce programs as the wordcount example.
By looking at the intial project:
![](https://i.imgur.com/WJiAFuh.png)
We understand that we have jobs that use mappers and reducers.
In order to create new jobs, we will have to create new mappers and reducers, use them in the corresponding job and include the jobs in the AppDriver to be able to run them.

## 1.8.1 Districts containing trees (very easy)

We created *ListDistrics* which displays the list of districts containing trees in the input file.
It uses a new mapper and a new reducer.
In our source code, you can see how we implemented our mapper and reducer.
By looking at the csv file, we see that the districts are in the second column.
For each line, the mapper named *ListDistrictsMapper* only returns one information: the district as input key for the reducer (and the arbitrary value 1).

For the reducer and combiner called *ListReducer*, we just combine the keys from the mapper.

In *ListDistrics*, we specify that we use *ListDistrictsMapper* as mapper and *ListReducer* as combiner and reducer.

Finally, we have to call our *ListDistrics* in the AppDriver in order to be able to run it.

Now let's take a look at the output.
First, we copy the jar:
```
$ scp hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar ccarayon@hadoop-edge01.efrei.online:/home/ccarayon/
Welcome to EFREI Hadoop Cluster

Password:
hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-wi 100%   51MB 931.6KB/s   00:56
```

We can see the several MapReduce programs available:
```
--sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar
An example program must be given as the first argument.
Valid program names are:
  listdistricts: A map/reduce program that displays the list of districts in the input files.
  wordcount: A map/reduce program that counts the words in the input files.
```

Then we can execute the MapReduce program listdistricts:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar listdistricts trees.csv listdistrictout
```

We obtain the following result, it will list all districts containing trees:
```
-sh-4.2$ hdfs dfs -cat listdistrictout/part-r-00000
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

## 1.8.2 Show all existing species (very easy)

We created *ListSpecies* which displays the list of different trees species from the input file.
It uses a new mapper and the previous reducer.

By looking at the csv file, we see that the species are in the fourth column.
For each line, the mapper called *ListSpeciesMapper* only returns one information: the species as input key for the reducer (and the arbitrary value 1).

For the reducer and combiner we reused *ListReducer*.

In *ListSpecies*, we specify that we use *ListSpeciesMapper* as mapper and *ListReducer* as combiner and reducer.

And we call our *ListSpecies* in the AppDriver in order to be able to run it.

Now let's take a look at the output.
First we run:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar listspecies trees.csv listspeciesout
```

And we obtain:
```
-sh-4.2$ hdfs dfs -cat listspeciesout/part-r-00000
araucana	1
atlantica	1
australis	1
baccata	1
bignonioides	1
biloba	1
bungeana	1
cappadocicum	1
carpinifolia	1
colurna	1
coulteri	1
decurrens	1
dioicus	1
distichum	1
excelsior	1
fraxinifolia	1
giganteum	1
giraldii	1
glutinosa	1
grandiflora	1
hippocastanum	1
ilex	1
involucrata	1
japonicum	1
kaki	1
libanii	1
monspessulanum	1
nigra	1
nigra laricio	1
opalus	1
orientalis	1
papyrifera	1
petraea	1
pomifera	1
pseudoacacia	1
sempervirens	1
serrata	1
stenoptera	1
suber	1
sylvatica	1
tomentosa	1
tulipifera	1
ulmoides	1
virginiana	1
x acerifolia	1
```

## 1.8.3 Number of trees by species (easy)

For this job, we need to calculate the number of trees for each species.
We can reuse the previous question which displays the list of species.

Firstly, we created *CountSpecies* which should display the number of trees by species.

For the mapper, we reused *ListSpeciesMapper*, which returns the species as input keys for the reducer.

For the reducer and combiner we reused *IntSumReducer* which was given with the wordcount, it combines the keys and counts their occurence.

In *CountSpecies*, we specify that we use *ListSpeciesMapper* as mapper and *IntSumReducer* as combiner and reducer.

And we call our *CountSpecies* in the AppDriver in order to be able to run it.

Now let's take a look at the output.
First we run:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar countspecies trees.csv countspeciesout
```

We obtain:
```
-sh-4.2$ hdfs dfs -cat countspeciesout/part-r-00000
araucana	1
atlantica	2
australis	1
baccata	2
bignonioides	1
biloba	5
bungeana	1
cappadocicum	1
carpinifolia	4
colurna	3
coulteri	1
decurrens	1
dioicus	1
distichum	3
excelsior	1
fraxinifolia	2
giganteum	5
giraldii	1
glutinosa	1
grandiflora	1
hippocastanum	3
ilex	1
involucrata	1
japonicum	1
kaki	2
libanii	2
monspessulanum	1
nigra	3
nigra laricio	1
opalus	1
orientalis	8
papyrifera	1
petraea	2
pomifera	1
pseudoacacia	1
sempervirens	1
serrata	1
stenoptera	1
suber	1
sylvatica	8
tomentosa	2
tulipifera	2
ulmoides	1
virginiana	2
x acerifolia	11
```

## 1.8.4 Maximum height per specie of tree (average)

We created *TallestSpecies* which calculates the height of the tallest tree of each species.
It implements a new mapper and a new reducer.

For the mapper named *TallestSpeciesMapper*, we focus on two information: we need the species as a key and the height as a value for our key value couple.

For the reducer and combiner called *MaxReducer*, we focus on the maximum height of each species. For each key, we compare each height with the current maximum and change it if the height read is bigger.
We return the key with the corresponding maximum value of height.

In *TallestSpecies*, we specify that we use *TallestSpeciesMapper* as mapper and *MaxReducer* as combiner and reducer.

Finally, we call our *TallestSpecies* in the AppDriver in order to be able to run it.

Now let's take a look at the output.
First we run:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar tallestspecies trees.csv tallestspeciesout
```

We obtain:
```
-sh-4.2$ hdfs dfs -cat tallestspeciesout/part-r-00000
araucana	9.0
atlantica	25.0
australis	16.0
baccata	13.0
bignonioides	15.0
biloba	33.0
bungeana	10.0
cappadocicum	16.0
carpinifolia	30.0
colurna	20.0
coulteri	14.0
decurrens	20.0
dioicus	10.0
distichum	35.0
excelsior	30.0
fraxinifolia	27.0
giganteum	35.0
giraldii	35.0
glutinosa	16.0
grandiflora	12.0
hippocastanum	30.0
ilex	15.0
involucrata	12.0
japonicum	10.0
kaki	14.0
libanii	30.0
monspessulanum	12.0
nigra	30.0
nigra laricio	30.0
opalus	15.0
orientalis	34.0
papyrifera	12.0
petraea	31.0
pomifera	13.0
pseudoacacia	11.0
sempervirens	30.0
serrata	18.0
stenoptera	30.0
suber	10.0
sylvatica	30.0
tomentosa	20.0
tulipifera	35.0
ulmoides	12.0
virginiana	14.0
x acerifolia	45.0
```
