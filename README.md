# flink-sql-submit

This project is inspired by `wuchong/flink-sql-submit`.
I pack sql file into jar so that it can be deployed in production env.

Usage:
1. place you sql file like `demo.sql` in `src/main/resources/sql`
2. config you properties (checkpoint url) in `src/main/resources/app.properties`
3. may be add some udf yourself
4. run `mvn clean package` to get target jar
5. run `flink run [-some args] sql-submit-1.0-SNAPSHOT.jar sqlFileName(e.g. demo)` to deploy.


Roadmap
* support cos/oss to get sqlfile
* support db to get properties and sql
