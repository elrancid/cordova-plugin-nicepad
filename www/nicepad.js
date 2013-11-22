/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

//var utils = require('cordova/utils');
var exec = require('cordova/exec');

/**
 * This represents the NicePad class with all methods to implements
 * 
 * How to use:
 * nicepad.echo(send message: "+message);
 * nicepad.echo(
 * 	function(msg) {
 * 	 // return msg
 *  },
 *  function(err) {
 * 	 // return err
 *  },
 *  "message to echo"
 * );
 * 
 * @constructor
 */
function NicePad() {}

/**
 * Echo a message
 *
 * @param {Function} successCallback The function to call when the echo message is available.
 * @param {Function} errorCallback The function to call when there is an error getting the echo message.
 * @param {String} message The message to send.
 */
NicePad.prototype.echo = function(successCallback, errorCallback, message) {
	exec(successCallback, errorCallback, "NicePad", "echo", [message]);
};

/**
 * Exit from webapp and return to notive app.
 */
NicePad.prototype.quit = function() {
	exec(null, null, "NicePad", "quit", []);
};

/**
 * Send a message over TCP connection.
 *
 * @param {Function} successCallback The function to call when the message is sent correctly.
 * @param {Function} errorCallback The function to call when there is an error sending message.
 * @param {String} message The message to send.
 */
NicePad.prototype.send = function(successCallback, errorCallback, message) {
	exec(successCallback, errorCallback, "NicePad", "send", [message]);
};

/**
 * Receive the messages from TCP connection.
 *
 * @param {Function} successCallback The function to call when the a message is received.
 * @param {Function} errorCallback The function to call when there is an error receiving the message.
 */
NicePad.prototype.receive = function(successCallback, errorCallback) {
	exec(successCallback, errorCallback, "NicePad", "receive", []);
};

module.exports = new NicePad();