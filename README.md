# lsde
Lsde2018group5Project

After the extraction of the author JSONs from the surfsaea cluster, we needed to arrange the authors in the files starting from the 
starting alphabets of their names in order to have a quick traversal and search. so We wrote a java script that converts the data JSONs
with a pdf data according to the DOI to the JSONs with author names starting alphabet.
so if a publisher has 3 authors, there will be 3 different entries in the newly created JSON files for each other and UI will directly 
be generated from those JSONs.

The UI runs in the a simple web server as the JSON operates on Http protocol and need to host the page in a localhost server.
for that we have use chrome web server. 
for Running we just need to add chrome web server plugin in our chrome and locate the project in that path and access the localhost server 
with our hosted site.


