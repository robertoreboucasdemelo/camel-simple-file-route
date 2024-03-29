package com.moduretick.simplefilepoller;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest
@UseAdviceWith
class LegacyFileRouteTest {
	
	@Autowired
	CamelContext context;
	
	@EndpointInject("mock:result")
	protected MockEndpoint mockEndpoint;
	
	@Autowired
	ProducerTemplate producerTemplate;

	@Test
	void testFileMove() throws Exception {
		
		// Setup the Mock
		
		String expectedBody = "This is an input file that will be processed and moved to output directory";
		mockEndpoint.expectedBodiesReceived(expectedBody);
		mockEndpoint.expectedMinimumMessageCount(1);
		
		
		// Tweak the Route Definition
		
		AdviceWith.adviceWith(context, "legacyFileMoveRouteId", routeBuilder -> {
			routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
		});
		
		// Start the Context and Validate is Mock is Asserted
		
		context.start();
		mockEndpoint.assertIsSatisfied();
	}
	
	@Test
	void testFileMoveByMockingFromEndpoint() throws Exception {
		
		// Setup the Mock
		
		String expectedBody = "This is an input file that will be processed and moved to output directory";
		mockEndpoint.expectedBodiesReceived(expectedBody);
		mockEndpoint.expectedMinimumMessageCount(1);
		
		
		// Tweak the Route Definition
		
		AdviceWith.adviceWith(context, "legacyFileMoveRouteId", routeBuilder -> {
			routeBuilder.replaceFromWith("direct:mockStart");
			routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
		});
		
		// Start the Context and Validate is Mock is Asserted
		
		context.start();
		producerTemplate.sendBody("direct:mockStart" , expectedBody);
		mockEndpoint.assertIsSatisfied();
	}

}
