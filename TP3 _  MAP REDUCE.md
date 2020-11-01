**Author**: Carayon Chloé - Taillieu Victor
**date**: 30/10/2020

---
# TP3 :  MAP REDUCE Java
---

# Introduction
In this TP, We have to create our own MapReduce program in java.

But firstly, we have to import our csv file in the cluster:
``` 
-sh-4.2$ hdfs dfs -put tree.csv 
```

Now let's test it with the MapReduce program: 
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar \wordcount trees.csv treeoutput

```
We will obtain those kind of results:
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

Our mission is to create several Map Reduce program as the wordcount.
By looking at the intial project:
![](https://i.imgur.com/WJiAFuh.png)
We understand, we have jobs which will use mappers and reducers. 
In order to create new jobs, we will have to create new mappers and reducers, use them in the corresponding job and  will have to modify the AppDriver to run them.


## 1.8.1 Districts containing trees (very easy)
We are going to create *ListDistrics* which will show the list of the district from our input trees.
It will implement a new mapper and a new reducer.
In our source code, you can see how we implement our mapper and reducer. 
By looking on the csv file, the district are on the second column. 
For the mapper named *ListDistrictsMapper*,for each line, we will only return one information: the district as inputs for the reducer.

For the reducer and combiner called *ListReducer*, we will just do the combination of our input from the mapper.

In *ListDistrics* , we will specify that we used *ListDistrictsMapper* as mapper and *ListReducer* as combiner and reducer.

Finally, we will have to call our *ListDistrics*  in the AppDriver in order to be able to run it.

Now let's take a look on our output.
First, we copy the jar:

```
$ scp hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar ccarayon@hadoop-edge01.efrei.online:/home/ccarayon/
Welcome to EFREI Hadoop Cluster

Password: 
hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-wi 100%   51MB 931.6KB/s   00:56 

```
We can see the several MapReduce operations available:
```
--sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar
An example program must be given as the first argument.
Valid program names are:
  listdistricts: A map/reduce program that displays the list of districts in the input files.
  wordcount: A map/reduce program that counts the words in the input files.
```


Then we can do the MapReduce operation listdistricts:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar listdistricts trees.csv listdistrictout

```

We obtain the following result, it will show us all the districts:
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

We create *ListSpecies* which will show the list of the species from our input trees.
It will implement a new mapper and the previous reducer.

By looking on the csv file, the species are on the fourth column. 
For the mapper named *ListSpeciesMapper*,for each line, we will only return one information: the species as inputs for the reducer.

For the reducer and combiner we reused *ListReducer*.

In *ListSpecies* , we will specify that we used *ListSpeciesMapper* as mapper and *ListReducer* as combiner and reducer.

And we will call our *ListSpeciesMapper*  in the AppDriver in order to be able to run it.

Now let's take a look on our output.
First we run : 
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar listspecies trees.csv listspeciesout
```

And we obtain :
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

### 1.8.3 Number of trees by species (easy)
For this job, we need to calculate the number of trees for each species.
We can reuse the previous question which shows the species for this job.

Firstly, We create *CountSpecies* which will show the the number of trees by species.

For the mapper, we will reuse *ListSpeciesMapper*, which will return the species as inputs for the reducer.

For the reducer and combiner we reused *IntSumReducer* which was given with the wordcount which will combine and count the occurencies.

In *CountSpecies* , we will specify that we used *ListSpeciesMapper* as mapper and *IntSumReducer* as combiner and reducer.

And we will call our *CountSpecies*  in the AppDriver in order to be able to run it.

Now let's take a look on our output.
First we run :
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar countspecies trees.csv countspeciesout
```

We will obtain:
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

### 1.8.4 Maximum height per specie of tree (average)

We create *TallestSpecies* which will show the tallest tree of each species.
It will implement a new mapper and a new reducer.

For the mapper named *TallestSpeciesMapper*, we will focus on two information: we will use the species as a key and the height as the value for our key value couple. 

For the reducer and combiner called *MaxReducer*, we will focus on the maximum height of each species. For each key, we will compare our current height with the maximum and while change if needed. 
We will return the key with the maximum value.

In *TallestSpecies* , we will specify that we used *TallestSpeciesMapper* as mapper and *MaxReducer* as combiner and reducer.

Finally, we will have to call our *TallestSpecies*  in the AppDriver in order to be able to run it.

Now let's take a look on our output.
First we run :
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar tallestspecies trees.csv tallestspeciesout
```

We will obtain:
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