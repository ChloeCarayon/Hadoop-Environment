**Authors**: Carayon Chloé - Taillieu Victor
**Date**: 30/10/2020

---
# TP3:  MapReduce Java
---

## Introduction

In this TP, we have to create our own MapReduce programs in Java.

Firstly, we have to import the jar in the cluster. We will do it again to store new MapReduce programs.
By building Maven, we get jar and import it in our cluster. 
```
$ scp hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar ccarayon@hadoop-edge01.efrei.online:/home/ccarayon/
Welcome to EFREI Hadoop Cluster

Password: 
hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-wi 100%   51MB   2.3MB/s   00:22   
```

By running *wordcount*, we obtain: 
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar 
An example program must be given as the first argument.
Valid program names are:
  wordcount: A map/reduce program that counts the words in the input files.
```

Now let's test it with the wordcount MapReduce program on our csv file trees.csv :
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

### 1.8.1.1 Implementation

We create *ListDistrics* to display the list of districts containing trees.
It uses a new mapper and a new reducer.
In our source code, you can see how we implemented our mapper and reducer.
By looking at the csv file, we see that the districts are in the second column.
For each line, the mapper *ListDistrictsMapper* only returns one information: the district as key (and null for the value) for the reducer.

For the reducer and combiner *ListReducer*, we just combine the keys from the mapper.
In *ListDistrics*, we specify that we use *ListDistrictsMapper* as mapper and *ListReducer* as combiner and reducer.
Finally, we have to call our *ListDistrics* in the AppDriver in order to be able to run it.

### 1.8.1.2 Commands and result

Now let's take a look at the output.
First, we copy the jar:
```
$ scp hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar ccarayon@hadoop-edge01.efrei.online:/home/ccarayon/
Welcome to EFREI Hadoop Cluster

Password:
hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-wi 100%   51MB 931.6KB/s   00:56
```

We see the several MapReduce programs available:
```
--sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar
An example program must be given as the first argument.
Valid program names are:
  listdistricts: A map/reduce program that displays the list of districts in the input files.
  wordcount: A map/reduce program that counts the words in the input files.
```

Then we execute listdistricts:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar listdistricts trees.csv listdistrictout
```

We obtain the following result, it will list all districts containing trees:
```
-sh-4.2$ hdfs dfs -cat listdistrictout/part-r-00000
11
12
13
14
15
16
17
18
19
20
3
4
5
6
7
8
9
```


## 1.8.2 Show all existing species (very easy)

### 1.8.2.1 Implementation

We create *ListSpecies* to display the list of different trees species.
It uses a new mapper and the previous reducer.

By looking at the csv file, we see that the species are in the fourth column.
For each line, the mapper *ListSpeciesMapper* only returns one information: the species as  key (and null for the value) as inputs for the reducer.

For the reducer and combiner we reuse *ListReducer*.

In *ListSpecies*, we specify that we use *ListSpeciesMapper* as mapper and *ListReducer* as combiner and reducer.

And we call our *ListSpecies* in the AppDriver in order to be able to run it.

### 1.8.2.2 Commands and result

