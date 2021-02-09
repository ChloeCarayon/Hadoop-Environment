
**Authors**: Carayon Chloé - Taillieu Victor
**Date**: 21/11/2020

---
# TP6: Apache HBase
---

## Introduction

This TP aims to discover Apache HBase and learn the Ruby synthax by creating our own database.
We will reuse the file trees.csv and create the corresponding table using a python script.

## 1. First commands

In order to familiarize with this new NoSQL database, let's perform some basic commands.

### 1.1. Base commands

* To connect
```
-sh-4.2$ hbase shell
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/hdp/3.1.5.0-152/hadoop/lib/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/hdp/3.1.5.0-152/hbase/lib/client-facing-thirdparty/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
HBase Shell
Use "help" to get list of supported commands.
Use "exit" to quit this interactive shell.
For Reference, please visit: http://hbase.apache.org/2.0/book.html#shell
Version 2.1.6.3.1.5.0-152, rUnknown, Thu Dec 12 20:16:57 UTC 2019
Took 0.0039 seconds   
```

* To obtain the status
```ruby
hbase(main):003:0> status
1 active master, 1 backup masters, 3 servers, 0 dead, 2.6667 average load
Took 0.3215 seconds    
```

* And the version 
```ruby
hbase(main):004:0> version
2.1.6.3.1.5.0-152, rUnknown, Thu Dec 12 20:16:57 UTC 2019
Took 0.0006 seconds
```

* whoami
```ruby
hbase(main):005:0>  whoami
ccarayon@EFREI.ONLINE (auth:KERBEROS)
	groups: ccarayon, hadoop-users
Took 0.0089 seconds   
```

* To list

When we enter the command list, we have the created namespace and tables. At the beginning, there was only the default ones.
```ruby
hbase(main):006:0> list
TABLE                                                                           
ATLAS_ENTITY_AUDIT_EVENTS                                                       
atlas_titan                                                                     
2 row(s)
Took 0.0336 seconds                                                             
=> ["ATLAS_ENTITY_AUDIT_EVENTS", "atlas_titan"]
```

* To exit

We exit HBase and will return in the Hadoop cluster. 
```ruby
hbase(main):007:0> exit
```

### 1.2. Create your own namespace 

Create our own namespace:
```ruby
hbase(main):006:0> create_namespace 'ccarayon'
```

To see the list of namespaces:
```ruby
hbase(main):005:0> list_namespace
NAMESPACE                                                                       
ccarayon                                                                        
default                                                                         
hbase                                                                           
mhatoum                                                                         
4 row(s)
Took 0.0881 seconds   
```

### 1.3. Creating a table

 To create the 'library' table with families 'author' and 'book', we enter this command: 
```ruby
hbase(main):006:0> create 'ccarayon:library', 'author', 'book'
Created table ccarayon:library
Took 1.2944 seconds                                                             
=> Hbase::Table - ccarayon:library
```

In order to specify the number of versions available for each family, we have to run this command:

* Version of Author
```ruby
hbase(main):007:0> alter 'ccarayon:library', {NAME => 'author', VERSIONS => 2}
Updating all regions with the new schema...
1/1 regions updated.
Done.
Took 2.3229 seconds 
```

* Version of Book
```ruby
hbase(main):008:0> alter 'ccarayon:library', {NAME => 'book', VERSIONS => 3}
Updating all regions with the new schema...
1/1 regions updated.
Done.
Took 2.0968 seconds  
```

To describe the table structure:

