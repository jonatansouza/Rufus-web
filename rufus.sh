#!/bin/bash
 
mkdir /var/rufus

chown tomcat7:tomcat7 /var/rufus

mkdir /var/rufus/icons
mkdir /var/rufus/db
mkdir /var/rufus/setup
mkdir /var/rufus/users

touch /var/rufus/setup/initFile
touch /var/rufus/db/rootUsers
echo 'admin@rufus.br' >> /var/rufus/db/rootUsers
echo "{\"hosts\":[{\"name\":\"web\",\"url\":\"http://146.134.234.10:8080\"},{\"name\":\"core\",\"url\":\"http://146.134.234.8:5000/v2\"},{\"name\":\"auth\",\"url\":\"http://192.168.122.218:3000\",\"authSetup\":{\"appID\":\"d06c878641cacda9a914caf894a51210ef6b3cfeb78f4d08fec23519c4438323\",\"appSecret\":\"48df678ce1336c984fc0bea7a86bd760ea157a56b612d226644ae96900b16b1c\",\"clientEndPoint\":\"/api/v1/me.json\",\"appEndPoint\":\"/rufus/oauth/callback\"}}]}" > /var/rufus/setup/initFile

echo "/var/rufus/users $1(rw,no_subtree_check,async,no_root_squash)" >> /etc/exports

exportfs
