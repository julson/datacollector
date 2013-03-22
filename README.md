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
Navigate to the project directory and run `mvn tomcat7:run` to launch
an embedded Tomcat server for development.