```ruby
hbase(main):009:0> describe 'ccarayon:library'
Table ccarayon:library is ENABLED                                               
ccarayon:library                                                                
COLUMN FAMILIES DESCRIPTION                                                     
{NAME => 'author', VERSIONS => '2', EVICT_BLOCKS_ON_CLOSE => 'false', NEW_VERSIO
N_BEHAVIOR => 'false', KEEP_DELETED_CELLS => 'FALSE', CACHE_DATA_ON_WRITE => 'fa
lse', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', MIN_VERSIONS => '0', REPL
ICATION_SCOPE => '0', BLOOMFILTER => 'ROW', CACHE_INDEX_ON_WRITE => 'false', IN_
MEMORY => 'false', CACHE_BLOOMS_ON_WRITE => 'false', PREFETCH_BLOCKS_ON_OPEN => 
'false', COMPRESSION => 'NONE', BLOCKCACHE => 'true', BLOCKSIZE => '65536'}     
{NAME => 'book', VERSIONS => '3', EVICT_BLOCKS_ON_CLOSE => 'false', NEW_VERSION_
BEHAVIOR => 'false', KEEP_DELETED_CELLS => 'FALSE', CACHE_DATA_ON_WRITE => 'fals
e', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER', MIN_VERSIONS => '0', REPLIC
ATION_SCOPE => '0', BLOOMFILTER => 'ROW', CACHE_INDEX_ON_WRITE => 'false', IN_ME
MORY => 'false', CACHE_BLOOMS_ON_WRITE => 'false', PREFETCH_BLOCKS_ON_OPEN => 'f
alse', COMPRESSION => 'NONE', BLOCKCACHE => 'true', BLOCKSIZE => '65536'}       
2 row(s)
Took 0.0288 seconds   
```

### 1.4. Adding values

We add those values, using put commands with: 
* 'vhugo' or 'jverne' as key
* 'author' or 'book' as family
* 'lastname', 'firstname' as columns for 'author'
* 'title', 'category', 'year', 'publisher' as columns for 'book'

```ruby
hbase(main):028:0> put 'ccarayon:library', 'vhugo', 'author:lastname', 'Hugo'
Took 0.3421 seconds  
hbase(main):029:0> put 'ccarayon:library', 'vhugo', 'author:firstname', 'Victor' 
Took 0.0084 seconds  
hbase(main):031:0> put 'ccarayon:library', 'vhugo', 'book:title', 'La légende des siècles'
Took 0.0092 seconds  
hbase(main):032:0> put 'ccarayon:library', 'vhugo', 'book:category', 'Poèmes'
Took 0.0111 seconds 
hbase(main):033:0> put 'ccarayon:library', 'vhugo', 'book:year', '1855'
Took 0.0090 seconds                                                             
hbase(main):034:0> put 'ccarayon:library', 'vhugo', 'book:year', '1877'
Took 0.0061 seconds                                                             
hbase(main):035:0> put 'ccarayon:library', 'vhugo', 'book:year', '1883'
Took 0.0059 seconds
hbase(main):036:0> put 'ccarayon:library', 'jverne', 'author:lastname', 'Verne'
Took 0.0072 seconds                                                             
hbase(main):037:0> put 'ccarayon:library', 'jverne', 'author:firstname', 'Jules'
Took 0.0069 seconds                                                             
hbase(main):038:0> put 'ccarayon:library', 'jverne', 'book:publisher', 'Hetzel'
Took 0.0064 seconds                                                             
hbase(main):039:0> put 'ccarayon:library', 'jverne', 'book:title', 'Face au drapeau'
Took 0.0078 seconds                                                             
hbase(main):040:0> put 'ccarayon:library', 'jverne', 'book:year', '1896'
Took 0.0075 seconds   
```

All those instructions only created two tuples, 'vhugo' and 'jverne'.
We can look at them using a get:

* 'vhugo'
```ruby
hbase(main):045:0> get 'ccarayon:library', 'vhugo'
COLUMN                            CELL                                                                                          
 author:firstname                 timestamp=1605599202272, value=Victor                                                         
 author:lastname                  timestamp=1605599164429, value=Hugo                                                           
 book:category                    timestamp=1605599332160, value=Po\xC3\xA8mes                                                  
 book:title                       timestamp=1605599292559, value=La l\xC3\xA9gende des si\xC3\xA8cles                           
 book:year                        timestamp=1605599362897, value=1883                                                           
1 row(s)
Took 0.0305 seconds                                                         
```

* 'jverne'
```ruby
hbase(main):046:0> get 'ccarayon:library', 'jverne'
COLUMN                            CELL                                                                                          
 author:firstname                 timestamp=1605599435686, value=Jules                                                          
 author:lastname                  timestamp=1605599420420, value=Verne                                                          
 book:publisher                   timestamp=1605599455183, value=Hetzel                                                         
 book:title                       timestamp=1605599472074, value=Face au drapeau                                                
 book:year                        timestamp=1605599483989, value=1896                                                           
1 row(s)
Took 0.0093 seconds 
```
We clearly see that those two tuples don't have the same columns.


