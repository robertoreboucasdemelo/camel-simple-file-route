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
	void testFileMoveByMockingFromEndpoint() throws Exception {
		
		String expectedBody = "OutboundNameAddress [name=Sam, address=12 Ajax, Ontario, L1S 2TR]";
	    mockEndpoint.expectedBodiesReceived(expectedBody);
	    mockEndpoint.expectedMinimumMessageCount(1);
	 
	    AdviceWith.adviceWith(context, "legacyFileMoveRouteId", routeBuilder -> {
	        routeBuilder.replaceFromWith("direct:mockStart");
	        routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
	    });
	 
	    context.start();
	    producerTemplate.sendBody("direct:mockStart", "name; house_number; city; province; postal_code\n" +
	            "Sam;12;Ajax;Ontario;L1S 2TR");
	    mockEndpoint.assertIsSatisfied();
	}

}
