package com.moduretick.simplefilepoller.components;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.moduretick.simplefilepoller.beans.NameAddress;

@Component
public class LegacyFileRoute extends RouteBuilder {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	BindyCsvDataFormat bindyDataFormat = new BindyCsvDataFormat(NameAddress.class);

	@Override
	public void configure() throws Exception {
		from("file:src/data/input?fileName=inputFile.csv&noop=true")
		.routeId("legacyFileMoveRouteId")
		.split(body().tokenize("\n", 1, true))
		.unmarshal(bindyDataFormat)
		.process(exchange ->{
			NameAddress fileData = exchange.getIn().getBody(NameAddress.class);
			logger.info("This is the read fileData: " + fileData.toString());
			exchange.getIn().setBody(fileData.toString());
		})
		.to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
		.end();
		
	}

}