### 1.5. Counting values

To count the number of tuples: 
```ruby
hbase(main):052:0> count 'ccarayon:library'
2 row(s)
Took 0.0254 seconds                                                                                                             
=> 2
```
We obtain 2 as we have created only two tuples: 'vhugo' and 'jverne'.


### 1.6. Retrieving values

* Get 'vhugo'

It gives us all the information of 'vhugo' tuple.
```ruby
hbase(main):053:0> get 'ccarayon:library', 'vhugo'
COLUMN                            CELL                                                                                          
 author:firstname                 timestamp=1605599202272, value=Victor                                                         
 author:lastname                  timestamp=1605599164429, value=Hugo                                                           
 book:category                    timestamp=1605599332160, value=Po\xC3\xA8mes                                                  
 book:title                       timestamp=1605599292559, value=La l\xC3\xA9gende des si\xC3\xA8cles                           
 book:year                        timestamp=1605599362897, value=1883                                                           
1 row(s)
Took 0.0127 seconds    
```

* Get 'vhugo', 'author'

It gives only the information from the 'author' family.
```ruby
hbase(main):054:0> get 'ccarayon:library', 'vhugo', 'author'
COLUMN                            CELL                                                                                          
 author:firstname                 timestamp=1605599202272, value=Victor                                                         
 author:lastname                  timestamp=1605599164429, value=Hugo                                                           
1 row(s)
Took 0.0198 seconds 
```

* Get 'vhugo', 'author:firstname'
```ruby
hbase(main):055:0> get 'ccarayon:library', 'vhugo', 'author:firstname'
COLUMN                            CELL                                                                                          
 author:firstname                 timestamp=1605599202272, value=Victor                                                         
1 row(s)
Took 0.0083 seconds 
```

* Get 'jverne', 'book'

In order to obtain the 'book' family from the 'jverne' tuple.
```ruby
hbase(main):059:0> get 'ccarayon:library', 'jverne', COLUMN => 'book'
COLUMN                            CELL                                                                                          
 book:publisher                   timestamp=1605599455183, value=Hetzel                                                         
 book:title                       timestamp=1605599472074, value=Face au drapeau                                                
 book:year                        timestamp=1605599483989, value=1896                                                           
1 row(s)
Took 0.0062 seconds 
```

* Get 'jverne', 'book:title'

In order to obtain the 'title' column from the book' family in the 'jverne' tuple.
```ruby
hbase(main):060:0> get 'ccarayon:library', 'jverne', COLUMN => 'book:title'
COLUMN                            CELL                                                                                          
 book:title                       timestamp=1605599472074, value=Face au drapeau                                                
1 row(s)
Took 0.0161 seconds  
```

* Get 'jverne', 'book:title' , 'book:year' , 'book:publisher'

In order to obtain the 'title', 'year' and 'publisher' columns from the book' family in the 'jverne' tuple.
```ruby
hbase(main):061:0> get 'ccarayon:library', 'jverne', COLUMN => ['book:title', 'book:year', 'book:publisher']
COLUMN                            CELL                                                                                          
 book:publisher                   timestamp=1605599455183, value=Hetzel                                                         
 book:title                       timestamp=1605599472074, value=Face au drapeau                                                
 book:year                        timestamp=1605599483989, value=1896                                                           
1 row(s)
Took 0.0204 seconds 
```

* The filter

This filter asks which columns have 'Jules' as value.
```ruby
hbase(main):062:0> get 'ccarayon:library', 'jverne', FILTER => "ValueFilter(=, 'binary:Jules')"
COLUMN                            CELL                                                                                          
 author:firstname                 timestamp=1605599435686, value=Jules                                                          
1 row(s)
Took 0.0707 seconds 
```


### 1.7. Tuple browsing

* To display all the data in the table

