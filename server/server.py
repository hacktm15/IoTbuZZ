#!/usr/bin/python

import os
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web

import json

import sqlite3 as sdb

from tornado.options import define, options
define("port", default=8000, help="run on the given port", type=int)

con = sdb.connect('smart_house.db')

class BaseHandler(tornado.web.RequestHandler):
    def respond(self, status, status_code=None):
        if status_code:
            self.set_status(status_code)
            return self.finish({"status":status})

        self.finish({"status": "OK", "data":status})

class IndexHandler(BaseHandler):
    def get(self):
        self.render("server/template.html", title="IoTbuZZ")

class SensorsHandler(BaseHandler):
    def get(self):
        greeting = self.get_argument('greeting', 'Hello')
        self.write(greeting + ', friendly user!')

class StatusHandler(BaseHandler):
    def get(self):
        start_time = self.get_argument("start_time", "1980")
        data = None
        with con:
            cur = con.cursor()
            cur.execute('SELECT * FROM sensors WHERE timestamp > %s' % start_time)
            data = cur.fetchall()
        if not data:
            self.respond("Fail to select data", 404)
        temperature = [row[4] for row in data if row[0] == u"temperature"]
        humidity = [row[4] for row in data if row[0] == u"humidity"]
        doors = [row[4] for row in data if row[0] == u"doors"]
        #print temperature
        #print data

        response = {"temperature": temperature,
                    "humidity": humidity,
                    "doors": doors
        }
        self.respond(response)

class ArriveHandler(BaseHandler):
    def post(self):
        try:
            data = json.loads(self.request.body)
        except Exception as err:
            print "Invalid body: ", err
            self.respond("Invalid body", 401)

        print data
        self.respond("Okay")

class ManualGateActionHandler(BaseHandler):
    def post(self):
        try:
            data = json.loads(self.request.body)
        except Exception as err:
            print "Invalid body: ", err
            self.respond("Invalid body", 401)

        self.respond("Okay")

if __name__ == "__main__":
    tornado.options.parse_command_line()
    app = tornado.web.Application(
            handlers = [
                (r"/", IndexHandler),
                (r"/arrive/", ArriveHandler),
                (r"/status/", StatusHandler),
                (r"/manual_gate_action/", ManualGateActionHandler),
	    ],
	    static_path = os.path.join(os.path.dirname(__file__), r"server"),)


    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
