# datacollector

A demo app that processes receives bulk event data from requests and stores
it in HBase.

## Setup

Install [HBase] (http://hbase.apache.org/) and start it by running `./start-hbase.sh`

Create the necessary tables and column families from the HBase shell
``` 
$ ./hbase shell
hbase(main):001:0> create 'events', {NAME => 'event'}, {NAME => 'props'}
```

Check out the project and launch an embedded Tomcat server

```
git clone git://github.com/julson/datacollector.git
cd ./datacollector
mvn tomcat7:run
```