Using a scan command, we recover the information from the different tuples.
```ruby
hbase(main):064:0> scan 'ccarayon:library'
ROW                         COLUMN+CELL                                                                 
 jverne                     column=author:firstname, timestamp=1605599435686, value=Jules               
 jverne                     column=author:lastname, timestamp=1605599420420, value=Verne                
 jverne                     column=book:publisher, timestamp=1605599455183, value=Hetzel                
 jverne                     column=book:title, timestamp=1605599472074, value=Face au drapeau           
 jverne                     column=book:year, timestamp=1605599483989, value=1896                       
 vhugo                      column=author:firstname, timestamp=1605599202272, value=Victor              
 vhugo                      column=author:lastname, timestamp=1605599164429, value=Hugo                 
 vhugo                      column=book:category, timestamp=1605599332160, value=Po\xC3\xA8mes          
 vhugo                      column=book:title, timestamp=1605599292559, value=La l\xC3\xA9gende des si\x
							C3\xA8cles                                                                  
 vhugo                      column=book:year, timestamp=1605599362897, value=1883                       
2 row(s)
Took 0.0266 seconds
```

* To display the data from the 'book' family

It returns all the information from the 'book' family from all the tuples we have created.
```ruby
hbase(main):065:0> scan 'ccarayon:library', COLUMNS => 'book'
ROW                         COLUMN+CELL                                                                 
 jverne                     column=book:publisher, timestamp=1605599455183, value=Hetzel                
 jverne                     column=book:title, timestamp=1605599472074, value=Face au drapeau           
 jverne                     column=book:year, timestamp=1605599483989, value=1896                       
 vhugo                      column=book:category, timestamp=1605599332160, value=Po\xC3\xA8mes          
 vhugo                      column=book:title, timestamp=1605599292559, value=La l\xC3\xA9gende des si\x
							C3\xA8cles                                                                  
 vhugo                      column=book:year, timestamp=1605599362897, value=1883                       
2 row(s)
Took 0.0096 seconds 
```

* To display the year values of the book family present in the table.

It returns the years in the table. 
If we want to see all the versions, we will have to add `VERSIONS => 10`.
```ruby
hbase(main):066:0> scan 'ccarayon:library', COLUMNS => 'book:year'
ROW                         COLUMN+CELL                                                                 
 jverne                     column=book:year, timestamp=1605599483989, value=1896                       
 vhugo                      column=book:year, timestamp=1605599362897, value=1883                       
2 row(s)
Took 0.0103 seconds 
```

* Return keys between a and n and the fields of the family author.

Using start and stop row, we can only see the tuple 'jverne'. 
The first letter of 'vhugo' is not between a and n.
```ruby
hbase(main):114:0> scan 'ccarayon:library', COLUMNS => 'author', STARTROW=>'a', STOPROW=>'n'
ROW                         COLUMN+CELL                                                                 
 jverne                     column=author:firstname, timestamp=1605599435686, value=Jules               
 jverne                     column=author:lastname, timestamp=1605599420420, value=Verne                
1 row(s)
Took 0.0106 seconds 
```

* Return keys between a and n and the fields of the family author using a filter.

We can use a regexstring expression to filter the row to perform it. And we will specify the family we are interresting in using FamilyFilter.

```ruby
hbase(main):121:0> scan 'ccarayon:library', {FILTER => "RowFilter(=, 'regexstring:^[a-n]') AND FamilyFilter(=, 'binary:author')"}
ROW                             COLUMN+CELL                                                                              
 jverne                         column=author:firstname, timestamp=1605599435686, value=Jules                            
 jverne                         column=author:lastname, timestamp=1605599420420, value=Verne                             
1 row(s)
Took 0.0348 seconds 
```

* To display the author values: firstname 

```ruby
hbase(main):102:0> scan 'ccarayon:library', COLUMNS => 'author:firstname'
ROW                         COLUMN+CELL                                                                 
 jverne                     column=author:firstname, timestamp=1605599435686, value=Jules               
 vhugo                      column=author:firstname, timestamp=1605599202272, value=Victor              
2 row(s)
Took 0.0095 seconds   
```

* To search for columns whose value equals the specified title

We will use a SingleColumnValueFilter which will look into the 'title' column in the 'book' family and specify that we want to find a value which is equal to the title 'Face au drapeau'.

```ruby
hbase(main):105:0> scan 'ccarayon:library', {FILTER => "SingleColumnValueFilter('book', 'title', =, 'binary:Face au drapeau')"}
ROW                         COLUMN+CELL                                                                 
 jverne                     column=author:firstname, timestamp=1605599435686, value=Jules               
 jverne                     column=author:lastname, timestamp=1605599420420, value=Verne                
 jverne                     column=book:publisher, timestamp=1605599455183, value=Hetzel                
 jverne                     column=book:title, timestamp=1605599472074, value=Face au drapeau           
 jverne                     column=book:year, timestamp=1605599483989, value=1896                       
1 row(s)
Took 0.0463 seconds  
```

