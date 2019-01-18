package de.grzb.medienbestandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class MedienbestandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedienbestandServiceApplication.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void fuegeMediumEin(Medium medium) {
		System.out.println("Fuege ein: " + medium);
	}

	public static class Medium {
		private String titel;

		public String getTitel()
		{
			return titel;
		}

		public void setTitel(String titel)
		{
			this.titel = titel;
		}

		@Override
		public String toString()
		{
			return this.titel;
		}
	}
}