Now let's take a look at the output.
We run:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar listspecies trees.csv listspeciesout
```

And we obtain:
```
-sh-4.2$ hdfs dfs -cat listspeciesout/part-r-00000
araucana
atlantica
australis
baccata
bignonioides
biloba
bungeana
cappadocicum
carpinifolia
colurna
coulteri
decurrens
dioicus
distichum
excelsior
fraxinifolia
giganteum
giraldii
glutinosa
grandiflora
hippocastanum
ilex
involucrata
japonicum
kaki
libanii
monspessulanum
nigra
nigra laricio
opalus
orientalis
papyrifera
petraea
pomifera
pseudoacacia
sempervirens
serrata
stenoptera
suber
sylvatica
tomentosa
tulipifera
ulmoides
virginiana
x acerifolia
```

## 1.8.3 Number of trees by species (easy)

### 1.8.3.1 Implementation 

For this job, we need to calculate the number of trees for each species.
We can reuse the previous question which displays the list of species.

Firstly, we create *CountSpecies* which displays the number of trees by species.
For the mapper, we reuse *ListSpeciesMapper*, which returns the species as input keys for the reducer.
For the reducer and combiner we reused *IntSumReducer* which was given for the wordcount, it combines the keys and counts their occurence.

In *CountSpecies*, we specify that we use *ListSpeciesMapper* as mapper and *IntSumReducer* as combiner and reducer.
And we call our *CountSpecies* in the AppDriver in order to be able to run it.

### 1.8.3.2 Commands and result

Now let's take a look at the output.
We run:
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

### 1.8.4.1 Implementation

We create *TallestSpecies* which calculates the height of the tallest tree of each species.
It implements a new mapper and a new reducer.

For the mapper *TallestSpeciesMapper*, we focus on two information: we need the species as a key and the height as a value for our key value couple.

For the reducer and combiner *MaxReducer*, we focus on the maximum height of each species. For each key, we compare each height with the current maximum and change it if the height read is bigger.
We return the key with the corresponding maximum value of height.

In *TallestSpecies*, we specify that we use *TallestSpeciesMapper* as mapper and *MaxReducer* as combiner and reducer.
Finally, we call our *TallestSpecies* in the AppDriver in order to be able to run it.


### 1.8.4.2 Commands and result

Now let's take a look at the output.
We run:
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

## 1.8.5 Sort the trees height from smallest to largest (average)

### 1.8.5.1 Implementation

We create *SortHeight* to sort the trees height from the smallest to the largest. 
It implements a new mapper and a new reducer.

For the mapper *SortHeightMapper*, we focus on two information: we will take the height (as a float) of the tree as a key and the id (an int) as value. 
For the reducer and combiner called *SortReducer*, we have key as float so it will order the trees by itself. We add a for loop to write all the key value couple.
For this part, we had to specify in those class that we use floatWritable and not Text as we have input and output key which are float.

In *SortHeight*, we specify that we use *SortHeightMapper* as mapper and *SortReducer* as combiner and reducer.
Finally, we call our *SortHeight* in the AppDriver in order to be able to run it.

### 1.8.5.2 Commands and result

Now let's take a look at the output.
We run:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar sortheight trees.csv sortheightout
```

And we obtain:
``` 
-sh-4.2$ hdfs dfs -cat sortheightout/part-r-00000

2.0	3
5.0	89
6.0	62
9.0	39
10.0	44
10.0	32
10.0	95
10.0	61
10.0	63
11.0	4
12.0	93
12.0	66
12.0	50
12.0	7
12.0	48
12.0	58
12.0	33
12.0	71
13.0	36
13.0	6
14.0	68
14.0	96
14.0	94
15.0	91
15.0	5
15.0	70
15.0	2
15.0	98
16.0	28
16.0	78
16.0	75
16.0	16
18.0	64
18.0	83
18.0	23
18.0	60
20.0	34
20.0	51
20.0	43
20.0	15
20.0	11
20.0	1
20.0	8
20.0	20
20.0	35
20.0	12
20.0	87
20.0	13
22.0	10
22.0	47
22.0	86
22.0	14
22.0	88
23.0	18
25.0	24
25.0	31
25.0	92
25.0	49
25.0	97
25.0	84
26.0	73
27.0	65
27.0	42
28.0	85
30.0	76
30.0	19
30.0	72
30.0	54
30.0	29
30.0	27
30.0	25
30.0	41
30.0	77
30.0	55
30.0	69
30.0	38
30.0	59
30.0	52
30.0	37
30.0	22
30.0	30
31.0	80
31.0	9
32.0	82
33.0	46
34.0	45
35.0	56
35.0	81
35.0	17
35.0	53
35.0	57
40.0	26
40.0	74
40.0	40
42.0	90
45.0	21
``` 


## 1.8.6 District containing the oldest tree (difficult)

### 1.8.6.1 Implementation 