* To display the tuples whose book column: date is less than or equal to 1890.

We will use a SingleColumnValueFilter which will look into the 'year' column in the 'book' family.
We specify that we want to find a value which is inferior or equal to the year '1890'.
We will obtain the tuple 'vhugo' where the year is 1883.
```ruby
hbase(main):125:0> scan 'ccarayon:library', {FILTER => "SingleColumnValueFilter('book', 'year', <=, 'binary:1890')"}
ROW                             COLUMN+CELL                                                                              
 vhugo                          column=author:firstname, timestamp=1605599202272, value=Victor                           
 vhugo                          column=author:lastname, timestamp=1605599164429, value=Hugo                              
 vhugo                          column=book:category, timestamp=1605599332160, value=Po\xC3\xA8mes                       
 vhugo                          column=book:title, timestamp=1605599292559, value=La l\xC3\xA9gende des si\xC3\xA8cles   
 vhugo                          column=book:year, timestamp=1605599362897, value=1883                                    
1 row(s)
Took 0.0161 seconds  
```

* To search for tuples whose key begins with jv and one of the values of which matches the regular expression.

We will use a RowFilter with regexstring. It returns the tuple corresponding.

```ruby
hbase(main):126:0> scan 'ccarayon:library', {FILTER => "RowFilter(=, 'regexstring:^jv') "}
ROW                             COLUMN+CELL                                                                              
 jverne                         column=author:firstname, timestamp=1605599435686, value=Jules                            
 jverne                         column=author:lastname, timestamp=1605599420420, value=Verne                             
 jverne                         column=book:publisher, timestamp=1605599455183, value=Hetzel                             
 jverne                         column=book:title, timestamp=1605599472074, value=Face au drapeau                        
 jverne                         column=book:year, timestamp=1605599483989, value=1896                                    
1 row(s)
Took 0.0149 seconds 
```

### 1.8. Updating a value

We update the values in our tuples.

```ruby
hbase(main):127:0> put 'ccarayon:library', 'vhugo', 'author:lastname', 'HAGO'
Took 0.0214 seconds                                                                                      
hbase(main):128:0> put 'ccarayon:library', 'vhugo', 'author:lastname', 'HUGO'
Took 0.0048 seconds                                                                                      
hbase(main):129:0> put 'ccarayon:library', 'vhugo', 'author:firstname', 'Victor Marie'
Took 0.0067 seconds                                                                                      
hbase(main):130:0> put 'ccarayon:library', 'vhugo', 'author:lastname', 'Hugo'
Took 0.0086 seconds                                                                                      
hbase(main):131:0> get 'ccarayon:library', 'vhugo', 'author'
COLUMN                      CELL                                                                         
 author:firstname           timestamp=1605604751485, value=Victor Marie                                  
 author:lastname            timestamp=1605604765492, value=Hugo                                          
1 row(s)

hbase(main):132:0> get 'ccarayon:library', 'vhugo', COLUMNS=>'author'
COLUMN                      CELL                                                                         
 author:firstname           timestamp=1605604751485, value=Victor Marie                                  
 author:lastname            timestamp=1605604765492, value=Hugo                                          
1 row(s)
Took 0.0060 seconds   
```

In order to see all the available versions, we will use 'VERSIONS => 10'.

* get 'vhugo:author' versions

It will give all the versions existing for the key 'vhugo' for family 'author'. There are 2 versions.

```ruby
hbase(main):133:0> get 'ccarayon:library', 'vhugo', COLUMNS => 'author', VERSIONS => 10
COLUMN                      CELL                                                                         
 author:firstname           timestamp=1605604751485, value=Victor Marie                                  
 author:firstname           timestamp=1605599202272, value=Victor                                        
 author:lastname            timestamp=1605604765492, value=Hugo                                          
 author:lastname            timestamp=1605604734968, value=HUGO                                          
1 row(s)
Took 0.0236 seconds 
```

### 1.9. Deleting a value or a column

* Deleteall deleted the value author:name=HUGO, but not the other 

