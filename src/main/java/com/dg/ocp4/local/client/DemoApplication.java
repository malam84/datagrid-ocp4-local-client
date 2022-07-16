package com.dg.ocp4.local.client;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {


	@Value("${infinispan.client.hotrod.server}")
	private String host;

	@Value("${infinispan.client.hotrod.port}")
	private int ssl_port;

	@Value("${infinispan.client.hotrod.auth_username}")
	private String userName;

	@Value("${infinispan.client.hotrod.auth_password}")
	private String password;

	@Value("${infinispan.client.hotrod.realm}")
	private String realm;

	@Value("${infinispan.client.hotrod.sasl_mechanism}")
	private String saslMechanism;

	@Value("${infinispan.client.hotrod.sni_host_name}")
	private String sniHostName;

	@Value("${infinispan.client.hotrod.trust_store_path}")
	private String trustStorePath;

	@Bean
	public RemoteCacheManager remoteCacheManager(){

		System.out.println("server started ===================================" +host);
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer()
				.host(host)
				.port(ssl_port)
				.security().authentication()
				.username(userName)
				.password(password)
				.realm(realm)
				.saslMechanism(saslMechanism)
				.ssl()
				.sniHostName(sniHostName)
				.trustStorePath(trustStorePath)
				.clientIntelligence(ClientIntelligence.BASIC);

		RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
		return cacheManager;

	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("server started ===================================");

		RemoteCache<String, String> cache = remoteCacheManager().getCache("TEST");
		cache.put("key1", "hey");
		cache.put("key2", "Whatsapp");
		// Retrieve the value and print it.
		//	cache.remove("hello");
		System.out.printf("key = %s\n", cache.get("key1"));

		cache.remove("key1");
		System.out.printf("key = %s\n", cache.get("key1"));

	}

}