This time, we have to create our own Writable subclass *DoubleIntWritable*, we create two IntWritable attributs val1 for district and val2 for year, their methods and the specific methods of writable classes : readfields and write.
The purpose of this custom writable class is to be able to have 2 attributs for the value in the key value couple in order to do comparison in the reducer. 

Then we create *SortHeight* to display the district of the oldest tree.
It implements a new mapper and a new reducer.

For the mapper *DistrictOldest*, we had to be carefull by taking as value output : DoubleIntWritable. 
If we are not in the header, we will set val1 to district (2nd column) and val2 to year (6th column) which will be the two attributs of our DoubleIntWritable object. 
Our outputs will be on the form: an IntWritable ( of value 1) for the key and the DoubleIntWritable object as the value.
By doing this, all our values will be store with only one key and we will be able to compare them in the reducer and return only one element.

For the reducer *OldestReducer*, we compare the year thanks to a for loop going through the values, and return the minimum one. 
We will have to specify that we have IntWritable and DoubleIntWritable as input and IntWritable and NullWritable as output. 
This difference between the input and output class bring us some issues:
As the output from the Mapper was different from the input of the combiner and the reducer, we had to supress the combiner from DistrictOldest job and we had to specify the MapOutputKey class and MapOutputValue class.
In *DistrictOldest*, we specify that we use *SortHeightMapper* as mapper and *SortReducer* as reducer.

### 1.8.6.2 Commands and result

Now let's take a look at the output.
We run:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar  districtoldest trees.csv oldestout
```

We obtain: 

```
-sh-4.2$ hdfs dfs -cat oldestout/part-r-00000
5
```


## 1.8.7 District containing the most trees (very difficult)

### 1.8.7.1 Implementation

For this last exercise, we have to do two MapReduce phases as we want to display the district which contains the most trees.

#### Count district
Firstly, we count the number of trees per district.
For the mapper, we create *CountDistrictsMapper* which gives the list of districts containing trees with district as key and 1 as value.
Then using *IntSumReducer*, we concatenate and reduce, it returns the district as key and the sum of its values ( number of trees) as value.
 
### Concatenate
Then, we have to find the district containing the most trees. 
For the mapper *ConcatMapper*, we take the output of the first job and as we want to compare several information and return only one element, we reused what we have done in the previous exercise, the custom writable class.
We save the values of the district and number of trees as attributs of an object of our class *DoubleIntWritable*.
So our outputs for the Mapper are on the form: an IntWritable  ( of value 1) for the key and a DoubleIntWritable object as the value.

For the reducer *ConcatMaxReducer*, we compare the different values of the key to return the maximum one. 
As the value of the key value couple, is a DoubleIntWritable object, we compare the attribut number of trees and keep the maximum one and its district. 
As output, we only return the district.

### The Job class DistrictMost
The Job class *DistrictMost* was tricky. 
In fact, we had to create two jobs in one class knowing that the output of the first job would be the input of the second one. 
We store the result of the first job in a file called *temp* and we use it for the second one as an input. 
As in the previous exercise, for the second job, we didn't use a combiner as we have different input and output for the reducer and we will have to set the output key and value class for the mapper.


### 1.8.7.2 Commands and result

Now let's take a look at the output.
First we run:
```
-sh-4.2$ yarn jar  hadoop-examples-mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar  districtmost trees.csv mostout
```

We obtain two files:
```
-sh-4.2$ hdfs dfs -ls mostout
Found 2 items
drwxr-xr-x   - ccarayon hdfs          0 2020-11-03 23:02 mostout/out
drwxr-xr-x   - ccarayon hdfs          0 2020-11-03 23:01 mostout/temp
```

The intermediate result *temp*:
```
-sh-4.2$ hdfs dfs -cat mostout/temp/part-r-00000
11	1
12	29
13	2
14	3
15	1
16	36
17	1
18	1
19	6
20	3
3	1
4	1
5	2
6	1
7	3
8	5
9	1
```


And the final result *out*:
```
-sh-4.2$ hdfs dfs -cat mostout/out/part-r-00000
16	36
```


## Conclusion 