In order to delete the value we are interesting in, we have to take its timestamp.

```ruby
hbase(main):143:0> deleteall 'ccarayon:library', 'vhugo', 'author:lastname', 1605604734968 
Took 0.0067 seconds                                                                                      
hbase(main):144:0> get 'ccarayon:library', 'vhugo', COLUMNS=>'author', VERSIONS => 10
COLUMN                      CELL                                                                         
 author:firstname           timestamp=1605605483168, value=Victor Marie                                  
 author:firstname           timestamp=1605605460436, value=Victor                                        
 author:lastname            timestamp=1605604765492, value=Hugo                                          
1 row(s)
Took 0.0111 seconds   
```

* Delete all values for the column firstname.
```ruby
hbase(main):145:0> deleteall 'ccarayon:library', 'vhugo','author:firstname'
Took 0.0065 seconds 
hbase(main):146:0> get 'ccarayon:library', 'vhugo', COLUMNS=>'author', VERSIONS => 10
COLUMN                      CELL                                                                         
 author:lastname            timestamp=1605604765492, value=Hugo                                          
1 row(s)
Took 0.0201 seconds 
```

* Delete the entire tuple
```ruby
hbase(main):147:0> deleteall 'ccarayon:library', 'vhugo'
Took 0.0052 seconds  
``` 

* Check the versions 

As we have deleted 'vhugo' tuple, we only see 'jverne' tuple.
```ruby
hbase(main):150:0> scan 'ccarayon:library',  VERSIONS => 10
ROW                         COLUMN+CELL                                                                  
 jverne                     column=author:firstname, timestamp=1605599435686, value=Jules                
 jverne                     column=author:lastname, timestamp=1605599420420, value=Verne                 
 jverne                     column=book:publisher, timestamp=1605599455183, value=Hetzel                 
 jverne                     column=book:title, timestamp=1605599472074, value=Face au drapeau            
 jverne                     column=book:year, timestamp=1605599483989, value=1896                        
1 row(s)
Took 0.0118 seconds 
```

### 1.10. Deleting a table 

To delete the entire table, we first have to disable the table to then be able to delete it.

```ruby
hbase(main):151:0> disable 'ccarayon:library'
Took 0.8570 seconds                                                                                      
hbase(main):152:0> drop 'ccarayon:library'
Took 0.4655 seconds   
```


## 2. Trees

Firstly, we create the table with the three families: 'gender', 'information' and 'address'.

```ruby
hbase(main):154:0> create 'ccarayon:library', 'gender', 'information','address'
Created table ccarayon:library
Took 1.2683 seconds                                                                                      
=> Hbase::Table - ccarayon:library
```

Then, we create our script in python. 
```python
#!/usr/bin/env python

import sys

lines = []
for line in sys.stdin:
	lines.append(line)

lines = lines[1:]  # remove header

for i in range(len(lines)):
	lines[i] = lines[i][:-1]  # remove \n


# GEOPOINT 0
# ARRONDISSEMENT 1
# GENRE 2
# ESPECE 3
# FAMILLE 4
# ANNEE PLANTATION 5
# HAUTEUR 6
# CIRCONFERENCE 7
# ADRESSE 8
# NOM COMMUN 9
# VARIETE 10
# OBJECTID 11
# NOM_EV 12

for line in lines:
	values = line.split(";")

	# gender family
	if values[2]:
		print "put 'ccarayon:library', '" + values[11] + "', 'gender:GENRE', \"" + values[2] + "\""
	if values[3]:
		print "put 'ccarayon:library', '" + values[11] + "', 'gender:ESPECE', \"" + values[3] + "\""
	if values[4]:
		print "put 'ccarayon:library', '" + values[11] + "', 'gender:FAMILLE', \"" + values[4] + "\""
	if values[9]:
		print "put 'ccarayon:library', '" + values[11] + "', 'gender:NOM_COMMUN', \"" + values[9] + "\""
	if values[10]:
		print "put 'ccarayon:library', '" + values[11] + "', 'gender:VARIETE', \"" + values[10] + "\""

	# information family
	if values[5]:
		print "put 'ccarayon:library', '" + values[11] + "', 'information:ANNEE_PLANTATION', \"" + values[5] + "\""
	if values[6]:
		print "put 'ccarayon:library', '" + values[11] + "', 'information:HAUTEUR', \"" + values[6] + "\""
	if values[7]:
		print "put 'ccarayon:library', '" + values[11] + "', 'information:CIRCONFERENCE', \"" + values[7] + "\""

	# address family
	if values[0]:
		print "put 'ccarayon:library', '" + values[11] + "', 'address:GEOPOINT', \"" + values[0] + "\""
	if values[1]:
		print "put 'ccarayon:library', '" + values[11] + "', 'address:ARRONDISSEMENT', \"" + values[1] + "\""
	if values[8]:
		print "put 'ccarayon:library', '" + values[11] + "', 'address:ADRESSE', \"" + values[8] + "\""
	if values[12]:
		print "put 'ccarayon:library', '" + values[11] + "', 'address:NOM_EV', \"" + values[12] + "\""

```

