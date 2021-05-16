# KeepTry
Algorithms + Data Structures.  DIY.

Environment:

Ubuntu 32 bit 16.04.1 LTS (Xenial Xerus)

Need install curl

Git version 2.7.4

Java

```
$ java  -version
java version "1.8.0_111"
Java(TM) SE Runtime Environment (build 1.8.0_111-b14)
Java HotSpot(TM) Server VM (build 25.111-b14, mixed mode)
```

Buck

```
$ buck --version
buck version 12963f12a2fa764f24c9c3a7413b0f9c4bdb2b05

```

Node.js
```
$ node --version
v7.4.0
```

Python

```
$ python --version
Python 2.7.11
```

Get all required Java libs and run all Junit tests

```
$ alias python=/usr/bin/python2.7; { buck targets //bow/...; buck targets //arrows/...; } | xargs buck build && buck test --all
```
Import it by Intellij IDEA 2016.3 and start enjoy coding

Leetcode
```
$ find . -name LC*.java -o -name Leetcode*[a-zA-Z].java | awk -F "/" '{print($NF)}' | sort|uniq |wc -l
```
Welcome ideas, comments and commits.

WebChat  : brucezu

Facebook : https://www.facebook.com/bruce.zu
