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
        print "put 'ccarayon:library', '"+ values[11] + "', 'gender:GENRE', \""+ values[2]+ "\""
    if values[3]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'gender:ESPECE', \""+ values[3]+  "\""
    if values[4]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'gender:FAMILLE', \""+ values[4]+  "\""
    if values[9]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'gender:NOM_COMMUN', \""+ values[9]+  "\""
    if values[10]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'gender:VARIETE', \""+ values[10]+  "\""

    # information family
    if values[5]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'information:ANNEE_PLANTATION', \""+ values[5]+  "\""
    if values[6]:
        print "put 'ccarayon:library', '"+ values[11] +"', 'information:HAUTEUR', \""+ values[6]+ "\""
    if values[7]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'information:CIRCONFERENCE', \""+ values[7]+ "\""

    # address family
    if values[0]:
        print "put 'ccarayon:library', '"+ values[11] +"', 'address:GEOPOINT', \""+ values[0]+ "\""
    if values[1]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'address:ARRONDISSEMENT', \""+ values[1]+ "\""
    if values[8]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'address:ADRESSE', \"" + values[8]+ "\""
    if values[12]:
        print "put 'ccarayon:library', '"+ values[11] + "', 'address:NOM_EV', \""+ values[12]+ "\""