All the print from our script will be interpreted as commands for our hbase shell thanks to this command:
```
-sh-4.2$ hdfs dfs -cat /user/ccarayon/trees.csv | python ./hbase.py | hbase shell
```

During the processing, we will see at each put command an associated Took.
It prooves that the new value has been added successfully.
```ruby
put 'ccarayon:library', '75', 'address:GEOPOINT', "(48.86260617, 2.23782412563)"
Took 0.0026 seconds                                                                                                                               
put 'ccarayon:library', '75', 'address:ARRONDISSEMENT', "16"
Took 0.0026 seconds                                                                                                                               
put 'ccarayon:library', '75', 'address:NOM_EV', "Bois de Boulogne (Moulin de Longchamp)"
Took 0.0025 seconds                                                         
```

Finally, we can take random keys to see if the information has been correctly inserted:
```
hbase(main):001:0> get 'ccarayon:library', '91'
COLUMN                                CELL                                                                                                        
 address:ADRESSE                      timestamp=1605952691079, value=Ile de Bercy                                                                 
 address:ARRONDISSEMENT               timestamp=1605952691076, value=12                                                                           
 address:GEOPOINT                     timestamp=1605952691073, value=(48.8302532096, 2.41400587444)                                               
 address:NOM_EV                       timestamp=1605952691081, value=Bois de Vincennes (Ile de Bercy)                                             
 gender:ESPECE                        timestamp=1605952691055, value=opalus                                                                       
 gender:FAMILLE                       timestamp=1605952691058, value=Sapindaceae                                                                  
 gender:GENRE                         timestamp=1605952691053, value=Acer                                                                         
 gender:NOM_COMMUN                    timestamp=1605952691062, value=Erable d'Italie                                                              
 information:ANNEE_PLANTATION         timestamp=1605952691065, value=1870                                                                         
 information:CIRCONFERENCE            timestamp=1605952691070, value=160.0                                                                        
 information:HAUTEUR                  timestamp=1605952691067, value=15.0                                                                         
1 row(s)
Took 0.3454 seconds                                                                                                                               
hbase(main):002:0> get 'ccarayon:library', '86'
COLUMN                                CELL                                                                                                        
 address:ADRESSE                      timestamp=1605952691047, value=route de la ceinture du Lac Daumesnil                                        
 address:ARRONDISSEMENT               timestamp=1605952691044, value=12                                                                           
 address:GEOPOINT                     timestamp=1605952691041, value=(48.833545551, 2.41033694606)                                                
 address:NOM_EV                       timestamp=1605952691050, value=Bois de Vincennes (Lac Daumesnil/porte Dor\xC3\xA9e)                         
 gender:ESPECE                        timestamp=1605952691023, value=orientalis                                                                   
 gender:FAMILLE                       timestamp=1605952691026, value=Platanaceae                                                                  
 gender:GENRE                         timestamp=1605952691020, value=Platanus                                                                     
 gender:NOM_COMMUN                    timestamp=1605952691029, value=Platane d'Orient                                                             
 information:ANNEE_PLANTATION         timestamp=1605952691032, value=1860                                                                         
 information:CIRCONFERENCE            timestamp=1605952691038, value=475.0                                                                        
 information:HAUTEUR                  timestamp=1605952691035, value=22.0      
```

## Conclusion

This TP gives us the opportunity to work on Apache HBase and manipulate Ruby synthax. 
We were able to run our own python script in order to insert data.
