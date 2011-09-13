/*
 * Copyright 2009-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cloudfoundry.test;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller that exposes basic information about dependencies that were
 * auto-wired on app startup
 *
 * @author Jennifer Hickey
 *
 */
@Controller
public class ServiceController {

	@Autowired
	private ServiceHolder serviceHolder;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String hello(HttpServletResponse response) throws IOException {
		return "Welcome to the vcap-java test application";
	}

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public ResponseEntity<Properties> getCloudProperties() {
		if (serviceHolder.getCloudProperties() == null) {
			return new ResponseEntity<Properties>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Properties>(serviceHolder.getCloudProperties(), HttpStatus.OK);
	}

	@RequestMapping(value = "/mysql", method = RequestMethod.GET)
	public ResponseEntity<String> getMySQLDataSourceDBUrl() {
		if (serviceHolder.getMySqlDataSource() == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(serviceHolder.getMySqlDataSource().getUrl(), HttpStatus.OK);
	}

	@RequestMapping(value = "/postgres", method = RequestMethod.GET)
	public ResponseEntity<String> getPostgresDataSourceDBUrl() {
		if (serviceHolder.getPostgresDataSource() == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(serviceHolder.getPostgresDataSource().getUrl(), HttpStatus.OK);
	}

	@RequestMapping(value = "/mongo", method = RequestMethod.GET)
	public ResponseEntity<String> getMongoHostAddress() {
		if (serviceHolder.getMongoDbFactory() == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(serviceHolder.getMongoDbFactory().getDb().getMongo().getAddress().toString(),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/rabbit", method = RequestMethod.GET)
	public ResponseEntity<String> getRabbitClass() {
		if (serviceHolder.getRabbitConnectionFactory() == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(serviceHolder.getRabbitConnectionFactory().getClass().getName(),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/redis", method = RequestMethod.GET)
	public ResponseEntity<String> getRedisClass() {
		if (serviceHolder.getRedisConnectionFactory() == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(serviceHolder.getRedisConnectionFactory().getClass().getName(), HttpStatus.OK);
	}
}