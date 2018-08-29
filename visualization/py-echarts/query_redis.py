#!/usr/bin/env python
# -*- coding: utf-8 -*-


import redis  

class Redis_Query:

    def query_click(self):
        r = redis.StrictRedis(host='127.0.0.1', port=6379)
        return r.hgetall('click')  

  
